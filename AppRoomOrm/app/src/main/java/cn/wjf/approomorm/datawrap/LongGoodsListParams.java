package cn.wjf.approomorm.datawrap;

import java.util.ArrayList;

import cn.wjf.approomorm.data.Field;
import cn.wjf.approomorm.nano.SortedListRequest;


public class LongGoodsListParams {
    public final static String TAG_LONG_LIST = "长列表";
    public final static String TAG_POSITION_LIST = "区间列表";
    public int pageLength = 15;
    public final static int VISABLE_ITEM_OFFSET = 5;
    public ArrayList<Object> longGoodsList = new ArrayList<>();
    public int position;
    public boolean reqLongArray = false;
    public SortedListRequest.SortedList_Request.ClassTypeList.ClassType[] classTypes;
    public int sort = 0;
    public Field sortID;
    public int[] positionFields;
    @Deprecated
    public SortedListRequest.SortedList_Request.GroupInfo groupInfo;//基本无用
    public int bkGoodsID;

    //弃用，通过longGoodsList cast ArraryList
//    public ArrayList<Integer> longGoodsIntegerList;//主要长列表Integer作为跳转使用
}
