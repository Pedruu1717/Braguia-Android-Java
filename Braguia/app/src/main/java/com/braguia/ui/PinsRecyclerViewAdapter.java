package com.braguia.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.braguia.R;
import com.braguia.model.Pin;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PinsRecyclerViewAdapter extends RecyclerView.Adapter<PinsRecyclerViewAdapter.ViewHolder> {

    private final List<Pin> mValues;

    public PinsRecyclerViewAdapter(List<Pin> items) {
        mValues = items;
    }

    private ItemClickListener mItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    public void addItemClickListener(PinsRecyclerViewAdapter.ItemClickListener listener) {
        mItemClickListener = listener;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());

        String pin_urls = mValues.get(position).getUrls();
        if (!pin_urls.isEmpty()) {
            String first_url = (pin_urls.split(";")[0]);
            String extension = first_url.substring(first_url.length() - 3);
            if(extension.equals("jpg") || extension.equals("png")) {
                String safe_url = first_url.replace("http", "https");
                Picasso.get().load(safe_url).into(holder.imageView);
            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView imageView;
        public Pin mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            imageView = view.findViewById(R.id.cardimage);
        }

        @Override
        public String toString() {
            return super.toString() + mIdView;
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}