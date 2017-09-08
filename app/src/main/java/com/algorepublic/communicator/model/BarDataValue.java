package com.algorepublic.communicator.model;

/**
 * Created by adilnazir on 23/08/2017.
 */

public class BarDataValue {

    public String id;
    public long startTime;
    public String description;
    public long endTime;
    public boolean isFirstChild;



    public BarDataValue(String id, String des , long sTime, long eTime, boolean ischild) {
        this.id = id;
        this.description = des;
        this.startTime = sTime;
        this.endTime = eTime;
        this.isFirstChild = ischild;
    }

}
