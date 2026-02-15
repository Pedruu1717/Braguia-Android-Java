package com.braguia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.braguia.R;
import com.braguia.model.Trail;
import com.braguia.viewModel.PinViewModel;
import com.braguia.viewModel.TrailsViewModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TrailListFragment extends Fragment implements TrailsRecyclerViewAdapter.ItemClickListener {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    private TrailsViewModel trailsViewModel;
    private PinViewModel pinViewModel;

    // private List<Trail> trails = new ArrayList<>();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrailListFragment() {
    }


    public static TrailListFragment newInstance(int columnCount) {
        TrailListFragment fragment = new TrailListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        trailsViewModel = new ViewModelProvider(this).get(TrailsViewModel.class);
        trailsViewModel.getAllTrails().observe(getViewLifecycleOwner(), x -> {
                loadRecyclerView(view, x);
        });

        pinViewModel = new ViewModelProvider(this).get(PinViewModel.class);
        pinViewModel.getAllPins().observe(getViewLifecycleOwner(), pins -> {
            if (pins == null) {
                Log.i("Null", "Expected Pins found null.");
            } else {
                Log.i("Pins found", "Pins loaded!");
            }
        });

        return view;
    }


    private void loadRecyclerView(View view, List<Trail> trails){
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Adicionar listener.
            TrailsRecyclerViewAdapter adapter = new TrailsRecyclerViewAdapter(trails);
            adapter.addItemClickListener(this);

            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), TrailInfoActivity.class);
        intent.putExtra("trail_id", position + 1);
        startActivity(intent);
    }

}
