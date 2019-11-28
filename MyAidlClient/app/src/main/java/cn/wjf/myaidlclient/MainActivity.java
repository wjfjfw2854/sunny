package cn.wjf.myaidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.wjf.myaidlclient.contentprovider.ContentData;
import cn.wjf.myaidlserver.InewBookListener;
import cn.wjf.myaidlserver.MyRemoteService;
import cn.wjf.myaidlserver.bean.Book;

public class MainActivity extends AppCompatActivity {

    private MyRemoteService myRemoteService;
    private final String strPackage = "cn.wjf.myaidlserver";
    private final String strAction = strPackage + ".RemoteServiceX";
    private final String strClassNameService = strPackage + ".RemoteService";
    private final String strActivity = strPackage + ".MainActivity";
    private ServiceConnection serviceConnection;

    private TextView txtBind;
    private TextView txtData;
    private TextView txtContent;
    private TextView txtLoadServerData;
    private TextView txtAdd;

    private int number;
    private TextView txtStartOther;
    private Intent aidlServerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initV();
    }

    private void startAidlServer(){
        try {
            Uri uri = Uri.parse("aidlservice://server");
//            Intent intent = new Intent(Intent.ACTION_MAIN,uri);
//            aidlServerIntent = new Intent(intent);
            aidlServerIntent = new Intent(Intent.ACTION_MAIN);
            aidlServerIntent.setClassName(strPackage, strClassNameService);
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(aidlServerIntent);
            } else {
                startService(aidlServerIntent);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        以下是启动server端的app(成功启动)
//        aidlServerIntent = new Intent(Intent.ACTION_MAIN);
//        aidlServerIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        aidlServerIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
//        aidlServerIntent.setComponent(new ComponentName(strPackage,strActivity));
//        startActivity(aidlServerIntent);
    }

    private void initV() {
        txtStartOther = (TextView)findViewById(R.id.txtStartOther);
        txtStartOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAidlServer();
            }
        });
        txtBind = (TextView)findViewById(R.id.txtBind);
        txtBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initConnection();
            }
        });
        txtData = (TextView)findViewById(R.id.txtData);
        txtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    myRemoteService.setBookNumber(++number);
                    String name = myRemoteService.getBookName();
                    myRemoteService.addBook(new Book("90克拉_" + number));
                }
                catch (Exception e)
                {
                    Log.e("wjf>>>>","出现错误：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        txtContent = (TextView)findViewById(R.id.txtContent);
        txtLoadServerData = (TextView)findViewById(R.id.txtLoadServerData);
        txtLoadServerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal(queryContentProvider());
            }
        });
        txtAdd = (TextView)findViewById(R.id.txtAdd);
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContentProvider();
                setVal(queryContentProvider());
            }
        });
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

    private void initConnection() {
        if(serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    myRemoteService = MyRemoteService.Stub.asInterface(service);
                    Log.e("wjf>>>>", "onServiceConnected--绑定远程服务成功！");
                    txtBind.setText("绑定远程服务成功！");
                    if(myRemoteService != null) {
                        try {
                            myRemoteService.registerListener(inewBookListener);//注册
                            myRemoteService.asBinder().linkToDeath(deathRecipient,0);//死亡代理
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Log.e("wjf>>>>", "onServiceDisconnected断开绑定----name: " + name);
                    myRemoteService = null;
                }
            };
        }
        Intent intent = new Intent(strAction);
        intent.setPackage(strPackage);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    private InewBookListener inewBookListener = new InewBookListener.Stub() {
        @Override
        public void newBook(Book newBook) throws RemoteException {
            Log.e("wjf>>>>","接口InewBookListener回调成功newBook.name: " + newBook.getName());
            txtData.setText(newBook.getName());
        }
    };

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

    private void setVal(List<Book> list) {
        StringBuffer sbf = new StringBuffer();
        for(int i = 0;i < list.size();i ++)
        {
            sbf.append(list.get(i).getName() + "\n");
        }
        txtContent.setText(sbf.toString().trim());
    }

    private void addContentProvider() {
        ContentResolver crl = getContentResolver();
        for(int i = 0;i < 12;i ++) {
            ContentValues cv = new ContentValues();
            cv.put(ContentData.UserTableData.NAME,"八神对话_" + i);
            cv.put(ContentData.UserTableData.TITLE,"高人_" + i);
            cv.put(ContentData.UserTableData.DATE_ADDED,200 + i);
            cv.put(ContentData.UserTableData.SEX,true);
            crl.insert(ContentData.UserTableData.CONTENT_URI,cv);
        }
    }

    @Override
    protected void onDestroy() {
        if(myRemoteService != null && myRemoteService.asBinder().isBinderAlive())
        {
            try{
                myRemoteService.unregisterListener(inewBookListener);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(serviceConnection != null) {
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }
}
