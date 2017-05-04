package com.weily.alumnibook;

import android.app.Application;
import android.content.Context;

import com.mystery0.tools.Logs.Logs;

public class App extends Application
{
    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        Logs.setLevel(Logs.LogLevel.Debug);
    }

    public static Context getContext()
    {
        return context;
    }
}
