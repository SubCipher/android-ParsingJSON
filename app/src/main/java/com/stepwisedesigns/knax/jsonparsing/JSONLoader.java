package com.stepwisedesigns.knax.jsonparsing;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class JSONLoader extends AsyncTaskLoader<List<Book>>{

    private static final String LOG_TAG = JSONLoader.class.getName();
    private String mUrl;

    public JSONLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }


    @Override
    public  List<Book>loadInBackground(){
        if(mUrl == null){
            return null;

        }
        List<Book> books = QueryUtil.fetchBookData(mUrl);
        return books;
    }

}