package com.dtt.thanhthuan.bottomnavigation.model;

import java.io.Serializable;

public class SANPHAM implements Serializable {
    public String masanpham, machude, tensanpham, hinhsanpham, mota;
    public int giasanpham, soluong;

    public SANPHAM(){}
    public SANPHAM(String masanpham, String tensanpham, String hinhsanpham, int giasanpham) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.hinhsanpham = hinhsanpham;
        this.giasanpham = giasanpham;
    }

    public SANPHAM(String tensanpham, String hinhsanpham, int giasanpham){
        this.tensanpham = tensanpham;
        this.hinhsanpham = hinhsanpham;
        this.giasanpham = giasanpham;
    }
    public SANPHAM(String masanpham, String machude, String tensanpham, String hinhsanpham, int giasanpham, String mota) {
        this.masanpham = masanpham;
        this.machude = machude;
        this.tensanpham = tensanpham;
        this.hinhsanpham = hinhsanpham;
        this.giasanpham = giasanpham;
        this.mota = mota;
    }



    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public String getMachude() {
        return machude;
    }

    public void setMachude(String machude) {
        this.machude = machude;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhsanpham() {
        return hinhsanpham;
    }

    public void setHinhsanpham(String hinhsanpham) {
        this.hinhsanpham = hinhsanpham;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
