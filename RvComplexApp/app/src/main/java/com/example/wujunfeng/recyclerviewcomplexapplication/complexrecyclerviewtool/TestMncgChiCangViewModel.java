package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.wujunfeng.recyclerviewcomplexapplication.R;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.ComplexDataTemple;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.DataReflect;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.interfaces.HeadClickLis;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.HeadSmall;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.TxtSmall;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.DrawTool;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.RvItemDataType;
import com.example.wujunfeng.recyclerviewcomplexapplication.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;


public class TestMncgChiCangViewModel extends BaseViewModel {
    private final Application application;
    public ComplexRecyclerAdapter complexRecyclerAdapter;
    public List<SmallSpace> leftHeadSpace = new ArrayList<>();
    public List<SmallSpace> rightHeadSpace = new ArrayList<>();
    public List<SmallSpace> leftSpace = new ArrayList<>();
    public List<SmallSpace> rightSpace = new ArrayList<>();

    public List<SmallSpace> leftHeadSpaceOne = new ArrayList<>();
    public List<SmallSpace> rightHeadSpaceOne = new ArrayList<>();
    public List<SmallSpace> leftSpaceOne = new ArrayList<>();
    public List<SmallSpace> rightSpaceOne = new ArrayList<>();

    public List<SmallSpace> leftHeadSpaceTwo = new ArrayList<>();
    public List<SmallSpace> rightHeadSpaceTwo = new ArrayList<>();
    public List<SmallSpace> leftSpaceTwo = new ArrayList<>();
    public List<SmallSpace> rightSpaceTwo = new ArrayList<>();

    public float smallWidth;
    public float smallWidth1;

    public TestMncgChiCangViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        init();
    }

    private void init()
    {
        complexRecyclerAdapter = new ComplexRecyclerAdapter(getApplication());
        smallWidth = (getScreenWidth()) / 4f;
//        smallWidth1 = (DeviceUtil.getInstance().getScreenWidth()) / 3f;
        smallWidth1 = smallWidth;
        initSmallSpace();
        initData();
    }

    public void initData()
    {
        complexRecyclerAdapter.datas.clear();
        DataReflect dataReflectHeadTop = new DataReflect();
        for(int i = 0;i < 1;i ++) {
            complexRecyclerAdapter.datas.add(dataReflectHeadTop);
        }
        DataReflect dataReflectOut = new DataReflect();
        dataReflectOut.type = RvItemDataType.TYPE_TOP0;
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.NAEM.id,ComplexDataTemple.DataTemple.NAEM.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.ZDF.id,ComplexDataTemple.DataTemple.ZDF.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.YK.id,ComplexDataTemple.DataTemple.YK.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.YKL.id,ComplexDataTemple.DataTemple.YKL.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.CBJ.id,ComplexDataTemple.DataTemple.CBJ.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.ZXJ.id,ComplexDataTemple.DataTemple.ZXJ.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.CCGS.id,ComplexDataTemple.DataTemple.CCGS.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.KMGS.id,ComplexDataTemple.DataTemple.KMGS.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.KMGSZJ1.id,ComplexDataTemple.DataTemple.KMGSZJ1.name);
        dataReflectOut.hashMapHead.put(ComplexDataTemple.DataTemple.KMGSZJ2.id,ComplexDataTemple.DataTemple.KMGSZJ2.name);
        complexRecyclerAdapter.datas.add(dataReflectOut);
        for(int i = 0;i < 15;i ++) {
            DataReflect dataReflect = new DataReflect();
            dataReflect.type = RvItemDataType.TYPE_TOP0;
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.NAEM.id, "变术" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.ZDF.id, "10.00%" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.YK.id, "600" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.YKL.id, "70" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.CBJ.id, "50" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.ZXJ.id, "40" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.CCGS.id, "30" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.KMGS.id, "20" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.KMGSZJ1.id, "10" + i);
            dataReflect.hashMapItem.put(ComplexDataTemple.DataTemple.KMGSZJ2.id, "210" + i);
            complexRecyclerAdapter.datas.add(dataReflect);
        }


        DataReflect dataReflectOut1 = new DataReflect();
        dataReflectOut1.type = RvItemDataType.TYPE_TOP1;
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.NAEM.id,ComplexDataTemple.DataTempleOne.NAEM.name);
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.ZDF.id,ComplexDataTemple.DataTempleOne.ZDF.name);
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.YK.id,ComplexDataTemple.DataTempleOne.YK.name);
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.YKL.id,ComplexDataTemple.DataTempleOne.YKL.name);
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.CBJ.id,ComplexDataTemple.DataTempleOne.CBJ.name);
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.ZXJ.id,ComplexDataTemple.DataTempleOne.ZXJ.name);
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.ZXJ1.id,ComplexDataTemple.DataTempleOne.ZXJ1.name);
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.ZXJ2.id,ComplexDataTemple.DataTempleOne.ZXJ2.name);
        dataReflectOut1.hashMapHeadOne.put(ComplexDataTemple.DataTempleOne.ZXJ3.id,ComplexDataTemple.DataTempleOne.ZXJ3.name);
        complexRecyclerAdapter.datas.add(dataReflectOut1);
        for(int i = 0;i < 29;i ++) {
            DataReflect dataReflect1 = new DataReflect();
            dataReflect1.type = RvItemDataType.TYPE_TOP1;
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.NAEM.id, "洪都航空" + i);
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.ZDF.id, "10.00%" + i);
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.YK.id, "600" + i);
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.YKL.id, "70" + i);
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.CBJ.id, "50" + i);
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.ZXJ.id, "40" + i);
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.ZXJ1.id, "30" + i);
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.ZXJ2.id, "20" + i);
            dataReflect1.hashMapItemOne.put(ComplexDataTemple.DataTempleOne.ZXJ3.id, "10" + i);
            complexRecyclerAdapter.datas.add(dataReflect1);
        }

        DataReflect dataReflectOut2 = new DataReflect();
        dataReflectOut2.type = RvItemDataType.TYPE_TOP2;
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.NAEM.id,ComplexDataTemple.DataTempleTwo.NAEM.name);
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.ZDF.id,ComplexDataTemple.DataTempleTwo.ZDF.name);
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.YK.id,ComplexDataTemple.DataTempleTwo.YK.name);
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.YKL.id,ComplexDataTemple.DataTempleTwo.YKL.name);
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.CBJ.id,ComplexDataTemple.DataTempleTwo.CBJ.name);
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.ZXJ.id,ComplexDataTemple.DataTempleTwo.ZXJ.name);
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.ZXJ1.id,ComplexDataTemple.DataTempleTwo.ZXJ1.name);
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.ZXJ2.id,ComplexDataTemple.DataTempleTwo.ZXJ2.name);
        dataReflectOut2.hashMapHeadTwo.put(ComplexDataTemple.DataTempleTwo.ZXJ3.id,ComplexDataTemple.DataTempleTwo.ZXJ3.name);
        complexRecyclerAdapter.datas.add(dataReflectOut2);
        for(int i = 0;i < 17;i ++) {
            DataReflect dataReflect2 = new DataReflect();
            dataReflect2.type = RvItemDataType.TYPE_TOP2;
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.NAEM.id, "股池" + i);
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.ZDF.id, "10.00%" + i);
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.YK.id, "600" + i);
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.YKL.id, "70" + i);
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.CBJ.id, "50" + i);
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.ZXJ.id, "40" + i);
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.ZXJ1.id, "30" + i);
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.ZXJ2.id, "20" + i);
            dataReflect2.hashMapItemTwo.put(ComplexDataTemple.DataTempleTwo.ZXJ3.id, "10" + i);
            complexRecyclerAdapter.datas.add(dataReflect2);
        }
        complexRecyclerAdapter.notifyDataSetChanged();
    }

    private void initSmallSpace()
    {
        int[] sortDrawId = new int[]{
                R.mipmap.tzt_userstock_pauxu_default,
                R.mipmap.tzt_userstock_pauxu_down,
                R.mipmap.tzt_userstock_pauxu_up
        };
        HeadClickLis headClickLisLeft = new HeadClickLis() {
            @Override
            public void clickHead(String titleName,int positionRow,int indexColumn) {
                Toast.makeText(getApplication(),"点击的是-titleName="+titleName+"-第positionRow=" + positionRow + "行,第indexColumn="+indexColumn+"列",Toast.LENGTH_SHORT).show();
            }
        };
        leftHeadSpace.add(new SmallSpace(ComplexDataTemple.DataTemple.NAEM, HeadSmall.class,smallWidth,new Object[]{DrawTool.LEFT,null,null,headClickLisLeft}));
        int width = DrawTool.dp2Px(getApplication(),7);
        int height = DrawTool.dp2Px(getApplication(),13);
        HeadClickLis headClickLisRight = new HeadClickLis() {
            @Override
            public void clickHead(String titleName,int positionRow,int indexColumn) {
                Toast.makeText(getApplication(),"点击的是-titleName="+titleName+"-第positionRow=" + positionRow + "行,第indexColumn="+indexColumn+"列",Toast.LENGTH_SHORT).show();
            }
        };
        for(int i = 1; i < ComplexDataTemple.DataTemple.values().length;i ++) {
            int[] wh = new int[] {width,height};
            rightHeadSpace.add(new SmallSpace(ComplexDataTemple.DataTemple.values()[i], HeadSmall.class, smallWidth,new Object[]{DrawTool.RIGHT,sortDrawId,wh,headClickLisRight}));
        }

        leftSpace.add(new SmallSpace(ComplexDataTemple.DataTemple.NAEM, TxtSmall.class,smallWidth,new Object[]{DrawTool.LEFT,null}));
        for(int i = 1; i < ComplexDataTemple.DataTemple.values().length;i ++) {
            rightSpace.add(new SmallSpace(ComplexDataTemple.DataTemple.values()[i], TxtSmall.class, smallWidth,new Object[]{DrawTool.RIGHT,null}));
        }


        leftHeadSpaceOne.add(new SmallSpace(ComplexDataTemple.DataTempleOne.NAEM, HeadSmall.class,smallWidth1));
        for(int i = 1; i < ComplexDataTemple.DataTempleOne.values().length;i ++) {
            rightHeadSpaceOne.add(new SmallSpace(ComplexDataTemple.DataTempleOne.values()[i], HeadSmall.class, smallWidth1));
        }

        leftSpaceOne.add(new SmallSpace(ComplexDataTemple.DataTempleOne.NAEM, TxtSmall.class,smallWidth1));
        for(int i = 1; i < ComplexDataTemple.DataTempleOne.values().length;i ++) {
            rightSpaceOne.add(new SmallSpace(ComplexDataTemple.DataTempleOne.values()[i], TxtSmall.class, smallWidth1));
        }

        leftHeadSpaceTwo.add(new SmallSpace(ComplexDataTemple.DataTempleTwo.NAEM, HeadSmall.class,smallWidth1));
        for(int i = 1; i < ComplexDataTemple.DataTempleTwo.values().length;i ++) {
            rightHeadSpaceTwo.add(new SmallSpace(ComplexDataTemple.DataTempleTwo.values()[i], HeadSmall.class, smallWidth1));
        }

        leftSpaceTwo.add(new SmallSpace(ComplexDataTemple.DataTempleTwo.NAEM, TxtSmall.class,smallWidth1));
        for(int i = 1; i < ComplexDataTemple.DataTempleTwo.values().length;i ++) {
            rightSpaceTwo.add(new SmallSpace(ComplexDataTemple.DataTempleTwo.values()[i], TxtSmall.class, smallWidth1));
        }

    }

    public int getScreenWidth() {
        return contextIsNull(application) ? 0 : application.getResources().getDisplayMetrics().widthPixels;
    }

    public boolean contextIsNull(Context context) {
        return context == null;
    }

//    private int getBackgroundRes(double value) {
//        if (value > 0) {
//            return R.drawable.mncg_secu_share_item_ylk_up_bg;
//        } else if (value < 0) {
//            return R.drawable.mncg_secu_share_item_ylk_down_bg;
//        } else {
//            return R.drawable.mncg_secu_share_item_ylk_normal_bg;
//        }
//    }
}
