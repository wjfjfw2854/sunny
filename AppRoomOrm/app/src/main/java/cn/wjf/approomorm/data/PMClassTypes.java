package cn.wjf.approomorm.data;

import java.io.Serializable;
import java.util.ArrayList;


public class PMClassTypes implements Serializable {
    public final static int PM_RANK_ID = 0;
    public final static int PM_DAPAN_ID = 1;
    public final static int PM_BK_ID = 2;
    public final static int PM_ZL_ID = 3;
    public final static int PM_ZL5_ID = 4;
    public final static int PM_ZC_ID = 5;

    public ArrayList<Params> classTypes;
    public String templateName;
    public int sortID = Field.ZF.param;
    public int idsType;
    public ArrayList<Integer> codes;//如：板块联想 指数用到传递股票code

    public static class Params implements Serializable {
        public int exchange;
        public long category;
    }

}
