package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.scrollhelper;


import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellOut;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;

import java.util.ArrayList;
import java.util.List;

public class ScrollHelper {
    public List<SmallSpace> spaceHeadLeft = new ArrayList<>();
    public List<SmallSpace> spaceHeadRight = new ArrayList<>();
    public List<SmallSpace> spaceLeft = new ArrayList<>();
    public List<SmallSpace> spaceRight = new ArrayList<>();

    public List<SmallSpace> spaceHeadLeftOne = new ArrayList<>();
    public List<SmallSpace> spaceHeadRightOne = new ArrayList<>();
    public List<SmallSpace> spaceLeftOne = new ArrayList<>();
    public List<SmallSpace> spaceRightOne = new ArrayList<>();

    public List<SmallSpace> spaceHeadLeftTwo = new ArrayList<>();
    public List<SmallSpace> spaceHeadRightTwo = new ArrayList<>();
    public List<SmallSpace> spaceLeftTwo = new ArrayList<>();
    public List<SmallSpace> spaceRightTwo = new ArrayList<>();

    public List<CellOut> cellOuts0 = new ArrayList<>();
    public int curScrollX;
    public float scrollMaxX;

    public List<CellOut> cellOuts1 = new ArrayList<>();
    public int curScrollX1;
    public float scrollMaxX1;

    public List<CellOut> cellOuts2 = new ArrayList<>();
    public int curScrollX2;
    public float scrollMaxX2;
}
