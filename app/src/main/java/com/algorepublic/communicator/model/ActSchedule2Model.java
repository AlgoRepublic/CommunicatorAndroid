package com.algorepublic.communicator.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adilnazir on 17/08/2017.
 */

public class ActSchedule2Model {

    public static ActSchedule2Model _obj = null;

    public ActSchedule2Model() {

    }

    public static ActSchedule2Model getInstance() {
        if (_obj == null) {
            _obj = new ActSchedule2Model();
        }
        return _obj;
    }

    public void setObj(ActSchedule2Model obj) {
        _obj = obj;
    }

    @SerializedName("size")
    public String size;

    @SerializedName("value")
    public ArrayList<Value> valueList = new ArrayList<>();


    public class Value {

        @SerializedName("id")
        public String id;

        @SerializedName("scheduleDate")
        public String scheduleDate;

        @SerializedName("equipmentId")
        public String equipmentId;


        @SerializedName("activityId")
        public int activityId;

        @SerializedName("startDate")
        public String startDate;

        @SerializedName("endDate")
        public String endDate;

        @SerializedName("taskId")
        public String taskId;

        @SerializedName("labourId")
        public String labourId;

        @SerializedName("status")
        public String status;

        @SerializedName("factor")
        public String factor;


    }

}