package ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.xiche.xiche_newyorktimes_assignment.R;

public class DetailActivity extends AppCompatActivity {

    private static String PUBLISHED_DATE = "published_date";
    private static String ABSTRACT_DATA = "abstractData";
    private static String SOURCE = "source";
    private static String BY_LINE = "byline";
    private static String TITLE = "title";
    private static String SECTION = "section";
    private static String URL = "url";

    private String publishDateValue = "";
    private String abstractDataValue = "";
    private String sourceValue = "";
    private String bylineValue = "";
    private String titleValue = "";
    private String sectionValue = "";
    private String urlValue = "";

    TextView txtTitle, txtDate, txtDescription, txtSource, txtAuthor, txtUrl, txtSection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initializeData();
        setDetailData();
    }

    private void initializeData() {

        txtTitle = findViewById(R.id.txtTitle);
        txtDate = findViewById(R.id.txtDate);
        txtDescription = findViewById(R.id.txtDescription);
        txtSource = findViewById(R.id.txtSource);
        txtSection = findViewById(R.id.txtSection);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtUrl = findViewById(R.id.txtUrl);
    }

    private void setDetailData() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        publishDateValue = intent.getStringExtra(PUBLISHED_DATE);
        abstractDataValue = bundle.getString(ABSTRACT_DATA);
        sourceValue = bundle.getString(SOURCE);
        bylineValue = bundle.getString(BY_LINE);
        titleValue = bundle.getString(TITLE);
        sectionValue = bundle.getString(SECTION);
        urlValue = bundle.getString(URL);

        txtTitle.setText(titleValue);
        txtDate.setText("Dated: " + publishDateValue);
        txtDescription.setText(abstractDataValue);
        txtSource.setText("Source : " + sourceValue);
        txtAuthor.setText("Author: " + bylineValue);
        txtSection.setText("Section: " + sectionValue);
        txtUrl.setText(urlValue);
    }

}