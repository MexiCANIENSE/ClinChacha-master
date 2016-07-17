package com.edu.paytonramirezg.clinchacha.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.edu.paytonramirezg.clinchacha.R;

import java.util.List;

public class CardHistoryAdapter extends RecyclerView.Adapter<CardHistoryAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    //List of services
    List<History> records;

    public CardHistoryAdapter(List<History> records, Context context){
        super();
        //Getting all the superheroes
        this.records = records;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        History record =  records.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(record.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_logoclin, android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(record.getImageUrl(), imageLoader);
        holder.textViewName.setText(record.getName());
        holder.textViewDate.setText(record.getDate());
        holder.textViewAddress.setText(record.getAddress());
        holder.textViewImport.setText(record.getImportt());
        holder.textViewRank.setText(String.valueOf(record.getRank()));

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewDate;
        public TextView textViewAddress;
        public TextView textViewImport;
        public TextView textViewRank;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.iv_cliner);
            textViewName = (TextView) itemView.findViewById(R.id.tv_name_cliner);
            textViewDate = (TextView) itemView.findViewById(R.id.tv_date);
            textViewAddress = (TextView) itemView.findViewById(R.id.tv_address);
            textViewImport = (TextView) itemView.findViewById(R.id.tv_import);
            textViewRank= (TextView) itemView.findViewById(R.id.tv_rating);

        }
    }

}
