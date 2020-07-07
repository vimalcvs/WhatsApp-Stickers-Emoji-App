package com.vimalcvs.stickers_app.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetStickers extends AsyncTask<Void, Void, Void> {

    private String url, jsonResult;
    FileOutputStream outputStream;
    Context contexxt;
    boolean newstickerpacks = false;
    private Callbacks callbacks;

    public GetStickers(Context context, Callbacks callbacks,String url) {
        this.callbacks = callbacks;
        contexxt = context;
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... z) {
        try {
            URL urll =  new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urll.openConnection();

            jsonResult = inputStreamToString(connection.getInputStream())
                    .toString();
            Log.i("response", "doInBackground: " + jsonResult);

        } catch (IOException e){
            e.printStackTrace();
        }
        /*try {
            if (fileExistance("sticker_packs")) {
                InputStream is = contexxt.openFileInput("sticker_packs");
                String lastWallsFile;
                lastWallsFile = inputStreamToString(is).toString();
                Log.e("LastWallsFile:", lastWallsFile);
                newstickerpacks = !lastWallsFile.equals(jsonResult);
                writeWallFile();
            } else {
                try {
                    writeWallFile();
                } catch (Exception e) {
                    //Do nothing because something is wrong! Oh well this feature just wont work on whatever device.
                }
            }
        } catch (Exception ex) {
            //Do nothing because something is wrong! Oh well this feature just wont work on whatever device.
        }*/

        return null;
    }

    private boolean fileExistance(String fname) {
        File file = contexxt.getFileStreamPath(fname);
        return file.exists();
    }

    private void writeWallFile() {
        try {
            outputStream = contexxt.openFileOutput("sticker_packs", Context.MODE_PRIVATE);
            outputStream.write(jsonResult.getBytes());
            outputStream.close();
        } catch (Exception ex) {
            //Do nothing because something is wrong! Oh well this feature just wont work on whatever device.
        }
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (callbacks != null)
            callbacks.onListLoaded(jsonResult, newstickerpacks);
    }

    public interface Callbacks {
        void onListLoaded(String jsonResult, boolean jsonSwitch);
    }

}
