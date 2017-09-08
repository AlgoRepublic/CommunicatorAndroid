package com.algorepublic.communicator.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adilnazir on 08/08/2017.
 */

public class LabourModel {

    public static LabourModel _obj = null;

    public LabourModel() {

    }

    public static LabourModel getInstance() {
        if (_obj == null) {
            _obj = new LabourModel();
        }
        return _obj;
    }

    public void setObj(LabourModel obj) {
        _obj = obj;
    }

    @SerializedName("size")
    public String size;

    @SerializedName("value")
    public ArrayList<Labour> laboursList = new ArrayList<>();


    public class Labour {

        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("surname")
        public String surname;
        @SerializedName("employeeId")
        public String employeeId;

        @SerializedName("designation")
        public String designation;

        @SerializedName("isActive")
        public boolean isActive;


    }

}