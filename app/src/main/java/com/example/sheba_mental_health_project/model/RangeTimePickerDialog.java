package com.example.sheba_mental_health_project.model;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;

public class RangeTimePickerDialog extends TimePickerDialog {
    private int minHour = -1;
    private int minMinute = -1;

    private int maxHour = 25;
    private int maxMinute = 25;

    private int currentHour = 0;
    private int currentMinute = 0;

    private Calendar calendar = Calendar.getInstance();
    private DateFormat dateFormat;


    public RangeTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute, is24HourView);
        currentHour = hourOfDay;
        currentMinute = minute;
        dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

        try {
            Class<?> superclass = getClass().getSuperclass();
            Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
            mTimePickerField.setAccessible(true);
            TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
            mTimePicker.setOnTimeChangedListener(this);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    public void setMin(int hour, int minute) {
        minHour = hour;
        minMinute = minute;
    }

    public void setMax(int hour, int minute) {
        maxHour = hour;
        maxMinute = minute;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        Log.d("RangeTimePickerDialog", "onTimeChanged");

        boolean validTime = true;
        if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)) {
            validTime = false;
        }

        if (hourOfDay > maxHour || (hourOfDay == maxHour && minute > maxMinute)) {
            validTime = false;
        }

        if (validTime) {
            currentHour = hourOfDay;
            currentMinute = minute;
        }

        updateTime(currentHour, currentMinute);
        updateDialogTitle(view, currentHour, currentMinute);
    }

    private void updateDialogTitle(TimePicker timePicker, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String title = dateFormat.format(calendar.getTime());
        setTitle(title);
    }
}
