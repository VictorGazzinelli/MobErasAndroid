package com.example.victor.moberas.activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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
    TextView nauseaPresenceQuestion, nauseaPresenceProgress;
    TextView diuresisQuestion, diuresisProgress, dietQuestion, dietProgress;
    TextView generalHealthQuestion, generalHealthProgress, flatusPresenceProgress, stoolPresenceProgress ;
    ImageView painImage;
    ImageButton nauseaImage1, nauseaImage2, nauseaImage3, nauseaImage4, nauseaImage5;
    ImageButton nauseaPresenceNo, nauseaPresenceYes ;
    ImageButton diuresisImage1, diuresisImage2, diuresisImage3, diuresisImage4, diuresisImage5;
    ImageButton dietImage1, dietImage2, dietImage3, dietImage4, dietImage5;
    ImageButton generalHealthImage1, generalHealthImage2, generalHealthImage3, generalHealthImage4, generalHealthImage5;
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
        nauseaPresenceQuestion = findViewById(R.id.tv_nausea_presence_question);
        diuresisQuestion = findViewById(R.id.tv_diuresis_question);
        dietQuestion = findViewById(R.id.tv_diet_question);
        generalHealthQuestion = findViewById(R.id.tv_general_health_question);
        nauseaProgress = findViewById(R.id.tv_nausea_progress);
        nauseaPresenceProgress = findViewById(R.id.tv_nausea_presence_progress);
        diuresisProgress = findViewById(R.id.tv_diuresis_progress);
        dietProgress = findViewById(R.id.tv_diet_progress);
        generalHealthProgress = findViewById(R.id.tv_general_health_progress);
        nauseaImage1 = findViewById(R.id.ib_nausea_image_1);
        nauseaImage2 = findViewById(R.id.ib_nausea_image_2);
        nauseaImage3 = findViewById(R.id.ib_nausea_image_3);
        nauseaImage4 = findViewById(R.id.ib_nausea_image_4);
        nauseaImage5 = findViewById(R.id.ib_nausea_image_5);
        nauseaPresenceNo = findViewById(R.id.ib_nausea_image_presence_no);
        nauseaPresenceYes = findViewById(R.id.ib_nausea_image_presence_yes);
        diuresisImage1 = findViewById(R.id.ib_diuresis_image_1);
        diuresisImage2 = findViewById(R.id.ib_diuresis_image_2);
        diuresisImage3 = findViewById(R.id.ib_diuresis_image_3);
        diuresisImage4 = findViewById(R.id.ib_diuresis_image_4);
        diuresisImage5 = findViewById(R.id.ib_diuresis_image_5);
        dietImage1 = findViewById(R.id.ib_diet_image_1);
        dietImage2 = findViewById(R.id.ib_diet_image_2);
        dietImage3 = findViewById(R.id.ib_diet_image_3);
        dietImage4 = findViewById(R.id.ib_diet_image_4);
        dietImage5 = findViewById(R.id.ib_diet_image_5);
        generalHealthImage1 = findViewById(R.id.ib_general_health_image_1);
        generalHealthImage2 = findViewById(R.id.ib_general_health_image_2);
        generalHealthImage3 = findViewById(R.id.ib_general_health_image_3);
        generalHealthImage4 = findViewById(R.id.ib_general_health_image_4);
        generalHealthImage5 = findViewById(R.id.ib_general_health_image_5);
        flatusPresenceProgress = findViewById();
        stoolPresenceProgress = findViewById();
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
                feedback.vomitElimination = getVomitingAndNauseaPresence();
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
        nauseaImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nauseaProgress.setText("1");
                nauseaImage1.setBackgroundColor(getResources().getColor(R.color.green));
                nauseaImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        nauseaImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nauseaProgress.setText("2");
                nauseaImage2.setBackgroundColor(getResources().getColor(R.color.green));
                nauseaImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        nauseaImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nauseaProgress.setText("3");
                nauseaImage3.setBackgroundColor(getResources().getColor(R.color.green));
                nauseaImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        nauseaImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nauseaProgress.setText("4");
                nauseaImage4.setBackgroundColor(getResources().getColor(R.color.green));
                nauseaImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        nauseaImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nauseaProgress.setText("5");
                nauseaImage5.setBackgroundColor(getResources().getColor(R.color.green));
                nauseaImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                nauseaImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        nauseaPresenceYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nauseaPresenceProgress.setText("Sim");
                nauseaPresenceYes.setBackgroundColor(getResources().getColor(R.color.green));
                nauseaPresenceNo.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        nauseaPresenceNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nauseaPresenceProgress.setText("Não");
                nauseaPresenceNo.setBackgroundColor(getResources().getColor(R.color.green));
                nauseaPresenceYes.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        diuresisImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diuresisProgress.setText("1");
                diuresisImage1.setBackgroundColor(getResources().getColor(R.color.green));
                diuresisImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        diuresisImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diuresisProgress.setText("2");
                diuresisImage2.setBackgroundColor(getResources().getColor(R.color.green));
                diuresisImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        diuresisImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diuresisProgress.setText("3");
                diuresisImage3.setBackgroundColor(getResources().getColor(R.color.green));
                diuresisImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        diuresisImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diuresisProgress.setText("4");
                diuresisImage4.setBackgroundColor(getResources().getColor(R.color.green));
                diuresisImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        diuresisImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diuresisProgress.setText("5");
                diuresisImage5.setBackgroundColor(getResources().getColor(R.color.green));
                diuresisImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                diuresisImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        dietImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietProgress.setText("1");
                dietImage1.setBackgroundColor(getResources().getColor(R.color.green));
                dietImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        dietImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietProgress.setText("2");
                dietImage2.setBackgroundColor(getResources().getColor(R.color.green));
                dietImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        dietImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietProgress.setText("3");
                dietImage3.setBackgroundColor(getResources().getColor(R.color.green));
                dietImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        dietImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietProgress.setText("4");
                dietImage4.setBackgroundColor(getResources().getColor(R.color.green));
                dietImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        dietImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietProgress.setText("5");
                dietImage5.setBackgroundColor(getResources().getColor(R.color.green));
                dietImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dietImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        generalHealthImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalHealthProgress.setText("1");
                generalHealthImage1.setBackgroundColor(getResources().getColor(R.color.green));
                generalHealthImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        generalHealthImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalHealthProgress.setText("2");
                generalHealthImage2.setBackgroundColor(getResources().getColor(R.color.green));
                generalHealthImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        generalHealthImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalHealthProgress.setText("3");
                generalHealthImage3.setBackgroundColor(getResources().getColor(R.color.green));
                generalHealthImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        generalHealthImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalHealthProgress.setText("4");
                generalHealthImage4.setBackgroundColor(getResources().getColor(R.color.green));
                generalHealthImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        generalHealthImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalHealthProgress.setText("5");
                generalHealthImage5.setBackgroundColor(getResources().getColor(R.color.green));
                generalHealthImage1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                generalHealthImage4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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

    private String getVomitingAndNauseaPresence(){
        return nauseaPresenceProgress.getText().toString();
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
        return flatusPresenceProgress.getText().toString();
    }

    private String getStoolElimination()
    {
        return stoolPresenceProgress.getText().toString();
    }

}
