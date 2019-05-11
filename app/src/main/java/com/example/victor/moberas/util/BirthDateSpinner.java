package com.example.victor.moberas.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.victor.moberas.R;


public class BirthDateSpinner extends LinearLayout {

    View rootView;
    NumberSpinner day, month, year;

    public BirthDateSpinner(Context context) {
        super(context);
        init(context);
    }

    public BirthDateSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.util_birth_date_spinner, this);
        day = findViewById(R.id.ns_day);
        month = findViewById(R.id.ns_month);
        year = findViewById(R.id.ns_year);
        day.setBoundaries(NumberSpinner.DAY);
        month.setBoundaries(NumberSpinner.MONTH);
        year.setBoundaries(NumberSpinner.YEAR);
    }

    public String getBirthDate(){
        return  day.getNumberString() + "/" + month.getNumberString() + "/" + year.getNumberString();
    }
}
