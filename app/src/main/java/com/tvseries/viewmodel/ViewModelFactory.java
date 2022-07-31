package com.tvseries.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tvseries.model.IDataFactory;

public class ViewModelFactory implements ViewModelProvider.Factory {
    IDataFactory service;

    public ViewModelFactory(IDataFactory service) {
        this.service = service;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TvSeriesListViewModel.class)) {
            return (T) new TvSeriesListViewModel(service);
        }

        return null;
        //throw IllegalArgumentException("Unknown ViewModel class")
    }
}
