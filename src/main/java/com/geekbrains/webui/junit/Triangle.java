package com.geekbrains.webui.junit;

public class Triangle {
    // треугольники могут быть и некорректными (с отрицательными сторонами, например)
    // проверкой корректности треугольника занимается getSquare (чтоб интереснее было тестировать)

    private double a, b, c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getSquare() throws NotTriangleException {
        if(a <= 0 || b <= 0 || c <= 0) throw new NotTriangleException("Стороны треугольника должны быть положительными");
        else if (a + b <= c || a + c <= b || b + c <= a) throw new NotTriangleException("Треугольника с такими сторонами не может существовать");
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public String toString() {
        return "a = " + a +
                ", b = " + b +
                ", c = " + c +
                '}';
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }
}
