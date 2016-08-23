package com.example.kenjihosaka.sampleandroid.app.timeline.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kenjihosaka.sampleandroid.R;
import com.example.kenjihosaka.sampleandroid.model.domain.entity.TDnetArticle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TDnetArticleListAdapter extends ArrayAdapter<TDnetArticle> {

    private LayoutInflater layoutInflater;
    private int layoutId;
    private Context context;

    public TDnetArticleListAdapter(Context context, int resource) {
        super(context, resource);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutId = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            view = layoutInflater.inflate(layoutId, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        TDnetArticle article = getItem(position);
        holder.company.setText(article.getCompany());
        holder.title.setText(article.getTitle());
        holder.pubdate.setText(article.getPubdate());

        if (article.isRead()) {
            view.setBackgroundResource(R.drawable.bg_list_element);
        } else {
            view.setBackgroundResource(R.drawable.bg_list_element_yellow);
        }

        return view;
    }


    class ViewHolder {

        @BindView(R.id.company) TextView company;
        @BindView(R.id.title)   TextView title;
        @BindView(R.id.pubdate) TextView pubdate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
