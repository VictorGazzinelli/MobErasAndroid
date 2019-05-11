package com.example.victor.moberas.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.victor.moberas.R;

public class NameEntry extends LinearLayout {

    View rootView;
    TextView textMessage;
    EditText entryMessage;
    TextView errorMessage;


    public NameEntry (Context context) {
        super(context);
        init(context);
    }

    public NameEntry (Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.util_name_entry, this);
        textMessage = rootView.findViewById(R.id.tv_name);
        entryMessage = rootView.findViewById(R.id.et_name);
        errorMessage = rootView.findViewById(R.id.tv_error_message_name);
    }

    public String getName()
    {
        return entryMessage.getText().toString();
    }

    public void setName(String message)
    {
        entryMessage.setText(message);
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
