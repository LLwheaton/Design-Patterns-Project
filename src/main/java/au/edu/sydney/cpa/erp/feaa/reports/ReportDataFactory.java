package au.edu.sydney.cpa.erp.feaa.reports;

import java.util.*;

public class ReportDataFactory {

    private static Map<Integer, double[]> arrays = new HashMap();

    public static double[] createArray(double[] array){

        if(arrays.containsKey(Arrays.hashCode(array))){
            return arrays.get(Arrays.hashCode(array));
        } else {
            arrays.put(Arrays.hashCode(array), array);
            return array;
        }
    }
}
