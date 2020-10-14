package com.vsga.pegawaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private android.widget.ListView ListView;
    private String JSON_STRING="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView=findViewById(R.id.ListView);

        getJSON();
    }
    private void getJSON(){
        GetJSON getJSON=new GetJSON();
        GetJSON.execute();
    }
    public class getJSON extends AsyncTask<Void,Void,String>{
        ProgressDialog Loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Loading=ProgressDialog.show(MainActivity.this, "Mengambil Data",
                    "Mohon Tunggu...", false, false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler rh=new RequestHandler();
            String s=rh.sendGetRequest(konfigurasi.URL_GET_ALL);

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Loading.dismiss();
            JSON_STRING=s;
            showEmployee();


        }
    }

    private void showEmployee(){
        JSONObject jsonObject=null;
        ArrayList<HashMap<String,String>> list=new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
                        int i;
            for (i = 0; i < result.length(); i++) ;

            JSONObject jo = result.getJSONObject(i);
            String id = jo.getString(konfigurasi.TAG_ID);
            String name = jo.getString(konfigurasi.TAG_NAME);
            HashMap<String, String> employee = new HashMap<>();
            employee.put(konfigurasi.TAG_ID,id);
            employee.put(konfigurasi.TAG_NAME, name);
            list.add(employee);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleAdapter adapter=new SimpleAdapter(this,list,
                R.layout.list_item,new String [] {konfigurasi.TAG_ID,konfigurasi.TAG_NAME} ,
                new int[]{R.id.id, R.id.name});
        ListView.setAdapter(adapter);

    }

    private static class GetJSON {

        public static void execute() {
        }
    }
}