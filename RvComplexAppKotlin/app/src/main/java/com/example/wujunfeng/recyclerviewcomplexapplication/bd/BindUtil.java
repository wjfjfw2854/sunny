package com.example.wujunfeng.recyclerviewcomplexapplication.bd;

import android.databinding.BindingAdapter;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.complexrecyclerview.ComplexRecyclerView;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;

import java.util.List;

public class BindUtil {
    @BindingAdapter({"leftHeadSpace",
            "rightHeadSpace",
            "leftSpace",
            "rightSpace",
            "leftHeadSpaceOne",
            "rightHeadSpaceOne",
            "leftSpaceOne",
            "rightSpaceOne",
            "leftHeadSpaceTwo",
            "rightHeadSpaceTwo",
            "leftSpaceTwo",
            "rightSpaceTwo"
    })
    public static void setSmallSpace(ComplexRecyclerView complexRecyclerView,
                                     List<SmallSpace> leftHeadSpace,
                                     List<SmallSpace> rightHeadSpace,
                                     List<SmallSpace> leftSpace,
                                     List<SmallSpace> rightSpace,
                                     List<SmallSpace> leftHeadSpaceOne,
                                     List<SmallSpace> rightHeadSpaceOne,
                                     List<SmallSpace> leftSpaceOne,
                                     List<SmallSpace> rightSpaceOne,
                                     List<SmallSpace> leftHeadSpaceTwo,
                                     List<SmallSpace> rightHeadSpaceTwo,
                                     List<SmallSpace> leftSpaceTwo,
                                     List<SmallSpace> rightSpaceTwo
    )
    {
        complexRecyclerView.setSmallSpace(leftHeadSpace,
                rightHeadSpace,
                leftSpace,
                rightSpace,
                leftHeadSpaceOne,
                rightHeadSpaceOne,
                leftSpaceOne,
                rightSpaceOne,
                leftHeadSpaceTwo,
                rightHeadSpaceTwo,
                leftSpaceTwo,
                rightSpaceTwo
        );
    }
}
