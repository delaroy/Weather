package com.delaroystudios.weather.di;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.delaroystudios.weather.viewmodel.ForecastViewModel;
import com.delaroystudios.weather.viewmodel.WeatherViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel.class)
    abstract ViewModel bindForecastViewModel(ForecastViewModel forecastViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(WeatherViewModelFactory factory);
}
