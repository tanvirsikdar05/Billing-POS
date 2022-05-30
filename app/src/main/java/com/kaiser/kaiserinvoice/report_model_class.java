package com.kaiser.kaiserinvoice;

public class report_model_class {
    String date,name,total_uom,p_price,discount,total_tk,id;

    public report_model_class(String date, String name, String total_uom, String p_price, String discount, String total_tk, String id) {
        this.date = date;
        this.name = name;
        this.total_uom = total_uom;
        this.p_price = p_price;
        this.discount = discount;
        this.total_tk = total_tk;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal_uom() {
        return total_uom;
    }

    public void setTotal_uom(String total_uom) {
        this.total_uom = total_uom;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal_tk() {
        return total_tk;
    }

    public void setTotal_tk(String total_tk) {
        this.total_tk = total_tk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
