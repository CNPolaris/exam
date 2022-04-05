package com.polaris.exam.utils;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class AnalyzeUtil {
    /**
     * 成绩转double数组
     * @param scoreList List<Integer>
     * @return double[]
     */
    public static double[] scoreArray(List<Integer> scoreList){
        int len = scoreList.size();
        double[] scoreArray= new double[len];
        for(int i=0;i<len;i++){
            scoreArray[i] = scoreList.get(i).doubleValue();
        }
        return scoreArray;
    }
}
