package com.javagyan.example.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Common utility methods
 * @author Sanjeev Kumar
 */
public class CommonUtil {

    /** Constant to avoid repeated object creation */
    private static Integer INTEGER_ONE = new Integer(1);

    /**
     * Null-safe check if the specified collection is empty. Null returns true.
     * @param the collection to check, may be null.
     * @return true if empty or null.
     */
    public static boolean isEmptyCollection(final Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * Checks if a String is empty ("") or null.
     * @param str the String to check, may be null.
     * @return true if the String is empty or null.
     */
    public static boolean isEmpty(final String str) {
        return str == null || str.length() == 0;
    }

    /**
     * Returns a Collection containing the union of the given Collection s. The cardinality of each element in the
     * returned Collection will be equal to the maximum of the cardinality of that element in the two given Collection
     * s.
     * @param <T>
     * @param a
     * @param b
     * @return
     */
    public static <T> Collection<T> union(final Collection<T> a, final Collection<T> b) {
        final List<T> list = new ArrayList<T>();
        final Map<T, Integer> mapa = getCardinalityMap(a);
        final Map<T, Integer> mapb = getCardinalityMap(b);
        final Set<T> elts = new HashSet<T>(a);
        elts.addAll(b);
        if (!isEmptyCollection(elts)) {
            for (final T obj : elts) {
                for (int i = 0, m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++) {
                    list.add(obj);
                }
            }
        }
        return list;
    }

    /**
     * Returns a Collection containing the intersection of the given Collection s. The cardinality of each element in
     * the returned Collection will be equal to the minimum of the cardinality of that element in the two given
     * Collection s.
     * @param <T>
     * @param a the first collection, must not be null.
     * @param b the second collection, must not be null.
     * @return the intersection of the two collections.
     */
    public static <T> Collection<T> intersection(final Collection<T> a, final Collection<T> b) {
        final List<T> list = new ArrayList<T>();
        final Map<T, Integer> mapa = getCardinalityMap(a);
        final Map<T, Integer> mapb = getCardinalityMap(b);
        final Set<T> elts = new HashSet<T>(a);
        elts.addAll(b);
        if (!isEmptyCollection(elts)) {
            for (final T obj : elts) {
                for (int i = 0, m = Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++) {
                    list.add(obj);
                }
            }
        }
        return list;
    }

    /**
     * Returns a Map mapping each unique element in the given Collection to an Integer representing the number of
     * occurrences of that element in the Collection. Only those elements present in the collection will appear as keys
     * in the map.
     * @param <T>
     * @param coll the collection to get the cardinality map for, must not be null.
     * @return the populated cardinality map.
     */
    public static <T> Map<T, Integer> getCardinalityMap(final Collection<T> coll) {
        final Map<T, Integer> count = new HashMap<T, Integer>();
        if (!isEmptyCollection(coll)) {
            for (final T obj : coll) {
                final Integer c = (count.get(obj));
                if (c == null) {
                    count.put(obj, INTEGER_ONE);
                } else {
                    count.put(obj, new Integer(c.intValue() + 1));
                }
            }
        }
        return count;
    }

    private static final <T> int getFreq(final Object obj, final Map<T, Integer> freqMap) {
        final Integer count = freqMap.get(obj);
        if (count != null) {
            return count.intValue();
        }
        return 0;
    }

    private CommonUtil() {
        // Should not be instantiated
    }

}
