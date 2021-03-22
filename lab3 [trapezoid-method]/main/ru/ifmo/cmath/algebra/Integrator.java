package ru.ifmo.cmath.algebra;

public class Integrator {
    public IntegralAnswer integrate(Integral integral, Double accuracy) throws ConvergeException {

        return approximate();
    }

    public IntegralAnswer approximate() throws ConvergeException {

        return new IntegralAnswer(0.0, 0.0, 0);
    }
}
