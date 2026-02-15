package com.braguia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.braguia.R;
import com.braguia.model.Pin;
import com.braguia.viewModel.PinViewModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class PinListFragment extends Fragment implements PinsRecyclerViewAdapter.ItemClickListener {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    private PinViewModel pinsViewModel;

    // private List<Pin> pins = new ArrayList<>();



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PinListFragment() {
    }

    public static PinListFragment newInstance(int columnCount) {
        PinListFragment fragment = new PinListFragment();
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

        pinsViewModel = new ViewModelProvider(this).get(PinViewModel.class);
        pinsViewModel.getAllPins().observe(getViewLifecycleOwner(), x -> {
                loadRecyclerView(view, x);
        });

        return view;
    }

    private void loadRecyclerView(View view, List<Pin> pins){
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Adicionar listener.
            PinsRecyclerViewAdapter adapter = new PinsRecyclerViewAdapter(pins);
            adapter.addItemClickListener(this);

            recyclerView.setAdapter(adapter);
           // recyclerView.setAdapter(new PinsRecyclerViewAdapter(pins));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), PinInfoActivity.class);
        intent.putExtra("pin_id", position + 1);
        startActivity(intent);
    }

}