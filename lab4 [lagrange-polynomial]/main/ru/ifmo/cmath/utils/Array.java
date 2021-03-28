package ru.ifmo.cmath.utils;

import java.util.Arrays;
import java.util.Collections;

public class Array {
    public static Double[] parseDoubleArray(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Array cannot be null!");
        }
        if (pattern.startsWith("[") && pattern.endsWith("]")) {
            String[] items = pattern.substring(1, pattern.length()-1).split("(,)");
            /* Return a new empty Double array */
            if (items.length == 1 && items[0].trim().isEmpty()) {
                return new Double[] { };
            }
            /* Build a new Double array length of item's length */
            Double[] array = new Double[items.length];
            /* Parse an every item to Double number */
            for (int i = 0; i < items.length; i++) {
                array[i] = Double.parseDouble(items[i].trim());
            }
            return array;
        } else {
            throw new RuntimeException("Invalid format of array!");
        }
    }

    public static Double minOf(Double[]... arrays) {
        Double minimum = Double.POSITIVE_INFINITY;
        for (Double[] array : arrays) {
            try {
                /* Calculate a min value of array */
                Double minValue = Collections.min(Arrays.asList(array));
                /* Check for MIN value */
                if (minimum > minValue) {
                    minimum = minValue;
                }
            } catch (RuntimeException ignored) { }
        }
        return minimum;
    }

    public static Double maxOf(Double[]... arrays) {
        Double maximum = Double.NEGATIVE_INFINITY;
        for (Double[] array : arrays) {
            try {
                /* Calculate a max value of array */
                Double maxValue = Collections.max(Arrays.asList(array));
                /* Check for MAX value */
                if (maximum < maxValue) {
                    maximum = maxValue;
                }
            } catch (RuntimeException ignored) { }
        }
        return maximum;
    }
}
