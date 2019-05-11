package com.example.victor.moberas.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.victor.moberas.R;

public class InfoButton extends LinearLayout {

    View rootView;
    Button action;

    public InfoButton(Context context) {
        super(context);
        init(context);
    }

    public InfoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.util_info_button, this);
        action = rootView.findViewById(R.id.bt_action);

    }

    public void setActionListener(OnClickListener onClickListener){
        action.setOnClickListener(onClickListener);
    }

    public void setMessage(String message){
        action.setText(message);
    }

    public String getMessage(){
        return action.getText().toString();
    }


    public void setActionClickable(boolean clickable)
    {
        action.setClickable(clickable);
    }

    public void setVisibility(boolean visibility){
        if(visibility){
            this.setVisibility(VISIBLE);
        }
        else {
            this.setVisibility(GONE);
        }
    }

}
