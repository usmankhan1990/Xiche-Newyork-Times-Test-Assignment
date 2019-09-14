package network.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import model.NYTData;

public class ServiceResponseModel implements Serializable {

    @SerializedName("results")
    private ArrayList<NYTData> mResults;
    public ArrayList<NYTData> getResults() {
        return mResults;
    }

}
