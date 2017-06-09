package com.example.madhumita.navi;

/**
 * Created by madhumita on 7/18/2016.
 * Provide data
 */
public class Dataprovider  {
    private String rating;

    private String name;


    public Dataprovider(String rating,String name)
    {
        this.name=name;
        this.rating=rating;

    }
    public String getcust() {
        return rating;
    }

    public void setCust(String cust) {
        this.rating = rating;
    }
    public String getname1() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }
    //

}
