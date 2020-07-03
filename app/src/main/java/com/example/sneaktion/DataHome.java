package com.example.sneaktion;

import java.math.BigDecimal;

public class DataHome {

    private String title, deskripsi, image_url;


    public String getImage_url(){
        return image_url;
    }
    public void setImage_url(String image_url){
        this.image_url = "http://192.168.1.92/kelompok-2-TIF-Inter/sneaktion/assets/image/sepatu/"+image_url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDeskripsi() {
        return deskripsi;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

}