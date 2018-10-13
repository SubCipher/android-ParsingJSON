package com.stepwisedesigns.knax.jsonparsing;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSONActivity extends AppCompatActivity  implements LoaderCallbacks<List<Book>>{


    private static final String URL_REQUEST_STRING = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";

    private static final int BOOK_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);


        android.app.LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);


    }



    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle){

        return new JSONLoader(this,URL_REQUEST_STRING);
    }
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books){

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader){

    }


}
