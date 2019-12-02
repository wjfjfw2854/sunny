package cn.wjf.approomorm.db.stockdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class StockListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<StockList> data);

    @Update
    public abstract void update(List<StockList> data);

    @Delete
    public abstract void delete(List<StockList> data);

    @Query("select * from stocklist")
    public abstract List<StockList> getAll();

//    @Query("select * from stocklist where goodid <= :code and exchange <> 12 limit 20")
//    @Query("select * from stocklist where pinyin like '%' || :code || '%'")
    @Query("select * from stocklist where pinyin like '%' || :code || '%'")
    public abstract List<StockList> getByGoodId(int code);
}
