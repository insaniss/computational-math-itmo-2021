package ru.ifmo.cmath.algebra;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegralAnswer {
    private final Double area, error;
    private final Integer parts;

    public Double area() {
        return this.area;
    }

    public Double error() {
        return this.error;
    }

    public Integer parts() {
        return this.parts;
    }
}
