package lenddo.com.lenddoconnect;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.lenddo.data.AndroidData;

/**
 * Created by Joey Mar Antonio on 11/18/16.
 */
public class Utils {

    public static String convertObjectToJsonString(Object obj) {
        return new GsonBuilder().serializeSpecialFloatingPointValues().disableHtmlEscaping().create().toJson(obj);
    }

    public static String returnNullIfEmpty(String value) {
        if (value.isEmpty()) return null;
        else return value;
    }

    public static String getProviderString(int position) {
        String provider;
        switch (position) {
            case 0:
                provider = AndroidData.PROVIDER_FACEBOOK;
                break;
            case 1:
                provider = AndroidData.PROVIDER_LINKEDIN;
                break;
            case 2:
                provider = AndroidData.PROVIDER_YAHOO;
                break;
            case 3:
                provider = AndroidData.PROVIDER_WINDOWSLIVE;
                break;
            case 4:
                provider = AndroidData.PROVIDER_GOOGLE;
                break;
            case 5:
                provider = AndroidData.PROVIDER_KAKAOTALK;
                break;
            case 6:
                provider = AndroidData.PROVIDER_TWITTER;
                break;
            default:
                provider = "";
                break;
        }
        return provider;
    }

    public static boolean isValidJson(String json) {
        boolean isValid = true;
        try {
            new JsonParser().parse(json).getAsJsonObject();
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }

}
