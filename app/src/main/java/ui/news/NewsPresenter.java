package ui.news;

import java.util.ArrayList;
import java.util.Locale;
import model.NYTData;

public class NewsPresenter implements NewsPresenterImpl {

    private NewsView iResultView;
    private ArrayList<NYTData> nytDataResults;

    public NewsPresenter(NewsView iResultView, ArrayList<NYTData> nytDataResults) {
        this.iResultView = iResultView;
        this.nytDataResults = nytDataResults;

        ArrayList<NYTData> tmpResults = new ArrayList<>();
        tmpResults.addAll(nytDataResults);
        showAllResult(tmpResults);
    }

    @Override
    public void showAllResult(ArrayList<NYTData> results) {
        iResultView.getAllResponseData(results);
    }

    @Override
    public void searchFilter(String query) {
        ArrayList<NYTData> tmpResults = new ArrayList<>();
        if (query.equals("")) {
            tmpResults.addAll(nytDataResults);
        } else {
            for (NYTData result : nytDataResults) {
                if (result.getTitle().toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))) {
                    tmpResults.add(result);
                }
            }
        }

        showAllResult(tmpResults);
    }
}
