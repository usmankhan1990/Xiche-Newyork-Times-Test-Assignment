# Xiche Newyork Times Test Assignment
 This is a test assignment for interview purpose which uses Newyork times api's for popular news.

# Newyork Times Most Popular Feeds
- This application is to show the most popular Newyork Times post. It is a master / detail app. It shows detail when items on the list are tapped.

## Requirement for the assignment

The requirements are as follows:
1)	It is based on object oriented approach.
2) It covers **MVP Architecture** for better approach and readibility.
3) **Unit Tests** are covered for API Request Model along with **Intrumental Espresso** tests
4) It included Singleton design Pattern, MVP, Retrofit SDK, latest dependencies. All succesful test cases.
5) It also contain **Coverage Reports**. Check it in app > reports > androidTests > connected > index.html


## Configuration and code snippets

In **RestServicesCalls** class, I am using it to connected with Rest Api which is based on ServiceResponseModel by using Retrofit Api's.

```Java
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
```

