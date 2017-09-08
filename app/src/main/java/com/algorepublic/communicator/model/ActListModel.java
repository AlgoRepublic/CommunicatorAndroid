package com.algorepublic.communicator.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adilnazir on 17/08/2017.
 */

public class ActListModel {

    public static ActListModel _obj = null;

    public ActListModel() {

    }

    public static ActListModel getInstance() {
        if (_obj == null) {
            _obj = new ActListModel();
        }
        return _obj;
    }

    public void setObj(ActListModel obj) {
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

        @SerializedName("activityTypeId")
        public int activityTypeId;

    }


}
