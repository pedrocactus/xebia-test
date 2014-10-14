package com.xebiatest.boxotop.app.ui.view;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xebiatest.boxotop.app.R;
import com.xebiatest.boxotop.app.model.Actor;
import com.xebiatest.boxotop.app.model.Movie;
import com.xebiatest.boxotop.app.ui.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pierrecastex on 28/09/2014.
 *
 * Adapter for different layout in items
 */
public class DetaiListAdapter extends BaseAdapter {

    private ArrayList<Map<String, Object>> detailItems;
    private Context context;

    public enum DetailType{CAST("cast",R.layout.image_gridview_layout),SIMILAR_MOVIES("similar_movies",R.layout.image_gridview_layout),SYNOPSIS("synopsis",R.layout.item_list_detail_info);
        private final int res;
        private final String value;

        private DetailType(String value, int res) {
            this.res = res;
            this.value=value;
        }
        public int getRes(){return res;}
        public String getValue() {
            return value;
        }}

    public static String TYPE="type";
    public static String TITLE = "title";
    public static String BODY = "body";

    public DetaiListAdapter(Context context) {

        this.context = context;
        this.detailItems = new ArrayList<Map<String, Object>>();
    }


    @Override
    public int getCount() {
        return detailItems.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return detailItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(((DetailType)getItem(position).get(TYPE)).getValue().equals(DetailType.SYNOPSIS.getValue())) {


            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.item_list_detail_info, parent, false);
            }
            TextView titleTextView = (TextView) ViewHolder.get(convertView, R.id.detail_review_title);
            TextView bodyTextView = ViewHolder.get(convertView, R.id.detail_review_body);
            titleTextView.setText(Html.fromHtml((String) getItem(position).get(TITLE)));
            bodyTextView.setText((String) getItem(position).get(BODY));


        }else if (((DetailType)getItem(position).get(TYPE)).getValue().equals(DetailType.SIMILAR_MOVIES.getValue())){


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LinearLayout baselayout = (LinearLayout)inflater
                    .inflate(R.layout.image_gridview_layout, parent, false);
            LinearLayout hsvLinearLayout = (LinearLayout)baselayout.findViewById(R.id.hsv_linear_layout);
            TextView hsvTitleTextView = (TextView)baselayout.findViewById(R.id.hsv_title);
	        hsvTitleTextView.setText(Html.fromHtml((String) getItem(position).get(TITLE)));
            List<Movie> movies = ((List<Movie>) getItem(position).get(BODY));

            for (int i = 0; i < movies.size(); i++) {
                LinearLayout layout = (LinearLayout)LayoutInflater.from(context)
                        .inflate(R.layout.image_text_layout, null, false);
                TextView text = (TextView)layout.findViewById(R.id.legend);
                ImageView image = (ImageView) layout.findViewById(R.id.imageview_legend);
                text.setText(movies.get(i).getTitle());
                Picasso.with(context)
                        .load(movies.get(i).getPosters().getDetailed())
                        .placeholder(R.drawable.placeholder_movie)
                        .into(image);
                hsvLinearLayout.addView(layout);
            }
            convertView = baselayout;


        }else if (((DetailType)getItem(position).get(TYPE)).getValue().equals(DetailType.CAST.getValue())){
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.item_list_detail_info, parent, false);
            }
            TextView titleTextView = (TextView) ViewHolder.get(convertView, R.id.detail_review_title);
            TextView bodyTextView = ViewHolder.get(convertView, R.id.detail_review_body);
            List<Actor> actors =(List<Actor>) getItem(position).get(BODY);
            String actorsNames = ((Actor)actors.get(0)).getName();
            for (int i = 1; i < actors.size(); i++) {
                actorsNames += ", "+((Actor)actors.get(i)).getName();
            }
            actorsNames+=".";
            titleTextView.setText(Html.fromHtml((String) getItem(position).get(TITLE)));
            bodyTextView.setText(actorsNames);
        }

        return convertView;
    }

    public void putData(ArrayList<Map<String, Object>> datas){
        detailItems.clear();
        detailItems.addAll(datas);
        notifyDataSetChanged();
    }
}
