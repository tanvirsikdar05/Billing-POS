package com.kaiser.kaiserinvoice;


public class meserment {

    public String second_mm(String firstmm){
        String con_lowe=firstmm.toLowerCase();
        if (con_lowe.equals("kg")){
            return "gm";
        }else if (con_lowe.equals("ft")){
            return "in";
        }else if (con_lowe.equals("pcs") || con_lowe.equals("pc")){
            return "0";
        }else if (con_lowe.equals("lt")){
            return "ml";
        }else {
            return "0";
        }
    }

    public int second_mm_increase(String firstmm){
        String con_lowe=firstmm.toLowerCase();
        if(con_lowe.equals("kg")){
            return 100;
        }else if (con_lowe.equals("ft")) return 1;
        else if (con_lowe.equals("lt")) return 100;
        else if (con_lowe.equals("pcs") || con_lowe.equals("pc")) return 0;
        else return 0;
    }
}
