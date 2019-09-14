package network.networkcalls;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import helper.HelperMethods;
import helper.NetworkStatus;
import network.retrofit.RetrofitCallsInterface;
import network.retrofit.model.ServiceResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestServicesCalls {

    private RetrofitCallsInterface rfInterface = null;

    HelperMethods helperMethods = HelperMethods.getInstance();

    public RestServicesCalls(String service) {
        rfInterface = helperMethods.getRetrofitInterface(service);
    }


    public class ServerCall {

        /**
         * <p>This method sends request and gives Success or Failure response in return</p>
         *  @param context   - Activity context
         *  @param apikey   - Server api key
         *  @param nwCall   - Network Status
         *  It will return a retrofit client
         */

        public void getDetails(final Context context, String apikey , final NetworkStatus nwCall) {
            nwCall.onStart(context, "Please Wait....");

            rfInterface.getDetails(apikey).enqueue(new Callback<ServiceResponseModel>() {
                @Override
                public void onResponse(Call<ServiceResponseModel> call, Response<ServiceResponseModel> response) {
                    try {
                        nwCall.onComplete();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", response.body().getResults());

                        nwCall.onSuccessResponse(bundle);

                    } catch (Exception e) {
                        Log.e("getDetail Error", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponseModel> call, Throwable t) {
                    nwCall.onComplete();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("message", "Something went wrong, please try again later");
                    nwCall.onFailureResponse(bundle);
                }
            });

        }
    }
}
