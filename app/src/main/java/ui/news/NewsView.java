package ui.news;

import java.util.ArrayList;

import model.NYTData;

public interface NewsView {

    void getAllResponseData(ArrayList<NYTData> results);
}
