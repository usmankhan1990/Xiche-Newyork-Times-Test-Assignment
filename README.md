# Xiche Newyork Times Test Assignment
 This is a test assignment for interview purpose which uses Newyork times api's for popular news / posts.

# Newyork Times Most Popular Feeds
- This application is to show the most popular Newyork Times post. It is a master / detail app. It shows detail when items on the list are tapped. I made a sample account on Newyork times developer portal to register my App there to get the API keys.

## Requirement for the assignment

The requirements are as follows:
1)	It is based on object oriented approach.
2) It covers **MVP Architecture** for better approach and readibility.
3) **Unit Tests** are covered for API Request Model along with **Intrumental Espresso** tests
4) It included Singleton design Pattern, MVP, Retrofit SDK, latest dependencies. All succesful test cases.
5) It also contain **Coverage Reports**. Check it in app > reports > androidTests > connected > index.html

## Dependencies used in this project

Following Dependencies, libraries and SDK are used in this project:

1)	**Retrofit** (com.squareup.retrofit2:retrofit:2.3.0) - For Rest API operations.
2) **Sweet Alert Dialog** (cn.ziyeyouhu.android:sweet-alert-dialog:1.0) - For Material Design Popups, Dialogs, Progressbars.
3) **Butterknife** (com.jakewharton:butterknife:8.8.1) - For Data binding
4) **Espresso** (com.android.support.test.espresso:espresso-core:3.0.2) - For Espresso tests
5) **Unit Testing** (junit:junit:4.12) - For Unit tests.
6) **Android Support** (com.android.support:design:28.0.0)

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

## Unit Tests & Instrumental Espresso Tests

**Unit Tests**

```Java
public class NewsRequestsTest {

    private RetrofitCallsInterface rfInterface = null;
    Call<ServiceResponseModel> rmDataCall = null;
    Response<ServiceResponseModel> rmDataResponse = null;
    private HelperMethods helperMethods = HelperMethods.getInstance();
    private Constants constants = Constants.getInstance();

    @Before
    public void setUp() throws Exception {

        rfInterface = helperMethods.getRetrofitInterface(constants.service);
        rmDataCall = rfInterface.getDetails(constants.apikey);
        rmDataResponse = rmDataCall.execute();

    }

    @Test
    public void success200_ResponseCode() throws IOException {

        assertEquals(200, rmDataResponse.code());
        assertEquals(true, rmDataResponse.isSuccessful());
    }

    @Test
    public void successOK_ResponseCode() throws IOException {

        assertEquals("OK", rmDataResponse.message());
    }

    @Test
    public void getArticleSourceText() throws IOException {

        Assert.assertEquals( "The New York Times", rmDataResponse.body().getResults().get(0).getSource() );
    }

    @After
    public void tearDown() {
        rfInterface = null;
    }
}
```
**Instrumental Tests**

```Java

@RunWith(AndroidJUnit4.class)
public class NewsActivityTest {

    private RetrofitCallsInterface rfInterface = null;
    private NewsPresenter newsPresenter;
    private Response<ServiceResponseModel> rmDataResponse = null;
    private HelperMethods helperMethods = HelperMethods.getInstance();
    private NewsActivity mainActivity = null;
    private Constants constants = Constants.getInstance();

    @Rule
    public ActivityTestRule<NewsActivity> activityTestRule = new ActivityTestRule<>(NewsActivity.class);

    @Before
    public void setUp() throws Exception {

        mainActivity = activityTestRule.getActivity();
        rfInterface = helperMethods.getRetrofitInterface(constants.service);

        Call<ServiceResponseModel> rmDataCall = rfInterface.getDetails(constants.apikey);
        rmDataResponse = rmDataCall.execute();

        if(rmDataResponse.body().getResults()!=null){
            newsPresenter= new NewsPresenter(mainActivity, rmDataResponse.body().getResults());
        }
    }

    @Test
    public void testNewsFeed_DrawerLayout() {
        View view = mainActivity.findViewById(R.id.drawer_layout);
        assertNotNull(view);
    }

    @Test
    public void testNewsFeed_RecyclerView() {
        View view = mainActivity.findViewById(R.id.rclView_news);
        assertNotNull(view);
    }

    @Test
    public void displayedTextLinkInDetailView() {
        onView(ViewMatchers.withId(R.id.rclView_news))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText("For more detail, click on the following link :")).check(matches(isDisplayed()));
    }

    @Test
    public void displayedAbstractTextView() {
        onView(ViewMatchers.withId(R.id.rclView_news))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.txtDescription)).check(matches(isDisplayed()));
    }

    @Test
    public void clickUrlTextLink() {
        onView(ViewMatchers.withId(R.id.rclView_news))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.txtUrl)).perform(click());
    }

    @After
    public void tearDown(){
        activityTestRule.finishActivity();
    }
}
```

## Screenshots

**Popular posts screen**


