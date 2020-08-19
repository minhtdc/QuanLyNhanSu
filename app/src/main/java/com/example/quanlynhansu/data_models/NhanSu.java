package com.example.quanlynhansu.data_models;

import androidx.annotation.NonNull;

public class NhanSu {
    private String name, degree, hoppies;
    private boolean check = false;

    public NhanSu() { }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public NhanSu(String name, String degree, String hoppies) {
        this.name = name;
        this.degree = degree;
        this.hoppies = hoppies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getHoppies() {
        return hoppies;
    }

    public void setHoppies(String hoppies) {
        this.hoppies = hoppies;
    }

    @Override
    public String toString() {
        return name + " " + degree + " " + hoppies;
    }
}
