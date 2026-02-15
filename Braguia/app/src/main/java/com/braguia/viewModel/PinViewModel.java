package com.braguia.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.braguia.model.Pin;
import com.braguia.repositories.PinRepository;

import java.util.List;

public class PinViewModel extends AndroidViewModel {

    private PinRepository repository;

    public LiveData<List<Pin>> pins;

    public PinViewModel(@NonNull Application application) {
        super(application);
        repository= new PinRepository(application);
        pins = repository.getAllPins();
    }

    public LiveData<List<Pin>> getAllPins() { return pins; }

    public LiveData<Pin> getPinById(String id) {
        return repository.getPinById(id);
    }
}
