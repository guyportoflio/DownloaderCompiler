package com.presly.downloadercompiler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by presly on 06/02/2017.
 */

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private Callbacks callbacks;
    private int httpStatusCode;
    private String errorMessage;

    //constructor
    public ImageDownloader(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    //execute before do in background
    protected void onPreExecute() {
        callbacks.onAboutToBegin();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream inputStream = null;

        try {
            String link = params[0];

            URL url = new URL(link);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            httpStatusCode = connection.getResponseCode();
            if (httpStatusCode != HttpURLConnection.HTTP_OK) {
                errorMessage = connection.getResponseMessage();
                return null;
            }

            inputStream = connection.getInputStream();

            Bitmap downloadedBitmap = BitmapFactory.decodeStream(inputStream);

            return downloadedBitmap;
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            return null;
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
        }
    }

    protected void onPostExecute(Bitmap downloadedBitmap) {
        if (downloadedBitmap != null)
            callbacks.onSuccess(downloadedBitmap);
        else
            callbacks.onError(httpStatusCode, errorMessage);
    }

    public interface Callbacks {
        void onAboutToBegin();

        void onSuccess(Bitmap downloadedBitmap);

        void onError(int httpStatusCode, String errorMessage);
    }
}
