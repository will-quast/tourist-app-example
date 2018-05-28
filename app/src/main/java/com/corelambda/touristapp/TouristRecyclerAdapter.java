package com.corelambda.touristapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.corelambda.touristapp.model.WikipediaPage;

import java.util.List;

public class TouristRecyclerAdapter extends RecyclerView.Adapter<TouristRecyclerAdapter.TouristViewHolder> {

    private List<WikipediaPage> items;
    private RequestManager imageRequestManager;

    public TouristRecyclerAdapter (List<WikipediaPage> items, RequestManager imageRequestManager) {
        this.items = items;
        this.imageRequestManager = imageRequestManager;
    }

    @NonNull
    @Override
    public TouristViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.place_list_item, parent, false);
        TouristViewHolder viewHolder = new TouristViewHolder(view, imageRequestManager);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TouristViewHolder holder, int position) {
        holder.bindView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class TouristViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        private RequestManager imageRequestManager;

        public TouristViewHolder(View view, RequestManager imageRequestManager) {
            super(view);
            this.textView = view.findViewById(R.id.textView);
            this.imageView = view.findViewById(R.id.imageView);
            this.imageRequestManager = imageRequestManager;
        }

        private void bindView(WikipediaPage page) {
            textView.setText(page.getTitle());


            if (page.getThumbnail() != null && page.getThumbnail().getSource() != null) {
                // use the image loader to download the image and display it
                imageRequestManager
                        .load(page.getThumbnail().getSource())
                        .apply(new RequestOptions()
                                .circleCrop()
                                .placeholder(android.R.drawable.btn_star))
                        .into(imageView);
            } else {
                // important! you need to reset the image to the default, this view may be recycled.
                imageView.setImageResource(android.R.drawable.btn_star);
            }
        }
    }
}
