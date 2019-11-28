package cn.wjf.myaidlserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    }

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
}
