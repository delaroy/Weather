package com.delaroystudios.weather.di;

import com.delaroystudios.weather.ui.settings.SettingsFragment;
import com.delaroystudios.weather.ui.weekly.WeeklyFragment;
import com.delaroystudios.weather.ui.today.TodayFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract TodayFragment contributeTodayFragment();

    @ContributesAndroidInjector
    abstract WeeklyFragment contributeWeeklyFragment();

    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();
}
