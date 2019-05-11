package com.example.victor.moberas.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.victor.moberas.R;

public class ButtonEntry extends LinearLayout {

    View rootView;
    Button login, register;
    TextView errorMessage;


    public ButtonEntry(Context context) {
        super(context);
        init(context);
    }

    public ButtonEntry(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.util_button_entry, this);
        login = rootView.findViewById(R.id.bt_login);
        register = rootView.findViewById(R.id.bt_register);
        errorMessage = rootView.findViewById(R.id.tv_error_message_button);
    }

    public void setLoginOnClickListener(OnClickListener onClickListener){
        login.setOnClickListener(onClickListener);
    }

    public void setRegisterOnClickListener(OnClickListener onClickListener){
        register.setOnClickListener(onClickListener);
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
