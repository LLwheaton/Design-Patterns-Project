package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.ordering.Report;
import com.google.common.primitives.ImmutableDoubleArray;

import java.util.*;

public class ReportDataFactory {

    private static Map<Integer, double[]> arrays = new HashMap();

    public static double[] createArray(double[] array){
//        Report report = reports.computeIfAbsent(report1.hashCode(), newHash ->{
//           Report newReport = new ReportImpl(report1.getReportName(), report1.getCommission(), report1.getLegalData(), report1.getCashFlowData(), report1.getMergesData(), report1.getTallyingData(), report1.getDeductionsData());
//           return newReport;
//        });
//        return report;

//        if(arrays.contains(ImmutableDoubleArray.copyOf(array))){
//            int index = arrays.indexOf(ImmutableDoubleArray.copyOf(array));
//            return arrays.get(index);
//        } else {
//            return ImmutableDoubleArray.copyOf(array);
//        }
        if(arrays.containsKey(Arrays.hashCode(array))){
            return arrays.get(Arrays.hashCode(array));
        } else {
            arrays.put(Arrays.hashCode(array), array);
            return array;
        }
    }
}
