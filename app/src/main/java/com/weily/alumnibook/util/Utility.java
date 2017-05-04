package com.weily.alumnibook.util;

import android.util.Log;
import org.json.JSONObject;

public class Utility  {
    public static String code2="";
    public static String content2="";
    public static void handleLoginReturnInfo(String resopnse){
        try {
            JSONObject jsonObject = new JSONObject(resopnse);
            String code = jsonObject.getString("code");
            String content = jsonObject.getString("content");
            Log.i("------code与content分别为", code+"与"+content);
            code2=code;
            content2=content;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}