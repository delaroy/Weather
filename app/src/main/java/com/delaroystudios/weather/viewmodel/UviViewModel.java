package com.delaroystudios.weather.viewmodel;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.delaroystudios.weather.model.Resource;
import com.delaroystudios.weather.model.UviDb;
import com.delaroystudios.weather.repository.ForecastRepository;

import javax.inject.Inject;

public class UviViewModel extends ViewModel {

    private ForecastRepository forecastRepository;

    @SuppressWarnings("unchecked")
    @Inject
    public UviViewModel(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @VisibleForTesting
    public LiveData<Resource<UviDb>> fetchUvi(Double lat, Double lon) {
        return forecastRepository.fetchUvi(lat, lon);
    }
}
