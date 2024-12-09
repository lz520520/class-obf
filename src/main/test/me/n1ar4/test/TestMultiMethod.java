package me.n1ar4.test;

public class TestMultiMethod {
    private int a(int x, int y) {
        return x + y;
    }

    private int b(int x, int y) {
        return x - y;
    }

    private int c(int x, int y) {
        return a(x, y) + b(x, y);
    }
}
