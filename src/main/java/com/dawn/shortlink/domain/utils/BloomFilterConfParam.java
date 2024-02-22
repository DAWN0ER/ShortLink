package com.dawn.shortlink.domain.utils;

public class BloomFilterConfParam {
    private String name;
    private int expectedInsertions;
    private double fpp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpectedInsertions() {
        return expectedInsertions;
    }

    public void setExpectedInsertions(int expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
    }

    public double getFpp() {
        return fpp;
    }

    public void setFpp(double fpp) {
        this.fpp = fpp;
    }
}