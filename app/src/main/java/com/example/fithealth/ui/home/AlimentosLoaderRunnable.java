package com.example.fithealth.ui.home;

import com.example.fithealth.Food;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class AlimentosLoaderRunnable implements Runnable {

    private final InputStream mInFile;
    private final OnFoodLoadedListener mOnReposLoadedListener;

    public AlimentosLoaderRunnable(InputStream inFile, OnFoodLoadedListener onReposLoadedListener){
        mInFile = inFile;
        mOnReposLoadedListener = onReposLoadedListener;
    }

    @Override
    public void run() {
        JsonReader reader = new JsonReader(new InputStreamReader(mInFile));
        // Parse JsonReader into list of Repo using Gson
        List<Food> repos = Arrays.asList(new Gson().fromJson(reader, Food[].class));
        for (Food r:repos){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                mOnReposLoadedListener.onReposLoaded(repos);
            }
        });
    }
}
