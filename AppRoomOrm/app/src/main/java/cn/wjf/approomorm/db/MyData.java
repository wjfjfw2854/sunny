package cn.wjf.approomorm.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyData {

    @PrimaryKey
    @ColumnInfo(name="id")
    public int idX;

    @ColumnInfo(name="name")
    public String nameX;

    @ColumnInfo(name="content")
    public String contentX;
}
