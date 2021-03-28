package ru.ifmo.cmath.utils;

public class Array {
    public static Double[] parseDoubleArray(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Array cannot be empty!");
        }
        if (pattern.startsWith("[") && pattern.endsWith("]")) {
            String[] items = pattern.substring(1, pattern.length()-1).split("(,)");
            /* Return a new empty Double array */
            if (items.length == 1 && items[0].trim().isEmpty()) {
                return new Double[] {};
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
}
