package com.stepwisedesigns.knax.jsonparsing;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtil {

    private static final String LOG_TAG = "URL Exception";

    private static URL createUrl(String stringUrl){
        URL url = null;

        try{
            url = new URL(stringUrl);

        } catch ( MalformedURLException e){
            Log.e(LOG_TAG, "Problem buidling the URL ", e);
        }
        return url;
    }




    private static String makeHttpRequest(URL url) throws IOException {
        String jasonResponse = " ";
        if(url ==null){
            return jasonResponse;

        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jasonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code " + urlConnection.getResponseCode());
            }


        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the JSON results ", e);

        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jasonResponse;

    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtil} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtil() {
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Book> extractFeatureFromJSON(String jasonResponse) {

        if(TextUtils.isEmpty(jasonResponse)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Book> books = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        if (jasonResponse != null) {

            try {

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.

                JSONObject baseJsonResponse = new JSONObject(jasonResponse);

                JSONArray bookArray = baseJsonResponse.getJSONArray("items");


                for (int i = 0; i < bookArray.length(); i++) {
                    JSONObject currentBook = bookArray.getJSONObject(i);

                    JSONObject properties = currentBook.getJSONObject("volumeInfo");
                    JSONArray industryArray = properties.getJSONArray("industryIdentifiers");
                    Log.e("industryIdentiers", "value " + industryArray.getString(0));

                    String industry = industryArray.getString(0);

                    Log.e("industryValue", "industry value " + industry);

                    //Double rating = properties.getDouble("averageRating");


                    String bookTitle =  properties.getString("title");
                    Log.d("Books API", "values " + bookTitle );
                    String subtitle = getJsonString(properties,"subtitle");
                    String author = properties.getString("authors");

                    String publisher = properties.getString("publisher");
                    String ID = properties.getString("publishedDate");

                    String category = getJsonString(properties,"description");

                    Book book = new Book(bookTitle, author, publisher, ID,subtitle,category);

                    books.add(book);

                }

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the JSON results", e);
            }
        }

        // Return the list of earthquakes
        return books;
    }

   private static  String getJsonString(JSONObject jso, String field) {
        if(jso.isNull(field))
            return null;
        else
            try {
                return jso.getString(field);
            }
            catch(Exception ex) {
                Log.e("model", "Error parsing value");
                return null;
            }
    }

    public static List<Book> fetchBookData(String requestUrl){
        //Delay app launch to show progress bar
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        URL url = createUrl(requestUrl);

        String jasonResponse = null;
        try {
            jasonResponse = makeHttpRequest(url);

        } catch (IOException e){
            Log.e(LOG_TAG, "Problem making Http request ", e);

        }
        List<Book> books = extractFeatureFromJSON(jasonResponse);

        return books;
    }

}