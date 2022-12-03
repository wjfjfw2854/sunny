package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.itemextend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wujunfeng.recyclerviewcomplexapplication.R;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.baseadpater.ItemBaseComplexRecycler;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellOut;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellScroll;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.DataReflect;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.scrollhelper.ScrollHelper;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.Small;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.Care4OldersShared;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.DrawTool;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.RvItemDataType;

import java.util.List;

public class ComplexHorizontalScrollItem extends ItemBaseComplexRecycler {

    private LinearLayout mLinComplexItem;
    private CellScroll cellScrollItemLeft;
    private CellOut celloutItem;
    private CellScroll cellScrollItemRight;

    private ScrollHelper scrollHelper;

    public ComplexHorizontalScrollItem(View itemView, LayoutInflater inflater, Object[] args) {
        super(itemView, inflater, args);
    }

    @Override
    public void initView() {
        scrollHelper = (ScrollHelper)args[0];
        mLinComplexItem = (LinearLayout)findViewById(R.id.complexItem);
        cellScrollItemLeft = (CellScroll) findViewById(R.id.cellScrollItemLeft);
        celloutItem = (CellOut)findViewById(R.id.celloutItem);
        cellScrollItemRight = (CellScroll)findViewById(R.id.cellScrollItemRight);
        cellScrollItemLeft.initSmalls(scrollHelper.spaceLeft);
        cellScrollItemRight.initSmalls(scrollHelper.spaceRight);

//        scrollHelper.cellOuts0.add(celloutItem);
    }

    @Override
    public void bindData(Object data, int pos) {
        if(data == null)
            return;
        if (mLinComplexItem != null) {
            ViewGroup.LayoutParams lp = mLinComplexItem.getLayoutParams();
            int heightNormal = DrawTool.dp2Px(getContext(),32);
            lp.height = heightNormal;
            int heightCare4Olders = DrawTool.dp2Px(getContext(),100);
            if (Care4OldersShared.isOpenCare4Olders) {
                lp.height = heightCare4Olders;
            }
            mLinComplexItem.setLayoutParams(lp);
        }
        if(data instanceof DataReflect) {
            DataReflect dataReflect = (DataReflect)data;
            if(dataReflect.type == RvItemDataType.TYPE_TOP0) {
                scrollHelper.cellOuts0.add(celloutItem);
                celloutItem.scrollTo(scrollHelper.curScrollX, 0);
            }
            else if(dataReflect.type == RvItemDataType.TYPE_TOP1) {
                scrollHelper.cellOuts1.add(celloutItem);
                celloutItem.scrollTo(scrollHelper.curScrollX1, 0);
            }
        }
        cellScrollItemLeft.bindData(data,pos,true);
        List<Small> list = cellScrollItemRight.getSmalls();
        if (list != null) {
            int size = list.size();
            if (size > 0 && size < scrollHelper.spaceRight.size()) {
                cellScrollItemRight.setSmalls(scrollHelper.spaceRight);
            }
        }
        cellScrollItemRight.bindData(data,pos,false);
    }
}
