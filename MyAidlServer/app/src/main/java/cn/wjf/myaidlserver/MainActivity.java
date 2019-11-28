package cn.wjf.myaidlserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.wjf.myaidlserver.bean.Book;
import cn.wjf.myaidlserver.datautils.contentprovider.ContentData;
import cn.wjf.myaidlserver.util.DataUtils;

public class MainActivity extends AppCompatActivity {

    private TextView txtContent;
    private TextView txtInitData;
    private TextView txtRefresh;

    private ServiceConnection serviceConnection;
    private MyRemoteService myRemoteService;
    private final String strPackage = "cn.wjf.myaidlserver";
    private final String strAction = strPackage + ".RemoteServiceX";
    private final String strClassNameService = strPackage + ".RemoteService";
    private InewBookListener inewBookListener = new InewBookListener.Stub() {
        @Override
        public void newBook(Book newBook) throws RemoteException {
            txtAdd.setText(newBook.getName()+"__再去看下MyAidlClient端如果看到该书名则这2个app通讯成功！");
        }
    };
    private TextView txtAdd;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtContent = findViewById(R.id.txtContent);
        initData();
//        loadData();
        txtInitData = findViewById(R.id.txtInitData);
        txtInitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initContentProvider();
                setVal(queryContentProvider());
            }
        });
        txtRefresh = findViewById(R.id.txtRefresh);
        txtRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal(queryContentProvider());
            }
        });
        txtAdd = findViewById(R.id.txtAdd);
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myRemoteService.setBookNumber(++number);
                    myRemoteService.addBook(new Book("本app_央国池鱼中书_" + number));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        initConnectServer();
    }

    private void initConnectServer() {
        if(serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    myRemoteService = MyRemoteService.Stub.asInterface(service);
                    if (myRemoteService != null) {
                        try {
                            myRemoteService.registerListener(inewBookListener);//注册
                            myRemoteService.asBinder().linkToDeath(deathRecipient, 0);//死亡代理
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    myRemoteService = null;
                }
            };
        }
        Intent intent = new Intent(strAction);
        intent.setPackage(strPackage);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(myRemoteService == null)
            {
                return;
            }
            myRemoteService.asBinder().unlinkToDeath(deathRecipient,0);//解除死亡代理，若Binder死亡，不会在解发binderDied
            myRemoteService = null;
        }
    };

    private void initContentProvider() {
        ContentResolver crl = getContentResolver();
        for(int i = 0;i < 12;i ++) {
            ContentValues cv = new ContentValues();
            cv.put(ContentData.UserTableData.NAME,"春雷冬寒_" + i);
            cv.put(ContentData.UserTableData.TITLE,"月神_" + i);
            cv.put(ContentData.UserTableData.DATE_ADDED,100 + i);
            cv.put(ContentData.UserTableData.SEX,true);
            crl.insert(ContentData.UserTableData.CONTENT_URI,cv);
        }
    }

    private List<Book> queryContentProvider()
    {
        ContentResolver crl = getContentResolver();
        //查询数据集
        Cursor cursor = crl.query(ContentData.UserTableData.CONTENT_URI,null,null,null,null,null);
        if(cursor == null || cursor.getCount() <= 0)
        {
            return null;
        }
        List<Book> list = new ArrayList<>();
        int k = -1;
        cursor.moveToFirst();
        do
        {
            k ++;
            int idxName = cursor.getColumnIndex(ContentData.UserTableData.NAME);
            String name = cursor.getString(idxName);
            int idxTitle = cursor.getColumnIndex(ContentData.UserTableData.TITLE);
            String title = cursor.getString(idxTitle);
            int idxDateAdded = cursor.getColumnIndex(ContentData.UserTableData.DATE_ADDED);
            long dataAdded = cursor.getLong(idxDateAdded);
            int idxSex = cursor.getColumnIndex(ContentData.UserTableData.SEX);
            String sex = cursor.getString(idxSex);
            Book book = new Book(name);
            book.setNumber(k);
            list.add(book);
        }while(cursor.moveToNext());
        return list;
    }

    private void loadData() {
        DataUtils.instance.setHandler(handler);
        DataUtils.instance.sendMsg();
    }

    private void initData() {
        DataUtils.instance.init();
    }

    public final int MSG_ = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            if(message.what == MSG_)
            {
                if(message.obj != null && message.obj instanceof List)
                {
                    List<Book> list = (List<Book>) message.obj;
                    setVal(list);
                }
            }
        }
    };

    private void setVal(List<Book> list) {
        StringBuffer sbf = new StringBuffer();
        for(int i = 0;i < list.size();i ++)
        {
            sbf.append(list.get(i).getName() + "\n");
        }
        txtContent.setText(sbf.toString().trim());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setVal(DataUtils.instance.getList());
    }

    @Override
    protected void onDestroy() {
        if(myRemoteService != null && myRemoteService.asBinder().isBinderAlive())
        {
            try {
                myRemoteService.unregisterListener(inewBookListener);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(serviceConnection != null)
        {
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }
}
