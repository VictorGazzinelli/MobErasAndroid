package com.example.victor.moberas.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.victor.moberas.R;

public class BirthEntry extends LinearLayout {

    View rootView;
    BirthDateSpinner birthDateSpinner;
    TextView errorMessage;


    public BirthEntry(Context context) {
        super(context);
        init(context);
    }

    public BirthEntry(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.util_birth_entry, this);
        birthDateSpinner = rootView.findViewById(R.id.bds_spinner);
        errorMessage = rootView.findViewById(R.id.tv_error_message_birth);
    }

    public String getBirthDate(){
        return  birthDateSpinner.getBirthDate();
    }

    public void showError()
    {
        errorMessage.setVisibility(VISIBLE);
    }

    public void hideError()
    {
        errorMessage.setVisibility(GONE);
    }

}
