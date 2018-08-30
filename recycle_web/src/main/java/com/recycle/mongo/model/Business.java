package com.recycle.mongo.model;

import java.io.Serializable;

public class Business implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _id;
    //维度
    private double lat;
    //经度
    private double lng;
    //商家编号
    private int openid;

    //默认构造函数
    public Business() {
        super();
    }

    //set和get
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getOpenid() {
        return openid;
    }

    public void setOpenid(int openid) {
        this.openid = openid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
