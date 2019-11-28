package cn.wjf.myaidlserver.datautils.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import cn.wjf.myaidlserver.datautils.contentprovider.ContentData;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context,String name,int version)
    {
        this(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ContentData.USERS_TABLE_NAME
                    + "(" + ContentData.UserTableData._ID
                    + " INTEGER PRIMARY KEY autoincrement,"
                    + ContentData.UserTableData.NAME + " varchar(20),"
                    + ContentData.UserTableData.TITLE + " varchar(20),"
                    + ContentData.UserTableData.DATE_ADDED + " long,"
                    + ContentData.UserTableData.SEX + " boolean)" + ";");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
