package com.kaiser.kaiserinvoice;

public class product_model_class {
    String id;
    String db_id;
    String name;
    String categroy;
    String uom;
    String price,stock;

    public product_model_class(String id, String db_id,String name, String categroy, String uom, String price) {
        this.id = id;
        this.name = name;
        this.categroy = categroy;
        this.uom = uom;
        this.price = price;
        this.db_id=db_id;


    }



    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategroy() {
        return categroy;
    }

    public void setCategroy(String categroy) {
        this.categroy = categroy;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
