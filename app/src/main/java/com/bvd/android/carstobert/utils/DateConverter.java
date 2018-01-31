package com.bvd.android.carstobert.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by bara on 1/31/2018.
 */

public class DateConverter {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
