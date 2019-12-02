package cn.wjf.approomorm.db.stockdb;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.io.File;

@Database(entities = {StockList.class},version = 1,exportSchema = false)
public abstract class StockListDataBase extends RoomDatabase {

    public static StockListDataBase instance;
    public static String dataBasePath;
    public static String dbPathFull;

    public abstract StockListDao stockListDao();

    public static String getRootPath(Context context) {
        File file = getRootFile(context);
        if (file != null) {
            file.delete();
            return file.getAbsolutePath();
        }
        return null;
    }

    public static File getRootFile(Context context) {
        File file = null;
        try {
            file = context.getExternalCacheDir();
        } catch (Exception ignored) {
        }
        if (file == null) {
            file = context.getFilesDir();
        }
        if (file == null) {
            file = context.getCacheDir();
        }
        return file;
    }

    public static void getSlDb(Context context)
    {
        if(instance == null)
        {
            synchronized (StockListDataBase.class)
            {
                if(instance == null)
                {
//                    instance = Room.databaseBuilder(context,StockListDataBase.class,"emgoodstable_v3")
//                            .build();
//                    dataBasePath = context.getApplicationInfo().dataDir + "/" + ASSET_DB_PATH + "/";//本路径是不可见的
                    dataBasePath = getRootPath(context);//本路径可见
                    dbPathFull = dataBasePath + "/" + "emgoodstable_v3";
                    instance = Room.databaseBuilder(context,StockListDataBase.class,dbPathFull)
                            .build();
                    Log.e("wjf>>>>","码表全路径名dbPathFull：" + dbPathFull);
                }
            }
        }
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
