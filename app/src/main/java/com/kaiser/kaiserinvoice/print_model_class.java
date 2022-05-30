package com.kaiser.kaiserinvoice;

public class print_model_class {
    String id,name,price,total_price,total_uom,Discount,uni_id;

    public print_model_class(String id,String name, String price, String total_price, String total_uom, String discount,
                             String uni_id) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.total_price = total_price;
        this.total_uom = total_uom;
        this.Discount = discount;
        this.uni_id = uni_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUni_id() {
        return uni_id;
    }

    public void setUni_id(String uni_id) {
        this.uni_id = uni_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_uom() {
        return total_uom;
    }

    public void setTotal_uom(String total_uom) {
        this.total_uom = total_uom;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
