package com.example.victor.moberas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.victor.moberas.R;
import com.example.victor.moberas.model.TimePick;
import com.example.victor.moberas.util.NumberSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.victor.moberas.model.Constants.RESULT_CODE_CANCEL;
import static com.example.victor.moberas.model.Constants.RESULT_CODE_OK;

public class TimePickerActivity extends AppCompatActivity {

    TextView description;
    Button confirm;
    NumberSpinner daySpinner,monthSpinner,hourSpinner,minuteSpinner;
    int day,month,hour,minute;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Keep Screen On
        identifyViews();
        registerListeners();
        setInitialValues();
    }

    private void setInitialValues() {
        String desc = getIntent().getStringExtra("description");
        description.setText(desc);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        daySpinner.setBoundaries(NumberSpinner.DAY);
        monthSpinner.setBoundaries(NumberSpinner.MONTH);
        hourSpinner.setBoundaries(NumberSpinner.HOUR);
        minuteSpinner.setBoundaries(NumberSpinner.MINUTE);
        daySpinner.setNumber(calendar.get(Calendar.DAY_OF_MONTH));
        monthSpinner.setNumber(calendar.get(Calendar.MONTH) + 1);
        hourSpinner.setNumber(calendar.get(Calendar.HOUR_OF_DAY));
        minuteSpinner.setNumber(calendar.get(Calendar.MINUTE));
    }

    private void identifyViews() {
        description = findViewById(R.id.tv_description);
        daySpinner = findViewById(R.id.ns_day);
        monthSpinner = findViewById(R.id.ns_month);
        hourSpinner = findViewById(R.id.ns_hour);
        minuteSpinner = findViewById(R.id.ns_minute);
        confirm = findViewById(R.id.bt_confirm);
    }

    private void registerListeners() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = daySpinner.getNumber();
                month = monthSpinner.getNumber();
                hour = hourSpinner.getNumber();
                minute = minuteSpinner.getNumber();
                time = String.format("%02d",day) + "/";
                time += String.format("%02d",month) + " - ";
                time += String.format("%02d",hour) + ":";
                time += String.format("%02d",minute);
                TimePick timePick = new TimePick();
                timePick.time = time;
                timePick.timestamp = getCurrentTime();
                Intent intent = new Intent();
                intent.putExtra("timePick",timePick);
                setResult(RESULT_CODE_OK, intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CODE_CANCEL, intent);
    }

    public String getDescription(){
        return description.getText().toString();
    }

    public String getCurrentTime(){
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        return dt.format(new Date());
    }
}
