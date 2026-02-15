package com.braguia.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.braguia.model.Edge;
import com.braguia.model.Trail;
import com.braguia.repositories.TrailRepository;

import java.util.List;

public class TrailsViewModel extends AndroidViewModel {

    private TrailRepository repository;

    public LiveData<List<Trail>> trails;

    public TrailsViewModel(@NonNull Application application) {
        super(application);
        repository= new TrailRepository(application);
        trails = repository.getAllTrails();
    }

    public LiveData<List<Trail>> getAllTrails() {

        return trails;
    }

    public LiveData<Trail> getTrailById(String id) {
        return repository.getTrailById(id);
    }

    public LiveData<List<Edge>> getEdgesByTrailId(String id) {
        return repository.getEdgesByTrailId(id);
    }
}
