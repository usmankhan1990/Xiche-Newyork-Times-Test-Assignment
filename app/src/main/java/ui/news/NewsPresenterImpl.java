package ui.news;

import java.util.ArrayList;
import model.NYTData;

public interface NewsPresenterImpl {

    void showAllResult(ArrayList<NYTData> results);
    void searchFilter(String query);
}
