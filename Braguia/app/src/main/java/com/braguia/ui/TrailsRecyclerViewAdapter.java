package com.braguia.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.braguia.R;
import com.braguia.model.Trail;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TrailsRecyclerViewAdapter extends RecyclerView.Adapter<TrailsRecyclerViewAdapter.ViewHolder> {

    private final List<Trail> mValues;
    public TrailsRecyclerViewAdapter(List<Trail> items) {
        mValues = items;
    }
    private ItemClickListener mItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }


    public void addItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position); // Trail
        holder.mIdView.setText(mValues.get(position).getTrailName());
        Picasso.get().load(mValues.get(position)
                .getUrl().replace("http", "https"))
                .into(holder.imageView);

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
        public Trail mItem;

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