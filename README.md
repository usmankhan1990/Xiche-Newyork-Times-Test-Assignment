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

In **RetrofitClient** class, This class use to connect with Retrofit client.

```Java
    /**
     * <p>This method makes retrofit client to connect and API</p>
     *  @param BASE_URL1   - Server api base URL
     *  It will return a retrofit client
     */
    public static Retrofit getClient(String BASE_URL1) {
        BASE_URL = BASE_URL1;
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
            retrofit = new Retrofit.Builder().client(clientBuilder.build()).baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
```

**Design Patterns:** 

Singleton Design patterns: 

```Java

public class HelperMethods {

    private static HelperMethods ourInstance = null;

    public static HelperMethods getInstance() {
        if (ourInstance == null) {
            ourInstance = new HelperMethods();
            return ourInstance;
        }
        return ourInstance;
    }

}

private HelperMethods helperMethods = HelperMethods.getInstance();

```

MVP: 

For classes are used in this Architecture:

```Java

NewsActivity
NewsPresenter
NewsPresenterImpl
NewsView

```

**API Data Model class**

Following is the Data Model class for the Rest Api:

NYTData

```Java

public class NYTData implements Serializable {

    @SerializedName("asset_id")
    private Long mAssetId;
    @SerializedName("byline")
    private String mByline;
    @SerializedName("id")
    private Long mId;
    @SerializedName("published_date")
    private String mPublishedDate;
    @SerializedName("section")
    private String mSection;
    @SerializedName("source")
    private String mSource;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("abstract")
    private String mAbstract;
    @SerializedName("views")
    private Double mViews;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public Long getAssetId() {
        return mAssetId;
    }

    public void setAssetId(Long assetId) {
        mAssetId = assetId;
    }

    public String getByline() {
        return mByline;
    }

    public void setByline(String byline) {
        mByline = byline;
    }

    public String getAbstract() {
        return mAbstract;
    }

    public void setAbstract(String mabstract) {
        mAbstract = mabstract;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        mPublishedDate = publishedDate;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Double getViews() {
        return mViews;
    }

    public void setViews(Double views) {
        mViews = views;
    }
}

```
