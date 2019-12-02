package cn.wjf.approomorm.db.stockdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stocklist")
public class StockList {
    @PrimaryKey
    public int goodid;
    public String name;
    public String code;
    public String pinyin;
    public int exchange;
    public long category;
    public int trade_session;
    public int version;
    public int weight;
    public String format_name;
}
