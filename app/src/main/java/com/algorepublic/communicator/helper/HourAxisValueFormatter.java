package com.algorepublic.communicator.helper;

import com.algorepublic.communicator.model.ActSchedule2Model;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Created by adilnazir on 23/08/2017.
 */

public class HourAxisValueFormatter implements IAxisValueFormatter {

    private long referenceTimestamp; // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;

    public HourAxisValueFormatter(long referenceTimestamp) {
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        this.mDate = new Date();
    }


    public int getDecimalDigits() {
        return 0;
    }

    private String getHour(long timestamp) {
        try {
            mDate.setTime(timestamp * 1000L);
            return mDataFormat.format(mDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        long convertedTimestamp = (long) value;


        // Retrieve original timestamp
        long originalTimestamp = convertedTimestamp - referenceTimestamp;

        // Convert timestamp to hour:minute
        return getHour(originalTimestamp);
    }
}
