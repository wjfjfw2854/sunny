// InewBookListener.aidl
package cn.wjf.myaidlserver;

// Declare any non-default types here with import statements
import cn.wjf.myaidlserver.bean.Book;
interface InewBookListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void newBook(in Book newBook);
}
