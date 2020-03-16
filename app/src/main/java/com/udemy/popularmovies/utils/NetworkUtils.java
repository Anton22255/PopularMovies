package com.udemy.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    public static final String KEY = "cc77263e88d3221dc890a327ff6efe19";
    final static String QUERY_PARAM = "api_key";
    private static final String STATIC_MOVIE_DB_URL = "https://api.themoviedb.org";
    private static final String API_VERSION = "3";

    public static URL buildURL(String mediaType, String filter) {
        Uri builtUri = Uri.parse(STATIC_MOVIE_DB_URL).buildUpon()
                .appendPath(API_VERSION)
                .appendPath(mediaType)
                .appendPath(filter)
                .appendQueryParameter(QUERY_PARAM, KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
