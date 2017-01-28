package com.example.annedwyer.databaseproducts;

/**
 * Created by annedwyer on 12/24/16.
 */
public class Products {
    private int _id;
    private String _productName;

    public Products(){

    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_productName(String _productname) {
        this._productName = _productName;
    }

    public int get_id() {
        return _id;
    }

    public String get_productName() {
        return _productName;
    }

    public Products(String productName) {
        this._productName = productName;
    }

}
