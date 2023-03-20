package com.example.meditation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    final static String userKey = "USER_VARIABLE";

    private AdapterBlock pAdapter;
    private List<MaskBlock> listQuote = new ArrayList<>();

    private AdapterCondition dataAdapter;
    private List<MaskCondition> listFeeling = new ArrayList<>();

    ImageView imgProfile;
    TextView txtHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        txtHello = findViewById(R.id.hello);
        txtHello.setText(txtHello.getText().toString() + MainActivity.Name + "!");

        ListView ivProducts = findViewById(R.id.listQuotes);
        pAdapter = new AdapterBlock(MainMenu.this, listQuote);
        ivProducts.setAdapter(pAdapter);
        new GetQuete().execute();

        RecyclerView rvFeeling = findViewById(R.id.visualizer);
        rvFeeling.setHasFixedSize(true);
        rvFeeling.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        dataAdapter = new AdapterCondition(MainMenu.this, listFeeling);
        rvFeeling.setAdapter(dataAdapter);
        new GetFeel().execute();

        imgProfile = findViewById(R.id.Profile);
        new AdapterBlock.DownloadImageTask((ImageView) imgProfile).execute(MainActivity.image);
    }

    public void onProfile(View view) {
        startActivity(new Intent(this, Profile.class));
    }

    private class GetFeel extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String l = "";
                while ((l = reader.readLine()) != null)
                {
                    result.append(l);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listFeeling.clear();
                dataAdapter.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    MaskCondition tempProduct = new MaskCondition(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getInt("position")
                    );
                    listFeeling.add(tempProduct);
                    dataAdapter.notifyDataSetChanged();
                }
                listFeeling.sort(Comparator.comparing(MaskCondition::getPosition));
                dataAdapter.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(MainMenu.this, "При выводе данных произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetQuete extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String l = "";

                while ((l = reader.readLine()) != null)
                {
                    result.append(l);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listFeeling.clear();
                dataAdapter.notifyDataSetChanged();
                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    MaskBlock tempProduct = new MaskBlock(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getString("description")
                    );
                    listQuote.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            }
            catch (Exception exception)
            {
                Toast.makeText(MainMenu.this, "При выводе данных произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void NextListen(View view) {
        startActivity(new Intent(this, Listen.class));
    }

    public void NextMenu(View view) {
        startActivity(new Intent(this, Menu.class));
    }
}