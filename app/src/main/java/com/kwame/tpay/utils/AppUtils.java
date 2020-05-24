package com.kwame.tpay.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.models.Driver;

public class  AppUtils {

    public static final String BASE_URL = "http://51.141.36.140/api/";
    public static final String MTN = "Mtn";
    public static final String AIRTELTIGO = "AirtelTigo";
    public static final String VODAFONE = "Vodafone";


    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void print(String msg){
       System.out.println(msg);
    }

    public static boolean isValid(String ...strs) {
        for (String str : strs){
            if (TextUtils.isEmpty(str))
                return false;
        }
        return true;
    }

    public static ProgressDialog buildLoading(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }

    public static void updateDriver(Driver driver) {
        Auth auth = GoodPrefs.getInstance().getObject("user", Auth.class);
        auth.setDriver(driver);
        GoodPrefs.getInstance().saveObject("user", auth);
    }

    public static String getToken() {
        return GoodPrefs.getInstance().getObject("user", Auth.class).getToken();
    }

    public static void saveLogin() {
        GoodPrefs.getInstance().saveBoolean("loggedIn", true);
    }

    public static boolean isLoggedIn() {
        return GoodPrefs.getInstance().getBoolean("loggedIn", false);
    }

    public static Auth getUser() {
        return GoodPrefs.getInstance().getObject("user", Auth.class);
    }



}
