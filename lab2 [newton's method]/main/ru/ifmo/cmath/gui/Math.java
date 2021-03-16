package ru.ifmo.cmath.gui;

import ru.ifmo.cmath.algebra.Function;

public class Math {
    public static final Function[][] EQUATIONS = new Function[][] {
            {
                new Function("sin(x)^2-x^2+1"), new Function("(sin(x)^2+1)^0.5"), new Function("x") // 1
            },
            {
                new Function("x^2-e^x-3x+2"), new Function("log(x^2-3x+2)"), new Function("x") // 2
            },
            {
                new Function("xe^{x^2}-sin(x)^2+3cos(x)+5"), new Function("(sin(x)^2-3cos(x)-5)/e^{x^2}"), new Function("x") // 3
            },
            {
                new Function("x^3-17"), new Function("17^(1/3)"), new Function("x") // 4
            }
    };

    public static final String[][] GRAPHS = new String[][] {
            {
                new String("y=sin(x)^2-x^2+1"), new String("y=(sin(x)^2+1)^0.5"), new String("y=x")
            },
            {
                new String("y=x^2-e^x-3x+2"), new String("y=log(x^2-3x+2)"), new String("y=x")
            },
            {
                new String("y=xe^{x^2}-sin(x)^2+3cos(x)+5"), new String("y=(sin(x)^2-3cos(x)-5)/e^{x^2}"), new String("y=x")
            },
            {
                new String("y=x^3-17"), new String("y=17^{1/3}"), new String("y=x")
            }
    };

    public static final String[] EQUATION = new String[] {
            new String("sin(x)^2 - x^2 + 1 = 0")
            ,
            new String("x^2 - e^x - 3x + 2 = 0")
            ,
            new String("xe^{x^2} - sin(x)^2 + 3cos(x) + 5 = 0")
            ,
            new String("x^3 - 17 = 0")
    };

    public static final Function[][] SYSTEMS = new Function[][] {
            {
                new Function("0.1x^2+x+0.2y^2-0.3"), new Function("0.2x^2+y-0.1xy-0.7"), new Function("((0.3-x-0.1x^2)/0.2)^0.5"), new Function("(0.2x^2-0.7)/(0.1x-1)") // 1
            },
            {
                new Function("sin(2x-y)-1.2x-0.4"), new Function("0.8x^2+1.5y^2-1"), new Function("2x-asin(1.2x+0.4)"), new Function("((1-0.8x^2)/1.5)^0.5") // 2
            }
    };

    public static final String[][] GRAPH = new String[][] {
            {
                new String("y=((0.3-x-0.1x^2)/0.2)^0.5"), new String("y=(0.2x^2-0.7)/(0.1x-1)")
            },
            {
                new String("y=2x-asin(1.2x+0.4)"), new String("y=((1-0.8x^2)/1.5)^0.5")
            }
    };

    public static final String[][] SYSTEM = new String[][] {
            {
                new String("0.1x^2 + x + 0.2y^2 - 0.3 = 0"), new String("0.2x^2 + y - 0.1xy - 0.7 = 0")
            },
            {
                new String("sin(2x-y) - 1.2x - 0.4 = 0"), new String("0.8x^2 + 1.5y^2 - 1 = 0")
            }
    };
}
