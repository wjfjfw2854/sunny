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

public class ComplexHorizontalScrollHead extends ItemBaseComplexRecycler {

    private LinearLayout complexHead;
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
        complexHead = (LinearLayout) findViewById(R.id.complexHead);
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
        if (complexHead != null) {
            ViewGroup.LayoutParams lp = complexHead.getLayoutParams();
            int heightNormal = DrawTool.dp2Px(getContext(),32);
            lp.height = heightNormal;
            int heightCare4Olders = DrawTool.dp2Px(getContext(),32*1.2f);
            if (Care4OldersShared.isOpenCare4Olders) {
                lp.height = heightCare4Olders;
            }
            complexHead.setLayoutParams(lp);
        }
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
        cellScrollHeadLeft.bindData(data,pos,true);
        List<Small> list = cellScrollHeadRight.getSmalls();
        if (list != null) {
            int size = list.size();
            if (size > 0 && size < scrollHelper.spaceHeadRight.size()) {
                cellScrollHeadRight.setSmalls(scrollHelper.spaceHeadRight);
            }
        }
        cellScrollHeadRight.bindData(data,pos,false);
    }
}
