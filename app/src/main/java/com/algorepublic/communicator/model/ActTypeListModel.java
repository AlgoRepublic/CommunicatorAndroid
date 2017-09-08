package com.algorepublic.communicator.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adilnazir on 17/08/2017.
 */

public class ActTypeListModel {
    public static ActTypeListModel _obj = null;

    public ActTypeListModel() {

    }

    public static ActTypeListModel getInstance() {
        if (_obj == null) {
            _obj = new ActTypeListModel();
        }
        return _obj;
    }

    public void setObj(ActTypeListModel obj) {
        _obj = obj;
    }

    @SerializedName("size")
    public String size;

    @SerializedName("value")
    public ArrayList<Value> valueList = new ArrayList<>();


    public class Value {

        @SerializedName("id")
        public int id;

        @SerializedName("description")
        public String description;

    }


}
