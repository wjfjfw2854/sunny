package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.wujunfeng.recyclerviewcomplexapplication.R;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.ComplexDataTemple;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.DataReflect;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.interfaces.HeadClickLis;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.interfaces.ItemClickLis;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.HeadLeftFixed;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.HeadSmall;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.ScrollOneLeftSmall;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.TxtSmall;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.TxtSmallLeft;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.Care4OldersShared;
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
    public float widthLeftPage;

    public String label = "给第1个滚动头部加字段，点击！！！";
    public String labelCare4Olders = Care4OldersShared.isOpenCare4Olders?"老年版列表item高度100dp，点击！！！":"正常版列表item高度32dp，点击！！！";

    public TestMncgChiCangViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        init();
    }

    private void init()
    {
        complexRecyclerAdapter = new ComplexRecyclerAdapter(getApplication());
        smallWidth = (getScreenWidth()) / (Care4OldersShared.isOpenCare4Olders?3f:4f);
//        smallWidth1 = (DeviceUtil.getInstance().getScreenWidth()) / 3f;
        smallWidth1 = smallWidth;
        widthLeftPage = (getScreenWidth() - smallWidth);
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
        for (ComplexDataTemple.DataTemple dt : ComplexDataTemple.DataTemple.values()) {
            dataReflectOut.hashMapHead.put(dt.id,dt.name);
        }
        complexRecyclerAdapter.datas.add(dataReflectOut);
        for(int i = 0;i < 15;i ++) {
            DataReflect dataReflect = new DataReflect();
            dataReflect.type = RvItemDataType.TYPE_TOP0;
            int k = 0;
            for (ComplexDataTemple.DataTemple dt : ComplexDataTemple.DataTemple.values()) {
                dataReflect.hashMapItem.put(dt.id,k == 0?"变战术" + i:(k%2==0?"888888888888":"-999"));
                k ++;
            }
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

    public void refreshData() {
        if (complexRecyclerAdapter != null) {
            rightHeads(true);
            rightSpaces(true);
            List<Object> list = complexRecyclerAdapter.datas;
            if (list.size() > 1) {
                Object obj = list.get(1);
                if (obj instanceof DataReflect) {
                    DataReflect dataReflect = ((DataReflect)obj);
                    for (ComplexDataTemple.DataTempleAddTest dataTempleAddTest:ComplexDataTemple.DataTempleAddTest.values()) {
                        dataReflect.hashMapHead.put(dataTempleAddTest.id,dataTempleAddTest.name);
                    }
                }
                for (int i = 2;i < list.size();i ++) {
                    Object o = list.get(i);
                    if (o instanceof DataReflect) {
                        DataReflect dataReflect = (DataReflect) o;
                        if (dataReflect.type == RvItemDataType.TYPE_TOP0) {
                            int k = 0;
                            for (ComplexDataTemple.DataTempleAddTest dataTempleAddTest:ComplexDataTemple.DataTempleAddTest.values()) {
                                dataReflect.hashMapItem.put(dataTempleAddTest.id, (k % 2 == 0?"-310":"310") + k);
                                k ++;
                            }
                        } else {
                            break;
                        }
                    }

                }
                complexRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initSmallSpace()
    {
        int[] sortDrawIdLeft = new int[]{
                R.mipmap.tzt_hqtab_select_arrow_down,
                R.mipmap.tzt_hqtab_select_arrow_up
        };
        int widthLeft = DrawTool.dp2Px(getApplication(),14);
        int heightLeft = DrawTool.dp2Px(getApplication(),9);
        int[] whLeft = new int[] {widthLeft,heightLeft};
        HeadClickLis headClickLisLeft = new HeadClickLis() {
            @Override
            public void clickHead(String titleName,int positionRow,int indexColumn) {
                Toast.makeText(getApplication(),"点击左边列-titleName="+titleName+"-第positionRow=" + positionRow + "行,第indexColumn="+indexColumn+"列",Toast.LENGTH_SHORT).show();
            }
        };
        leftHeadSpace.add(new SmallSpace(ComplexDataTemple.DataTemple.NAEM, HeadLeftFixed.class,false,smallWidth,new Object[]{DrawTool.LEFT,sortDrawIdLeft,whLeft,headClickLisLeft}));
        rightHeads(false);

        ItemClickLis itemClickLisLeft = new ItemClickLis() {
            @Override
            public void clickItem(String val, int positionRow, int indexColumn) {
                Toast.makeText(getApplication(),"点击左边列-val="+val+"-第positionRow=" + positionRow + "行,第indexColumn="+indexColumn+"列",Toast.LENGTH_SHORT).show();
            }
        };
        leftSpace.add(new SmallSpace(ComplexDataTemple.DataTemple.NAEM, TxtSmallLeft.class,false,smallWidth,new Object[]{DrawTool.LEFT,null,null,itemClickLisLeft,null}));
        rightSpaces(false);


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

    private void rightSpaces(boolean needAdd) {
        ItemClickLis itemClickLisRight = new ItemClickLis() {
            @Override
            public void clickItem(String val, int positionRow, int indexColumn) {
                Toast.makeText(getApplication(),"点击右边可左右滚动列-val="+val+"-第positionRow=" + positionRow + "行,第indexColumn="+indexColumn+"列",Toast.LENGTH_SHORT).show();
            }
        };
        int[][] bgAndGag = new int[][]{
                {0xFFD02541,0xff089900,0xff9d9d9d},//背景:涨-红，跌-绿，默认-灰
                {DrawTool.dp2Px(getApplication(),5)},//背景框的间距
                {0xffffffff,0xff212121}//黑白版字体颜色
        };
        boolean forbidScroll = false;//是否需要参与滚动
        if (!needAdd) {
            for (int i = 1; i < ComplexDataTemple.DataTemple.values().length; i++) {
                if (i == 2) {
                    rightSpace.add(new SmallSpace(new Object[]{ComplexDataTemple.DataTemple.values()[i-1],ComplexDataTemple.DataTemple.values()[i]}, ScrollOneLeftSmall.class, true,i == 2 ? widthLeftPage : smallWidth, new Object[]{DrawTool.RIGHT, null, null, itemClickLisRight, bgAndGag, i == 2 ? !forbidScroll : forbidScroll}));
                } else if (i > 2) {
                    rightSpace.add(new SmallSpace(ComplexDataTemple.DataTemple.values()[i], TxtSmall.class,false, i == 2 ? widthLeftPage : smallWidth, new Object[]{DrawTool.RIGHT, null, null, itemClickLisRight, bgAndGag, i == 2 ? !forbidScroll : forbidScroll}));
                }
            }
        } else {
            for (int i = 0; i < ComplexDataTemple.DataTempleAddTest.values().length; i++) {
                rightSpace.add(new SmallSpace(ComplexDataTemple.DataTempleAddTest.values()[i], TxtSmall.class, false,smallWidth, new Object[]{DrawTool.RIGHT, null, null, itemClickLisRight, bgAndGag,forbidScroll}));
            }
        }
    }

    private void rightHeads(boolean needAdd) {
        int[] sortDrawId = new int[]{
                R.mipmap.tzt_userstock_pauxu_default,
                R.mipmap.tzt_userstock_pauxu_down,
                R.mipmap.tzt_userstock_pauxu_up
        };
        int width = DrawTool.dp2Px(getApplication(),7);
        int height = DrawTool.dp2Px(getApplication(),13);
        HeadClickLis headClickLisRight = new HeadClickLis() {
            @Override
            public void clickHead(String titleName,int positionRow,int indexColumn) {
                Toast.makeText(getApplication(),"点击右边可左右滚动-titleName="+titleName+"-第positionRow=" + positionRow + "行,第indexColumn="+indexColumn+"列",Toast.LENGTH_SHORT).show();
            }
        };
        if (!needAdd) {
            for (int i = 1; i < ComplexDataTemple.DataTemple.values().length; i++) {
                int[] wh = new int[]{width, height};
                rightHeadSpace.add(new SmallSpace(ComplexDataTemple.DataTemple.values()[i], HeadSmall.class, i == 1 || i == 2?true:false,i == 1 || i == 2?widthLeftPage/2:smallWidth, new Object[]{DrawTool.RIGHT, sortDrawId, wh, headClickLisRight}));
            }
        } else {
            for (int i = 0; i < ComplexDataTemple.DataTempleAddTest.values().length; i++) {
                int[] wh = new int[]{width, height};
                rightHeadSpace.add(new SmallSpace(ComplexDataTemple.DataTempleAddTest.values()[i], HeadSmall.class, false,smallWidth, new Object[]{DrawTool.RIGHT, sortDrawId, wh, headClickLisRight}));
            }
        }
    }

    public int getScreenWidth() {
        return contextIsNull(application) ? 0 : application.getResources().getDisplayMetrics().widthPixels;
    }

    public boolean contextIsNull(Context context) {
        return context == null;
    }

    private void care4Olders3Columns() {
        smallWidth = (getScreenWidth()) / (Care4OldersShared.isOpenCare4Olders?3f:4f);
        smallWidth1 = smallWidth;
        widthLeftPage = (getScreenWidth() - smallWidth);
        if (leftHeadSpace != null && !leftHeadSpace.isEmpty()) {
            int size = leftHeadSpace.size();
            for (int i = 0;i < size;i ++) {
                SmallSpace ss = leftHeadSpace.get(i);
                ss.width = smallWidth;
            }
        }
        if (rightHeadSpace != null && !rightHeadSpace.isEmpty()) {
            int size = rightHeadSpace.size();
            for (int i = 0;i < size;i ++) {
                SmallSpace ss = rightHeadSpace.get(i);
                if (ss.isNeedDealWidthSpecial()) {//(i == 0 || i == 1) {
                    ss.width = widthLeftPage/2;
                } else {
                    ss.width = smallWidth;
                }
            }
        }
        if (leftSpace != null && !leftSpace.isEmpty()) {
            int size = leftSpace.size();
            for (int i = 0;i < size;i ++) {
                SmallSpace ss = leftSpace.get(i);
                ss.width = smallWidth;
            }
        }
        if (rightSpace != null && !rightSpace.isEmpty()) {
            int size = rightSpace.size();
            for (int i = 0;i < size;i ++) {
                SmallSpace ss = rightSpace.get(i);
                if (ss.isNeedDealWidthSpecial()) {//(i == 0) {
                    ss.width = widthLeftPage;
                } else {
                    ss.width = smallWidth;
                }
            }
        }
    }

    public void rvAdapterRefresh() {
//        care4Olders3Columns();
        if (complexRecyclerAdapter != null) {
            complexRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
