// MyRemoteService.aidl
package cn.wjf.myaidlserver;

// Declare any non-default types here with import statements
import cn.wjf.myaidlserver.bean.Book;
import cn.wjf.myaidlserver.InewBookListener;
interface MyRemoteService {
    void setBookName(String name);
    String getBookName();
    void setBookNumber(int number);
    List<Book> getBookList();
    void addBook(inout Book book);

    void registerListener(InewBookListener listener);
    void unregisterListener(InewBookListener listener);
}
