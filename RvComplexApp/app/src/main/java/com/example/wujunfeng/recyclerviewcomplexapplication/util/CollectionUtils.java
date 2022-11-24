package com.example.wujunfeng.recyclerviewcomplexapplication.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class CollectionUtils {
    public static boolean isEmpty(int[] ary) {
        return ary == null || ary.length == 0;
    }

    public static boolean isEmpty(List ary) {
        return isNULL(ary) || ary.size() < 1;
    }

    public static boolean isEmpty(Object[] ary) {
        return isNULL(ary) || ary.length < 1;
    }

    public static boolean isEmpty(Map ary) {
        return isNULL(ary) || ary.size() < 1;
    }

    public static boolean isEmpty(Set ary) {
        return isNULL(ary) || ary.size() < 1;
    }

    public static boolean isEmpty(Vector ary) {
        return isNULL(ary) || ary.size() < 1;
    }

    public static boolean isNULL(List ary) {
        return ary == null;
    }

    public static boolean isNULL(Object[] ary) {
        return ary == null;
    }

    public static boolean isNULL(Map ary) {
        return ary == null;
    }

    public static boolean isNULL(Set ary) {
        return ary == null;
    }

    public static boolean isNULL(Vector ary) {
        return ary == null;
    }

    public static boolean isInRange(Object[] ary, int index) {
        if (isEmpty(ary)) {
            return false;
        }
        if (index < 0) {
            return false;
        }
        if (index >= ary.length) {
            return false;
        }
        return true;
    }

    public static boolean isInRange(List ary, int index) {
        if (isEmpty(ary)) {
            return false;
        }
        if (index < 0) {
            return false;
        }
        if (index >= ary.size()) {
            return false;
        }
        return true;
    }

    public static boolean isInRange(Vector ary, int index) {
        if (isEmpty(ary)) {
            return false;
        }
        if (index < 0) {
            return false;
        }
        if (index >= ary.size()) {
            return false;
        }
        return true;
    }

    public static boolean isOutRange(Object[] ary, int index) {
        return !isInRange(ary, index);
    }

    public static boolean isOutRange(Vector ary, int index) {
        return !isInRange(ary, index);
    }

    public static boolean isOutRange(List ary, int index) {
        return !isInRange(ary, index);
    }


    public static int[] Integer2int(Integer[] integers) {
        int[] ints = new int[integers.length];
        for (int i = 0; i < integers.length; i++) {
            ints[i] = integers[i];
        }
        return ints;
    }

    public static int[]Integer2int(List<Integer> integers){
        return Integer2int(integers.toArray(new Integer[integers.size()]));
    }



}
