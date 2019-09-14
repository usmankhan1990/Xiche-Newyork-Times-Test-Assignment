package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiche.xiche_newyorktimes_assignment.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.NYTData;
import ui.detail.DetailActivity;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<NYTData> results;

    public NewsFeedAdapter(Context context, ArrayList<NYTData> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_feeds_list, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       final NYTData result = results.get(position);

        holder.ll_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result != null) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("published_date", result.getPublishedDate());
                    intent.putExtra("abstractData", result.getAbstract());
                    intent.putExtra("source", result.getSource());
                    intent.putExtra("byline", result.getByline());
                    intent.putExtra("title", result.getTitle());
                    intent.putExtra("section", result.getSection());
                    intent.putExtra("url", result.getUrl());
                    context.startActivity(intent);
                }
            }
        });

        holder.txtTitle.setText(result.getTitle());
        holder.txtAuthor.setText(result.getByline());
        holder.txtCreatedDate.setText(result.getPublishedDate());
        DecimalFormat decimalFormat = new DecimalFormat("##0");
        holder.txtViews.setText(decimalFormat.format((Math.round(result.getViews() * 100.0) / 100.0)));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private View itemView;

        private TextView txtTitle, txtAuthor, txtCreatedDate, txtViews;

        private LinearLayout ll_Main;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            ll_Main = itemView.findViewById(R.id.ll_Main);
            txtCreatedDate = itemView.findViewById(R.id.txtCreatedDate);
            txtViews = itemView.findViewById(R.id.txtViews);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
