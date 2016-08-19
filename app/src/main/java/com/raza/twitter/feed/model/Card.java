package com.raza.twitter.feed.model;

import com.google.gson.annotations.SerializedName;
/**
 * Created by jaffarraza on 19/08/16.
 */
public class Card {

    @SerializedName("binding_values")
    public final BindingValues bindingValues;

    @SerializedName("name")
    public final String name;

    public Card(BindingValues bindingValues, String name) {
        this.bindingValues = bindingValues;
        this.name = name;
    }
}
