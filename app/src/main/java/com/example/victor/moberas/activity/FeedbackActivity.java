package com.example.victor.moberas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victor.moberas.R;
import com.example.victor.moberas.model.Feedback;
import com.example.victor.moberas.model.UserData;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.victor.moberas.model.Constants.RESULT_CODE_CANCEL;
import static com.example.victor.moberas.model.Constants.RESULT_CODE_OK;

public class FeedbackActivity extends AppCompatActivity {

    Button ok;
    TextView painQuestion, painProgress, nauseaQuestion, nauseaProgress;
    TextView diuresisQuestion, diuresisProgress, dietQuestion, dietProgress;
    TextView generalHealthQuestion, generalHealthProgress;
    ImageView painImage;
    SeekBar painSeekBar;

    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Keep Screen On
        userData = (UserData) getIntent().getSerializableExtra("userData");
        identifyViews();
        registerListeners();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CODE_CANCEL, intent);
    }

    private void identifyViews() {
        ok = findViewById(R.id.bt_ok_feedback);
        painQuestion = findViewById(R.id.tv_pain_question);
        painProgress = findViewById(R.id.tv_pain_progress);
        painSeekBar = findViewById(R.id.sb_pain);
        painImage = findViewById(R.id.iv_pain_image);
        nauseaQuestion = findViewById(R.id.tv_nausea_question);
        diuresisQuestion = findViewById(R.id.tv_diuresis_question);
        dietQuestion = findViewById(R.id.tv_diet_question);
        generalHealthQuestion = findViewById(R.id.tv_general_health_question);
        nauseaProgress = findViewById(R.id.tv_nausea_progress);
        diuresisProgress = findViewById(R.id.tv_diuresis_progress);
        dietProgress = findViewById(R.id.tv_diet_progress);
        generalHealthProgress = findViewById(R.id.tv_general_health_progress);
    }

    private void registerListeners() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback feedback = new Feedback();
                feedback.ratingPain = getPainProgress();
                feedback.ratingVomitingAndNausea = getVomitingAndNauseaProgress();
                feedback.ratingDietTolerance = getDietProgress();
                feedback.ratingVolumeDiuresis = getDiuresisProgress();
                feedback.ratingGeneralHealth = getGeneralHealthProgress();
                feedback.flatusElimination = getFlatusElimination();
                feedback.stoolElimination = getStoolElimination();
                feedback.time = getFeedbackTime();
                Intent intent = new Intent();
                intent.putExtra("feedback",feedback);
                setResult(RESULT_CODE_OK, intent);
                finish();
            }
        });
        painSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String newValue = "" + progress;
                painProgress.setText(newValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do Nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do Nothing
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private String getFeedbackTime() {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dt.format(new Date());
    }

    private int  getPainProgress(){
        return Integer.parseInt(painProgress.getText().toString());
    }

    private int  getVomitingAndNauseaProgress(){
        return Integer.parseInt(nauseaProgress.getText().toString());
    }

    private int  getDiuresisProgress(){
        return Integer.parseInt(diuresisProgress.getText().toString());
    }

    private int  getDietProgress(){
        return Integer.parseInt(dietProgress.getText().toString());
    }

    private int  getGeneralHealthProgress(){
        return Integer.parseInt(generalHealthProgress.getText().toString());
    }

    private String getFlatusElimination()
    {
        return "NO";
    }

    private String getStoolElimination()
    {
        return "NO";
    }

}
