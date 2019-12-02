package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool;

import android.content.Context;
import android.util.SparseArray;
import com.example.wujunfeng.recyclerviewcomplexapplication.R;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.baseadpater.MutiComplexRecyclerItemAdapter;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.DataReflect;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.itemextend.*;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.scrollhelper.ScrollHelper;

import java.util.HashMap;

public class ComplexRecyclerAdapter extends MutiComplexRecyclerItemAdapter {
    private ScrollHelper scrollHelper;

    public ComplexRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    protected void initTemplates(SparseArray<Class> templates, SparseArray<Object[]> argsMap) {

        templates.put(R.layout.complexheadtop, ComplexHorizontalFixedHeadTop.class);
        argsMap.put(R.layout.complexheadtop,null);

        templates.put(R.layout.complexrecyclehead, ComplexHorizontalScrollHead.class);
        argsMap.put(R.layout.complexrecyclehead,new Object[]{scrollHelper});
        templates.put(R.layout.complexrecyclescrollhorizontal_item, ComplexHorizontalScrollItem.class);
        argsMap.put(R.layout.complexrecyclescrollhorizontal_item,new Object[]{scrollHelper});

        templates.put(R.layout.complexrecycleheadone, ComplexHorizontalScrollHeadOne.class);
        argsMap.put(R.layout.complexrecycleheadone,new Object[]{scrollHelper});
        templates.put(R.layout.complexrecyclerscrollitem_one, ComplexHorizontalScrollItemOne.class);
        argsMap.put(R.layout.complexrecyclerscrollitem_one,new Object[]{scrollHelper});

        templates.put(R.layout.complexrecycleheadtwo, ComplexHorizontalScrollHeadTwo.class);
        argsMap.put(R.layout.complexrecycleheadtwo,new Object[]{scrollHelper});
        templates.put(R.layout.complexrvitem_two, ComplexHscrollItemTwo.class);
        argsMap.put(R.layout.complexrvitem_two,new Object[]{scrollHelper});
    }

    @Override
    public int getItemViewType(int position) {
        HashMap<Integer,String> drfMapHead = (HashMap)((DataReflect)datas.get(position)).hashMapHead;
        HashMap<Integer,String> drfMapItem = (HashMap)((DataReflect)datas.get(position)).hashMapItem;

        HashMap<Integer,String> drfMapHeadOne = (HashMap)((DataReflect)datas.get(position)).hashMapHeadOne;
        HashMap<Integer,String> drfMapItemOne = (HashMap)((DataReflect)datas.get(position)).hashMapItemOne;

        HashMap<Integer,String> drfMapHeadTwo = (HashMap)((DataReflect)datas.get(position)).hashMapHeadTwo;
        HashMap<Integer,String> drfMapItemTwo = (HashMap)((DataReflect)datas.get(position)).hashMapItemTwo;
        if(drfMapHead.size() == 0
                && drfMapItem.size() == 0
                && drfMapHeadOne.size() == 0
                && drfMapItemOne.size() == 0
                && drfMapHeadTwo.size() == 0
                && drfMapItemTwo.size() == 0
        )
        {
            return R.layout.complexheadtop;
        }
        else if(drfMapHead.size() != 0)
        {
            return R.layout.complexrecyclehead;
        }
        else if(drfMapItem.size() != 0)
        {
            return R.layout.complexrecyclescrollhorizontal_item;
        }
        else if(drfMapHeadOne.size() != 0)
        {
            return R.layout.complexrecycleheadone;
        }
        else if(drfMapItemOne.size() != 0)
        {
            return R.layout.complexrecyclerscrollitem_one;
        }
        else if(drfMapHeadTwo.size() != 0)
        {
            return R.layout.complexrecycleheadtwo;
        }
        else if(drfMapItemTwo.size() != 0)
        {
            return R.layout.complexrvitem_two;
        }
        return 0;
    }

    public void setScrollHelper(ScrollHelper scrollHelper) {
        this.scrollHelper = scrollHelper;
    }
}
