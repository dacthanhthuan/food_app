package com.dtt.thanhthuan.bottomnavigation.model;

public class CHUDE {
    public String maChude, tenChude;
    public String hinhChude;

    public CHUDE(){}

    public CHUDE(String maChude, String tenChude, String hinhChude) {
        this.maChude = maChude;
        this.tenChude = tenChude;
        this.hinhChude = hinhChude;
    }

    public String getMaChude() {
        return maChude;
    }

    public void setMaChude(String maChude) {
        this.maChude = maChude;
    }

    public String getTenChude() {
        return tenChude;
    }

    public void setTenChude(String tenChude) {
        this.tenChude = tenChude;
    }

    public String getHinhChude() {
        return hinhChude;
    }

    public void setHinhChude(String hinhChude) {
        this.hinhChude = hinhChude;
    }
}
