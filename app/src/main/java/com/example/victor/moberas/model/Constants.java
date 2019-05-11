package com.example.victor.moberas.model;

public class Constants {
    public static final int FEEDBACK = 0;
    public static final int UPDATE = 1;
    public static final int LOGOUT = 2;
    public static final int ORAL_DIET_INTAKE = 10;
    public static final int BLADDER_CATHETER_REMOVAL = 11;
    public static final int DIURESIS = 12;
    public static final int FLATUS = 13;
    public static final int STOOL = 14;
    public static final int DRIP = 15;
    public static final long HOUR_IN_MILLIS = 60 * 60 * 1000;
    public static final long NOTIFICATION_INTERVAL_IN_MILLIS = HOUR_IN_MILLIS;
    public static final long FEEDBACK_INTERVAL_IN_MILLIS = 5 * 60 * 1000;
    public static final long MOTION_INTERVAL = 180_000L;
    public static final long MOTION_INTERVAL_IN_MINUTES = MOTION_INTERVAL/(60 * 1000);
    public static final String MOTION_ACTION = "motion";
    public static final int REQUEST_CODE_MEDICAL = 1;
    public static final int REQUEST_CODE_FEEDBACK = 2;
    public static final int REQUEST_CODE_TIME_PICK = 3;
    public static final int RESULT_CODE_OK = -1;
    public static final int RESULT_CODE_CANCEL = 0;
    public static final String TAG_DB = "Tag_Database";
    public static final String TAG_AUTH = "Tag_Auth";
    public static final String TAG_SERVICE = "Tag_Service";
    public static final String TAG_RUNTIME = "Tag_Runtime";
    public static final String[] infoMessages = {"não se esqueça de movimentar as pernas!",
            "faça refeições na cadeira ao invés da cama!", "durante o dia, faça pelo menos oito caminhadas!",
            "procure ficar, se possível, 8 horas fora do leito!"};
}
