package com.example.victor.moberas.activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import androidx.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.victor.moberas.R;
import com.example.victor.moberas.model.Constants;
import com.example.victor.moberas.model.UserData;
import com.example.victor.moberas.util.InfoButton;

import static com.example.victor.moberas.model.Constants.REQUEST_CODE_TIME_PICK;
import static com.example.victor.moberas.model.Constants.RESULT_CODE_CANCEL;
import static com.example.victor.moberas.model.Constants.RESULT_CODE_OK;

public class MedicalActivitiesActivity extends AppCompatActivity {

    UserData userData;
    InfoButton diet, catheter, diuresis, flatus, stool, drip;
    int activityCalledCode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_activities);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Keep Screen On
        userData = (UserData) getIntent().getSerializableExtra("userData");
        identifyViews();
        registerListeners();
        setMessages();
        setVisibility();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_TIME_PICK && resultCode == RESULT_CODE_OK){
            Intent result = data;
            result.putExtra("activityCalledCode", activityCalledCode);
            setResult(RESULT_CODE_OK, result);
            finish();
        }
    }

    private void setVisibility() {
        diet.setVisibility(userData.firstOralDietIntakeTime == null);
        catheter.setVisibility(userData.bladderCatheterRemovalTime == null);
        diuresis.setVisibility(userData.firstDiuresisTime == null);
        flatus.setVisibility(userData.firstFlatusTime == null);
        stool.setVisibility(userData.firstStoolTime == null);
        drip.setVisibility(userData.dripRemovalTime == null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CODE_CANCEL, intent);
    }

    private void identifyViews() {
        diet = findViewById(R.id.ib_activities_diet);
        catheter = findViewById(R.id.ib_activities_catheter);
        diuresis = findViewById(R.id.ib_activities_diuresis);
        flatus = findViewById(R.id.ib_activities_flatus);
        stool = findViewById(R.id.ib_activities_stool);
        drip = findViewById(R.id.ib_activities_drip);
    }

    private void registerListeners() {
        diet.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCalledCode = Constants.ORAL_DIET_INTAKE;
                Intent intent = new Intent(MedicalActivitiesActivity.this,TimePickerActivity.class);
                intent.putExtra("description",diet.getMessage());
                startActivityForResult(intent,REQUEST_CODE_TIME_PICK);
            }
        });
        catheter.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCalledCode = Constants.BLADDER_CATHETER_REMOVAL;
                Intent intent = new Intent(MedicalActivitiesActivity.this,TimePickerActivity.class);
                intent.putExtra("description",catheter.getMessage());
                startActivityForResult(intent,REQUEST_CODE_TIME_PICK);
            }
        });
        diuresis.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCalledCode = Constants.DIURESIS;
                Intent intent = new Intent(MedicalActivitiesActivity.this,TimePickerActivity.class);
                intent.putExtra("description",diuresis.getMessage());
                startActivityForResult(intent,REQUEST_CODE_TIME_PICK);
            }
        });
        flatus.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCalledCode = Constants.FLATUS;
                Intent intent = new Intent(MedicalActivitiesActivity.this,TimePickerActivity.class);
                intent.putExtra("description",flatus.getMessage());
                startActivityForResult(intent,REQUEST_CODE_TIME_PICK);
            }
        });
        stool.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCalledCode = Constants.STOOL;
                Intent intent = new Intent(MedicalActivitiesActivity.this,TimePickerActivity.class);
                intent.putExtra("description",stool.getMessage());
                startActivityForResult(intent,REQUEST_CODE_TIME_PICK);
            }
        });
        drip.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCalledCode = Constants.DRIP;
                Intent intent = new Intent(MedicalActivitiesActivity.this,TimePickerActivity.class);
                intent.putExtra("description",stool.getMessage());
                startActivityForResult(intent,REQUEST_CODE_TIME_PICK);
            }
        });
    }

    private void setMessages(){
        diet.setMessage("Primeira Ingestão Dieta Oral");
        catheter.setMessage("Retirada da Sonda");
        diuresis.setMessage("Primeira Diurese Espontânea");
        flatus.setMessage("Primeira Eliminação de Flatos");
        stool.setMessage("Primeira Eliminação de Fezes");
        drip.setMessage("Retirada do Soro");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
