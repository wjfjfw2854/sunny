package cn.wjf.approomorm.pojo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;


import java.util.List;

import cn.wjf.approomorm.R;
import cn.wjf.approomorm.data.Field;
import cn.wjf.approomorm.data.Goods;

public class IndexZpsItemData {
    private List goodsList;
    public Goods goods;
    public String strName;
    public String strCounts;
    public int colorCounts;
    public int drawId;

    public float sZjs;
    public float xDjs;
    public float pPjs;

    private int pos;

    public boolean isRaise;

    public IndexZpsItemData(int pos, List goodsList)
    {
        this.pos = pos;
        this.goodsList = goodsList;
        this.goods = (Goods) goodsList.get(pos);
        strName = goods.getGoodsName();
        String strVal = goods.getValue(Field.SZJS.param);
        String strXdVal = goods.getValue(Field.XDJS.param);
        String strPpVal = goods.getValue(Field.PPJS.param);

        float val = Float.valueOf("".equals(strVal) ? "0" : strVal);
        float valXd = Float.valueOf("".equals(strXdVal) ? "0" : strXdVal);
        float valPp = Float.valueOf("".equals(strPpVal) ? "0" : strPpVal);

        float vol = val + valXd + valPp;
        if(vol != 0) {
            sZjs = val / vol;
            xDjs = valXd / vol;
            pPjs = valPp / vol;
        }

        if(val > 0) {
            isRaise = true;
            colorCounts = 0xED1C24;//Theme.C1;
            drawId = R.mipmap.icon_v3_4_arrowup_red;
        }
        else {
            isRaise = false;
            colorCounts = 0x00ff60;//Theme.C3;
            drawId = 0;
        }
        strCounts = strVal + "只";
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(goodsList.size() > pos) {
//                UB.addEvent("hsfrag", ((Goods) goodsList.get(pos)).getGoodsName());
            }
//            Rt.get(EventID.G_CGOODD)
//                    .withParams("goodIds", RtParam.getGoodsIds(goodsList))
//                    .withParams("currentIndex", pos)
//                    .open();
        }
    };
}
