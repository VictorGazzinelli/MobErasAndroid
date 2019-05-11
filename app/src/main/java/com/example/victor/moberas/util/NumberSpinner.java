package com.example.victor.moberas.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.victor.moberas.R;

public class NumberSpinner extends LinearLayout {

    View rootView;
    EditText numberView;
    ImageView numberInc,numberDec;
    String numberFormat;
    int min,max;

    public static final int HOUR = 1;
    public static final int MINUTE = 2;
    public static final int SECOND = 3;
    public static final int DAY = 4;
    public static final int MONTH = 5;
    public static final int YEAR = 6;

    public NumberSpinner(Context context) {
        super(context);
        init(context);
    }

    public NumberSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.util_number_spinner, this);
        numberView = rootView.findViewById(R.id.et_num);
        numberInc = rootView.findViewById(R.id.iv_num_inc);
        numberDec = rootView.findViewById(R.id.iv_num_dec);
        numberInc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! (getNumber() >= max) ){
                    setNumber(getNumber() + 1);
                }
                else {
                    setNumber(min);
                }
            }
        });
        numberDec.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! (getNumber() <= min) ){
                    setNumber(getNumber() - 1);
                }
                else {
                    setNumber(max);
                }
            }
        });
        numberView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String content = numberView.getText().toString();
                    if( content.length() == 0){
                        setNumber(min);
                    }
                    else {
                        int value = getNumber();
                        if( value > max){
                            setNumber(max);
                        }
                        if( value < min){
                            setNumber(min);
                        }
                    }
                }
            }
        });
    }

    public void setMin(int min){
        this.min = min;
    }

    public void setMax(int max){
        this.max = max;
    }

    public void setNumberFormat(int places) {
        this.numberFormat = "%0" + places + "d";
    }

    public void setBoundaries(int type){
        switch (type){
            case HOUR:{
                setMin(0);
                setMax(23);
                setNumberFormat(2);
                break;
            }
            case MINUTE:{
                setMin(0);
                setMax(59);
                setNumberFormat(2);
                break;
            }
            case SECOND:{
                setMin(0);
                setMax(59);
                setNumberFormat(2);
                break;
            }
            case DAY:{
                setMin(1);
                setMax(31);
                setNumberFormat(2);
                break;
            }
            case MONTH:{
                setMin(1);
                setMax(12);
                setNumberFormat(2);
                break;
            }
            case YEAR:{
                setMin(1920);
                setMax(2019);
                setNumberFormat(4);
                break;
            }
        }
        this.setNumber(min);
    }

    public void setNumber(int number) {
        if( this.numberFormat == null){
            numberView.setText("" + number);
        }
        else {
            numberView.setText(String.format(this.numberFormat,number));
        }
    }

    public int getNumber (){
        return Integer.parseInt(numberView.getText().toString());
    }

    public String getNumberString (){
        return numberView.getText().toString();
    }
}
