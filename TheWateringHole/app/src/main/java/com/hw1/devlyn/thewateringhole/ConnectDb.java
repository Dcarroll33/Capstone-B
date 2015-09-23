package com.hw1.devlyn.thewateringhole;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Devlyn on 4/21/2015.
 */
public class ConnectDb extends AsyncTask<String, Void, Integer> {

    private Exception exception;

    private int result = -1;

    private int userId = -1;

    private int save = -1;

    private int eventSave = -1;

    private HttpClient httpclient;

    private HttpPost httppost;

    private ArrayList<NameValuePair> nameValuePairs;

    private HttpResponse response;

    private Bitmap bitmap;


    public static MyApplicationClass.MySQLAccess dao = new MyApplicationClass.MySQLAccess();

    public static MyApplicationClass.MySQLAccess getDao() {
        return dao;
    }

    protected Integer doInBackground(String... strings) {
        try {
            if (strings[0] == "register") {
                dao.readDataBase();
                Log.d("conDb", "Registration success");
                result = dao.registerUser(strings[1], strings[2], strings[3]);
                return result;

            } else if (strings[0] == "login") {
                dao.readDataBase();
                int result = dao.loginUser(strings[1], strings[2]);
                if (result != -1) {
                    Log.d("ConDB", "Login successful! User ID is: " + result);
                    userId = result;
                    return 0;
                } else {
                    Log.d("ConDB", "Login failed :'(");
                    return -1;
                }
            } else if (strings[0] == "save") {
                dao.readDataBase();
                int saveResult = dao.userProfile(strings[1], strings[2], strings[3], strings[4]);
                if (saveResult != -1) {
                    Log.d("ConDB", "Save successful");
                    save = saveResult;
                    return 0;
                } else {
                    return -1;
                }
            } else if (strings[0] == "eventSave") {
                dao.readDataBase();
                int eventResult = dao.userEvents(strings[1], strings[2], strings[3]);
                if (eventResult != -1) {
                    Log.d("EventSaved", "Event saved");
                    eventSave = eventResult;
                    return 0;
                } else {
                    return -1;
                }
            }
        } catch (Exception e) {
            Log.e("upload failed", e.toString());
        }
        return null;
    }
        /*return null;*/
        /*try {
            // Save the image to the SD card.
            //File file = new File(Environment.getExternalStorageDirectory(),
            //System.currentTimeMillis() + ".png");
            //FileOutputStream stream = new FileOutputStream(file);
            //bitmap.compress(CompressFormat.PNG, 100, stream);

            //convert to byte


            String name = "prescription";
            //save image to mysql


            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("jdbc:mysql://direct.cwardcode.com/wateringhole");
            nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", name));
         //   nameValuePairs.add(new BasicNameValuePair("image", imagedata));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();

            Log.e("Connection", "connection success ");
          //  Log.e("bitmap", imagedata);

        } catch (Exception e) {
            Log.e("upload failed", e.toString());
        }
        return null;
    }*/

    public int getResult(){
        return result;
    }

    public int getUserId(){
        return userId;
    }

    public int getSave(){
        return save;
    }

    public int getEventSave(){
        return eventSave;
    }
}
