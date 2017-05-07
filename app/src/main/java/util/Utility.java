package util;


import android.util.Log;
import org.json.JSONObject;

public class Utility  {
    public static String resultCode="";
    public static String resultContent="";
    public static void handleLoginReturnInfo(String resopnse){
        try {
            JSONObject jsonObject = new JSONObject(resopnse);
            String code = jsonObject.getString("code");
            String content = jsonObject.getString("content");
            Log.i("------code与content分别为", code+"与"+content);
            resultCode=code;
            resultContent=content;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
