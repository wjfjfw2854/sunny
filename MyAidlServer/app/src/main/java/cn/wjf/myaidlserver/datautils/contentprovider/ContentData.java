package cn.wjf.myaidlserver.datautils.contentprovider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContentData {
    public static final String AUTHORITY = "cn.wjf.myaidlserver";
    public static final String DATABASE_NAME = "teacher.db";
    //创建 数据库的时候，都必须加上版本信息；并且必须大于4
    public static final int DATABASE_VERSION = 4;
    public static final String USERS_TABLE_NAME = "teacher";

    public static final class UserTableData implements BaseColumns{

        public static final String TABLE_NAME = "teacher";
        //必须唯一的uri
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/teacher");
        //数据集的mime类型应以vnd.android.cursor.dir/开头
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/cn.wjf.teachers";
        //单一数据的mime类型应以vnd.android.cursor.item/开头
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/cn.wjf.teacher";

        public static final int TEACHERS = 1;
        public static final int TEACHER = 2;

        public static final String TITLE = "title";
        public static final String NAME = "name";
        public static final String DATE_ADDED = "date_added";
        public static final String SEX = "SEX";
        public static final String DEFAULT_SORT_ORDER = "_id desc";

        public static final UriMatcher uriMatcher;
        static{
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(ContentData.AUTHORITY,"teacher",TEACHERS);//数据集
            uriMatcher.addURI(ContentData.AUTHORITY,"teacher/#",TEACHER);//单一数据
        }
    }
}
