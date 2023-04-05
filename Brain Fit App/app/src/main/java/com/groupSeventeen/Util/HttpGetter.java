package com.groupSeventeen.Util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Simon on 01.03.2018.
 */

public class HttpGetter extends AsyncTask<String, Void, String> {
    private String serverUrl = "";

    public HttpGetter(String url) {
        this.serverUrl = url;
    }

    // Uses the default url specified in the Configuration class
    public HttpGetter(){
        this(Configuration.ServerURL);
    }

    private String httpGetResult = "";

    @Override
    protected String doInBackground(String... params)
    {
        Log.d("INFORMATION","Reached HttpGetter");
        BufferedReader inBuffer = null;
        String url = this.serverUrl;
        String result = "fail";

        for(String parameter: params) {
            url = url + "/" + parameter;
        }

        try {
            // https://stackoverflow.com/questions/31433687/android-gradle-apache-httpclient-does-not-exist
            URL getter = new URL(url);
            Log.d("INFORMATION","URL: " + url);
            InputStream s = (getter.openConnection()).getInputStream();
            Log.e("INFORMATION","InputStream " +  s);
            result = convertInputStreamToString(s);
            Log.d("INFORMATION","Getter got " + result);
        } catch(Exception e) {
            /*
             * some exception handling should take place here
             */

            Log.e("INFORMATION","Getter got " + e);

        } finally {
            if (inBuffer != null) {
                try {
                    inBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  result;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException{
        Log.e("INFORMATION","try to convert");
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null) {
            result += line;
            Log.e("INFORMATION","Conversion to" + result);
        }

        inputStream.close();
        return result;

    }

    protected void onPostExecute(String result) {
        // this is used to access the result of the HTTP GET lateron
        httpGetResult = result;
    }

    /**
     * Returns the result of the operation if needed lateron.
     * @return
     */
    public String getResult(){
        return httpGetResult;
    }

}
