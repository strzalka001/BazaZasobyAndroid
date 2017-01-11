package com.example.milek.android3;

/**
 * Created by Milek on 2016-11-04.
 */

public class Product{

    public int id;
    public int title;
    public int img_url;
    public int desc;

    public Product(int p_title, int p_img_url, int p_desc) {
        title = p_title;
        img_url = p_img_url;
        desc=p_desc;
    }
    public Product(int p_id, int  p_title, int p_img_url, int p_desc) {
        id = p_id;
        title = p_title;
        img_url = p_img_url;
        desc=p_desc;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getImg_url() {
        return img_url;
    }

    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }




}