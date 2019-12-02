package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.itemextend;

import android.view.LayoutInflater;
import android.view.View;
import com.example.wujunfeng.recyclerviewcomplexapplication.R;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.baseadpater.ItemBaseComplexRecycler;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellOut;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellScroll;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.DataReflect;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.scrollhelper.ScrollHelper;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.RvItemDataType;

public class ComplexHorizontalScrollItem extends ItemBaseComplexRecycler {

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
        cellScrollItemLeft.bindData(data,pos);
        cellScrollItemRight.bindData(data,pos);
    }
}
