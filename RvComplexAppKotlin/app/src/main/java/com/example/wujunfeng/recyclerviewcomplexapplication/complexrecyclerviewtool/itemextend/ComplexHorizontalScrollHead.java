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

public class ComplexHorizontalScrollHead extends ItemBaseComplexRecycler {

    private CellScroll cellScrollHeadLeft;
    private CellOut celloutHead;
    private CellScroll cellScrollHeadRight;

    private ScrollHelper scrollHelper;

    public ComplexHorizontalScrollHead(View itemView, LayoutInflater inflater, Object[] args) {
        super(itemView, inflater, args);
    }

    @Override
    public void initView() {
        scrollHelper = (ScrollHelper)args[0];
        cellScrollHeadLeft = (CellScroll) findViewById(R.id.cellScrollHeadLeft);
        celloutHead = (CellOut)findViewById(R.id.celloutHead);
        cellScrollHeadRight = (CellScroll)findViewById(R.id.cellScrollHeadRight);
        cellScrollHeadLeft.initSmalls(scrollHelper.spaceHeadLeft);
        cellScrollHeadRight.initSmalls(scrollHelper.spaceHeadRight);

//        scrollHelper.cellOuts0.add(celloutHead);
    }

    @Override
    public void bindData(Object data, int pos) {
        if(data == null)
            return;
        if(data instanceof DataReflect) {
            DataReflect dataReflect = (DataReflect)data;
            if(dataReflect.type == RvItemDataType.TYPE_TOP0) {
                scrollHelper.cellOuts0.add(celloutHead);
                celloutHead.scrollTo(scrollHelper.curScrollX, 0);
            }
            else if(dataReflect.type == RvItemDataType.TYPE_TOP1) {
                scrollHelper.cellOuts1.add(celloutHead);
                celloutHead.scrollTo(scrollHelper.curScrollX1, 0);
            }
        }
        cellScrollHeadLeft.bindData(data,pos);
        cellScrollHeadRight.bindData(data,pos);
    }
}
