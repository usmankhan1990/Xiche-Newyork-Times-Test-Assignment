package com.xiche.xiche_newyorktimes_assignment;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import helper.Constants;
import helper.HelperMethods;
import network.retrofit.RetrofitCallsInterface;
import network.retrofit.model.ServiceResponseModel;
import retrofit2.Call;
import retrofit2.Response;
import ui.news.NewsActivity;
import ui.news.NewsPresenter;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;

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
//        Thread.sleep(1000);
//        mainActivity.finish();
        activityTestRule.finishActivity();
    }
}
