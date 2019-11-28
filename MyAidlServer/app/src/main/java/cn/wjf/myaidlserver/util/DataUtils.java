package cn.wjf.myaidlserver.util;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import cn.wjf.myaidlserver.bean.Book;

public class DataUtils {
    public static DataUtils instance = new DataUtils();
    private final int MSG_ = 1;
    private List<Book> list = new ArrayList<>();
    private Handler handler;

    public void init()
    {
        for(int i = 0;i < 10;i ++)
        {
            Book book = new Book("雾都孤儿_" + i);
            book.setNumber(i);
            list.add(book);
        }
    }

    public List<Book> getList(){
        return list;
    }

    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    public void sendMsg()
    {
        if(handler == null)
        {
            return;
        }
        Message msg = handler.obtainMessage(MSG_);
        msg.obj = DataUtils.instance.getList();
        handler.sendMessage(msg);
    }
}
