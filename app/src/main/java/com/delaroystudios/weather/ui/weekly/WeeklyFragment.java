package com.delaroystudios.weather.ui.weekly;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delaroystudios.weather.R;
import com.delaroystudios.weather.di.Injectable;
import com.delaroystudios.weather.model.SavedDailyForecast;
import com.delaroystudios.weather.util.SharedPreferences;
import com.delaroystudios.weather.util.Utility;
import com.delaroystudios.weather.viewmodel.ForecastViewModel;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeeklyFragment extends Fragment implements Injectable, WeeklyAdapter.ItemClickListener {
    private ForecastViewModel weeklyViewModel;
    private WeeklyAdapter adapter;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.city) TextView mcity;
    @BindView(R.id.date) TextView date;
    @BindView(R.id.condition) TextView condition;
    @BindView(R.id.weather_resource) ImageView weather_resource;
    @BindView(R.id.temp_condition) TextView temp_condition;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static WeeklyFragment create() {
        WeeklyFragment weeklyFragment = new WeeklyFragment();
        return weeklyFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_weekly, container, false);
        ButterKnife.bind(this, root);
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(),R.color.weekly_background));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeeklyAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        fetchData();
        return root;
    }

    private void fetchData() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        String city = SharedPreferences.getInstance(getContext()).getCity();
        String numDays = SharedPreferences.getInstance(getContext()).getNumDays();

        weeklyViewModel = new ViewModelProvider(this, viewModelFactory).get(ForecastViewModel.class);
        weeklyViewModel.fetchResults(city, numDays).observe(getViewLifecycleOwner(), result -> {
            List<SavedDailyForecast> dailyForecasts = result.data;
            mcity.setText(city);
            if (dailyForecasts != null && dailyForecasts.size() > 0) {

                adapter.setForecasts(dailyForecasts);

                weather_resource.setImageResource(Utility.getArtResourceForWeatherCondition(dailyForecasts.get(0).getWeatherid()));
                condition.setText(Utility.toTitleCase(dailyForecasts.get(0).getDescription()));
                date.setText(String.format("%s, %s", Utility.format(dailyForecasts.get(0).getDate()), Utility.formatDate(dailyForecasts.get(0).getDate())));

                if(timeOfDay >= 0 && timeOfDay < 12){
                    temp_condition.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getMorningTemp()));
                }else if(timeOfDay >= 12 && timeOfDay < 16){
                    temp_condition.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getDayTemp()));
                }else if(timeOfDay >= 16 && timeOfDay < 21){
                    temp_condition.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getEveningTemp()));
                }else if(timeOfDay >= 21 && timeOfDay < 24){
                    temp_condition.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getNightTemp()));
                }

                adapter.removeItem(0);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {

    }
}
