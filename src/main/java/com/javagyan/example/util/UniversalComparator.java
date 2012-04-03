package com.javagyan.example.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * A simple universal comparator for comparing any kind of object on any of its fields(Comparable fields).
 * <p>
 * Description: You can use this class to sort an array/collection of Objects without creating custom Comparators for
 * each field. For example, if you have a class Employee, with methods getName() and getSalary(), you can simply do
 * Arrays.sort(myEmployeeArray, new UniversalComparator("getName", 1)) or Collections.sort(myEmployeeList, new
 * UniversalComparator("getSalary", -1)). The first statement would sort your array in ascending order by the value of
 * getName() method, the second would sort a list in descending order by the value of getSalary() method. The class uses
 * the reflection API to accomplish this task.
 * </p>
 * @author Sanjeev Kumar
 */
public class UniversalComparator implements Comparator<Object> {
    public static enum SortOrder {
        ASC, DESC;
        public static int intValue(final SortOrder order) {
            return SortOrder.DESC.equals(order) ? -1 : 1;
        }
    }

    private String methodName = "toString";

    private SortOrder sortOrder = SortOrder.ASC; // default is ASCENDING

    public UniversalComparator(final SortOrder descAscIndicator) {
        this.sortOrder = descAscIndicator;
    }

    public UniversalComparator(final String methodName, final SortOrder descAscIndicator) {
        this(descAscIndicator);
        this.methodName = methodName;
    }

    public int compare(final Object object1, final Object object2) {
        Object comp1 = null;
        Object comp2 = null;
        try {
            final Method object1_Method = object1.getClass().getMethod(this.methodName, null);
            final Method object2_Method = object2.getClass().getMethod(this.methodName, null);
            comp1 = object1_Method.invoke(object1, null);
            comp2 = object2_Method.invoke(object2, null);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException("NoSuchMethodException", e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException", e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException("InvocationTargetException", e);
        }
        return ((Comparable) comp1).compareTo(comp2) * SortOrder.intValue(this.sortOrder);
    }
}
