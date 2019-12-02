package cn.wjf.approomorm.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {MyData.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract MyDao Dao();

    private static AppDataBase appDataBase;

    public static AppDataBase getDb(Context context)
    {
        if(appDataBase == null)
        {
            synchronized (AppDataBase.class)
            {
                if(appDataBase == null) {
                    appDataBase = Room.databaseBuilder(context, AppDataBase.class, "dataroomwjf.db").build();
                }
            }
        }
        return appDataBase;
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
