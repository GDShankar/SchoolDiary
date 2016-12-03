package com.elbissopsoft.schooldiary;

/**
 * Created by Thyagu on 11/7/2016.
 */

public class Config {


    private static final String domain_name= "http://elbissopdemo.com/schooldiary/";

    public static final String get_homework_URL= domain_name+"homework.php?";
    public static final String get_testmarks_URL= domain_name+"testmark.php?";
    public static final String get_timetable_URL= domain_name+"timetable.php?date=";
    public static final String get_events_URL= domain_name+"events.php?";
    public static final String LOGIN_URL = domain_name+"students_login.php";
    public static final String FEEDBACK_URL = domain_name+"feedback.php?";
    public static final String d_eng= "english";
    public static final String d_tam= "tamil";
    public static final String d_sci= "science";
    public static final String d_soc= "social";
    public static final String d_mat= "maths";
    public static final String d_rem= "remarks";
    public static final String JSON_ARRAY = "result";
    public static final String e_events= "events";
    public static final String e_edesc= "event_desc";
    public static final String e_eldesc= "longdesc";
    public static final String e_date= "date";
    public static final String KEY_reg_no="reg_no";
    public static final String KEY_pwd="pwd";
    public static final String FEEDBACK="feedback";


    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myschoolapp";

    //This would be used to store the email of current logged in user
    public static final String REGNO_SHARED_PREF = "reg_no";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

}
