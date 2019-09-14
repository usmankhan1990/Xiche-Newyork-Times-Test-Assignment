package ui.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.xiche.xiche_newyorktimes_assignment.R;
import java.util.ArrayList;
import adapters.NewsFeedAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import helper.Constants;
import helper.NetworkStatus;
import model.NYTData;
import network.networkcalls.RestServicesCalls;

public class NewsActivity extends AppCompatActivity implements NewsView {

    private RestServicesCalls.ServerCall serverCall;
    private NewsFeedAdapter newsFeedAdapter;
    private NewsPresenter presenter;
    Constants constants = Constants.getInstance();

    @BindView(R.id.toolbar_newsfeed)
    Toolbar toolbar_newsfeed;

    @BindView(R.id.rclView_news)
    public RecyclerView rclView_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar_newsfeed);
        getSupportActionBar().setHomeButtonEnabled(true);

        rclView_news.setLayoutManager(new LinearLayoutManager(this));
        serverCall = new RestServicesCalls(constants.service).new ServerCall();
        getResponse();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.textSearch) {

            item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    presenter.searchFilter("");
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    return true;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <p>This is a callback function for creating menu for search edittext field</p>
     *  @param menu   - menu from the action bar
     *  This function make a MenuItem for showing search button, cross button, back button and edittext
     *  will return a list of News feeds
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem search = menu.findItem(R.id.textSearch);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * <p>This function gets Server call response.</p>
     *  It initiates a network call and give result for the presenter to display
     */

    private void getResponse() {
        serverCall.getDetails(NewsActivity.this, constants.apikey, new NetworkStatus(true) {

            @Override
            public void onSuccessResponse(Bundle msg) {
                ArrayList<NYTData> results = (ArrayList<NYTData>) msg.getSerializable("data");
                presenter = new NewsPresenter(NewsActivity.this, results);
            }

            @Override
            public void onFailureResponse(Bundle msg) {
                Toast.makeText(NewsActivity.this, msg.getString("message"), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * <p>This is a callback function returning an Arraylist of response to pass in NewsFeedAdapter</p>
     *  @param results   - ArrayList<NYTData>
     */

    @Override
    public void getAllResponseData(ArrayList<NYTData> results) {
        newsFeedAdapter = new NewsFeedAdapter(this, results);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                rclView_news.setAdapter(newsFeedAdapter);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}