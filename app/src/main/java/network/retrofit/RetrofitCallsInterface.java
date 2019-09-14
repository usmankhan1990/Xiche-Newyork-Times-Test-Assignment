package network.retrofit;

import network.retrofit.model.ServiceResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitCallsInterface {

    @GET("7.json")
    Call<ServiceResponseModel> getDetails(@Query("api-key") String apikey);

}
