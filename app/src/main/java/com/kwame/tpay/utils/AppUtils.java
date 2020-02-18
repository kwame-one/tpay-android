package com.kwame.tpay.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class  AppUtils {

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


}
