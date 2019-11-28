package cn.wjf.myaidlserver.datautils.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.wjf.myaidlserver.datautils.db.DBOpenHelper;

public class TeacherContentProvider extends ContentProvider {

    private DBOpenHelper dbOpenHelper = null;
    private static final UriMatcher uriMatcher = ContentData.UserTableData.uriMatcher;//new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        dbOpenHelper = new DBOpenHelper(getContext(),ContentData.DATABASE_NAME,ContentData.DATABASE_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
    switch (uriMatcher.match(uri))
    {
        case ContentData.UserTableData.TEACHERS:
            return db.query(ContentData.UserTableData.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        case ContentData.UserTableData.TEACHER:
            long personid = ContentUris.parseId(uri);
            String where = "_ID=" + personid;
            where += !TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "";
            return db.query(ContentData.UserTableData.TABLE_NAME,projection,where,selectionArgs,null,null,sortOrder);
        default:
            throw new IllegalArgumentException("unknown uri: " + uri);
    }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri))
        {
            case ContentData.UserTableData.TEACHERS:
                return ContentData.UserTableData.CONTENT_TYPE;
            case ContentData.UserTableData.TEACHER:
                return ContentData.UserTableData.CONTENT_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("unknow uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long id = 0;
        switch (uriMatcher.match(uri))
        {
            case ContentData.UserTableData.TEACHERS:
                id = db.insert(ContentData.UserTableData.TABLE_NAME,null,values);
                return ContentUris.withAppendedId(uri,id);
            case ContentData.UserTableData.TEACHER:
                id = db.insert(ContentData.UserTableData.TABLE_NAME,null,values);
                String path = uri.toString();
                return Uri.parse(path.substring(0,path.lastIndexOf("/")) + id);
            default:
                throw  new IllegalArgumentException("unknown uri: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri))
        {
            case ContentData.UserTableData.TEACHERS:
                count = db.delete(ContentData.UserTableData.TABLE_NAME,selection,selectionArgs);
                break;
            case ContentData.UserTableData.TEACHER:
                long personid = ContentUris.parseId(uri);
                String where = "_ID=" + personid;
                where += !TextUtils.isEmpty(selection)? " and (" + selection + ")" : "";
                count = db.delete(ContentData.UserTableData.TABLE_NAME,where,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown uri" + uri);
        }
        db.close();
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri))
        {
            case ContentData.UserTableData.TEACHERS:
                count = db.update(ContentData.UserTableData.TABLE_NAME,values,selection,selectionArgs);
                break;
            case ContentData.UserTableData.TEACHER:
                long personid = ContentUris.parseId(uri);
                String where = "_ID=" + personid;
                where += !TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "";
                count = db.update(ContentData.UserTableData.TABLE_NAME,values,where,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown uri" + uri);
        }
        db.close();
        return count;
    }
}
