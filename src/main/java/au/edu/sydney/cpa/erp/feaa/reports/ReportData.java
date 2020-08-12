// Added class
package au.edu.sydney.cpa.erp.feaa.reports;

import java.util.*;

/**
 * Class uses the Flyweight pattern to cache the values with a Report object. The cached values
 * reduce the RAM usage while using the program by half. The values are stored in maps
 * of each type with key as the hashCode of that value.
 */
public class ReportData {

    private static Map<Integer, double[]> arrayCache = new HashMap();
    private static Map<Integer, String> nameCache = new HashMap<>();
    private static Map<Integer, Double> commissionCache = new HashMap<>();

    /**
     * Checks the array by its hashCode in arrayCache and either
     * returns the cached array or itself if it has not been cached before.
     * @param array to be cached
     * @return The cached array or itself
     */
    public static double[] createArray(double[] array) {
        if(arrayCache.containsKey(Arrays.hashCode(array))) {
            return arrayCache.get(Arrays.hashCode(array));
        } else {
            arrayCache.put(Arrays.hashCode(array), array);
            return array;
        }
    }

    /**
     * Checks hashCode of String and returns the cached String,
     * otherwise adds to cache and returns itself
     * @param name to be cached
     * @return The cached String or itself
     */
    public static String createName(String name) {
        if(nameCache.containsKey(name.hashCode())) {
            return nameCache.get(name.hashCode());
        } else {
            nameCache.put(name.hashCode(), name);
            return name;
        }
    }

    /**
     * Checks hashCode of Double and returns the cached Double,
     * otherwise adds to cache and returns itself
     * @param commission to be cached
     * @return The cached Double or itself
     */
    public static double createCommission(Double commission) {
        if(commissionCache.containsKey(commission.hashCode())) {
            return commissionCache.get(commission.hashCode());
        } else {
            commissionCache.put(commission.hashCode(), commission);
            return commission;
        }
    }
}
