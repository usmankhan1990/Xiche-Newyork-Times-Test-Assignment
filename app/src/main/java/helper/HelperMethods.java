package helper;

import network.retrofit.RetrofitCallsInterface;
import network.retrofit.RetrofitClient;

public class HelperMethods {

    private static HelperMethods ourInstance = null;

    public static HelperMethods getInstance() {
        if (ourInstance == null) {
            ourInstance = new HelperMethods();
            return ourInstance;
        }
        return ourInstance;
    }

    public static RetrofitCallsInterface getRetrofitInterface(String BASE_URL) {
        return RetrofitClient.getClient(BASE_URL).create(RetrofitCallsInterface.class);
    }
}
