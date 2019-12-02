package cn.wjf.approomorm.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void inser(MyData... data);

    @Update
    public abstract void updata(MyData... data);

    @Delete
    public abstract void delete(MyData... data);

    @Query("select * from MyData")
    public abstract List<MyData> getAll();
}
