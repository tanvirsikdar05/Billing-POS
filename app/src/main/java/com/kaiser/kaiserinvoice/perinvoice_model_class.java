package com.kaiser.kaiserinvoice;

public class perinvoice_model_class {
    String id,date,total;

    public perinvoice_model_class(String id, String date, String total) {
        this.id = id;
        this.date = date;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
