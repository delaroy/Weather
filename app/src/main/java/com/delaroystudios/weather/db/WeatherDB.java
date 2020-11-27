package com.delaroystudios.weather.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.delaroystudios.weather.model.SavedDailyForecast;
import com.delaroystudios.weather.model.UviDb;

@Database(entities = {SavedDailyForecast.class, UviDb.class},
        version = 1,
        exportSchema = false)

public abstract class WeatherDB extends RoomDatabase {
    abstract public ForecastDao forecastDao();
}
