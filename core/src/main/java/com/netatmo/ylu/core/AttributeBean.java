package com.netatmo.ylu.core;

import android.content.res.TypedArray;
import android.util.SparseIntArray;

public class AttributeBean {

    private SparseIntArray sparseIntArray = new SparseIntArray();

    private static final int DEFAULT_VALUE = -1;

    public void saveViewResource(TypedArray typedArray, int[] styleable){
        for (int i = 0;i < typedArray.length() ;i++){
            int key = styleable[i];
            int resourceId = typedArray.getResourceId(i,DEFAULT_VALUE);

            sparseIntArray.put(key, resourceId);
        }
    }

    public int getViewResource(int styleable){
        return sparseIntArray.get(styleable);
    }
}
