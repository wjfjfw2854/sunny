package cn.wjf.myaidlserver;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.wjf.myaidlserver.bean.Book;

public class MyBinder extends MyRemoteService.Stub{
    private String name;
    private int number;

//    CopyOnWriteArrayList是ArrayList 一个线程安全的变体
    private CopyOnWriteArrayList<Book> bookAry = new CopyOnWriteArrayList<>();
//    InewBookListener接口必须用RemoteCallbackList接，而不能用CopyOnWriteArrayList存
//    客户端注册和移除注册过程中使用的虽是同一个客户端对象，但通过Binder传递到服务端后，
//    产生了两个不同的对象，因为对象是不能跨进程直接传输的，对象的跨进程传输本质上都是反序列化的过程
    private RemoteCallbackList<InewBookListener> bookListenerAry = new RemoteCallbackList<>();

    @Override
    public void setBookName(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public String getBookName() throws RemoteException {
        Log.e("wjf>>>>","getBookName()；服务端具体实现，得到书名name: " + name);
        if(TextUtils.isEmpty(name))
        {
            name = "getBookName()";
        }
        return name;
    }

    @Override
    public void setBookNumber(int number) throws RemoteException {
        this.number = number;
        Log.e("wjf>>>>","setBookNumber(x)；服务端具体实现，得到数量：" + number);
    }

    @Override
    public List<Book> getBookList() throws RemoteException {
        Log.e("wjf>>>>","getBookList()；服务端具体数据，得到：" + bookAry.size() + "本书");
        return bookAry;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        Log.e("wjf>>>>","addBook()；服务端加入客服端书籍名：" + book.getName());
        bookAry.add(book);
        final int n = bookListenerAry.beginBroadcast();
        for(int i = 0;i < n;i ++)
        {
            InewBookListener inewBookListener = bookListenerAry.getBroadcastItem(i);
            if(inewBookListener != null)
            {
                try{
                    inewBookListener.newBook(book);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        bookListenerAry.finishBroadcast();
    }

    @Override
    public void registerListener(InewBookListener listener) throws RemoteException {
        boolean successRegister = bookListenerAry.register(listener);
        final int n = bookListenerAry.beginBroadcast();
        bookListenerAry.finishBroadcast();
    }

    @Override
    public void unregisterListener(InewBookListener listener) throws RemoteException {
        boolean successUnRegister = bookListenerAry.unregister(listener);
        final int n = bookListenerAry.beginBroadcast();
        bookListenerAry.finishBroadcast();
    }
}
