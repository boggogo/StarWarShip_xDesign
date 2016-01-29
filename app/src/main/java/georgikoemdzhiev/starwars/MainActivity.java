package georgikoemdzhiev.starwars;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    String starwarsurl = "http://internday.xdesign365.com/starships";
    public static StarWarShips mStarWarShips;
    private RecyclerView mRecyclerView;
    private ShipAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);


        mStarWarShips = new StarWarShips();

        if(!isNetworkAvailable()) {
            loadLocalData();
            Log.d(TAG, "NO WIFI");
            adapter = new ShipAdapter(mStarWarShips.getStarwarShips(), this);
            mRecyclerView.setAdapter(adapter);

        }else{
            loadDataFromInternet();
            Log.d(TAG, "WIFI AVAILABLE");
        }

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);




    }

    private void loadDataFromInternet() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(starwarsurl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse");
                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Response successful");
                        parseShipsDetails(jsonData);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ShipAdapter(mStarWarShips.getStarwarShips(), MainActivity.this);
                                mRecyclerView.setAdapter(adapter);
                            }
                        });
                    } else {
                        Log.d(TAG, "Response NOT successful");
                    }
                } catch (IOException | JSONException e) {
                    Log.e(TAG, "Exception caught:", e);
                }
            }
        });
    }

    private void parseShipsDetails(String jsonData) throws JSONException {
        JSONArray shipArray = new JSONArray(jsonData);
        String shipName = "";
        for (int i = 0; i < shipArray.length(); i++) {
            JSONObject newshipJson = shipArray.getJSONObject(i);;

            StarwarShip newShip = new StarwarShip();
            newShip.setName(newshipJson.getString("name"));
            newShip.setManifacturer(newshipJson.getString("manufacturer"));
            newShip.setCost_in_credits(newshipJson.getString("cost_in_credits"));
            newShip.setLength(newshipJson.getString("length"));
            newShip.setMax_atmosphering_speed(newshipJson.getString("max_atmosphering_speed"));
            newShip.setCargo_capacity_kg(newshipJson.getString("cargo_capacity_kg"));
            newShip.setHyperdrive_rating(newshipJson.getString("hyperdrive_rating"));

            JSONObject docingCoordinates = newshipJson.getJSONObject("docking_station");
            newShip.setDocking_station_latityde(docingCoordinates.getString("latitude"));
            newShip.setDocking_station_longitude(docingCoordinates.getString("longitude"));

            // for the log
            shipName = newshipJson.getString("name");

            mStarWarShips.add(newShip);
        }

        Log.d(TAG,"SHIPNAME_JSON: "+shipName);
        Log.d(TAG,"ships size: "+mStarWarShips.size());
    }

    private void loadLocalData() {
        InputStream inputStream = getResources().openRawResource(R.raw.starships);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> scoreList = csvFile.read();
        for(String[] scoreData:scoreList ) {
//            Log.d(TAG,scoreData[0] + "" + scoreData[1] + "" + scoreData[2]);
            StarwarShip newStarwarShip = new StarwarShip();
            newStarwarShip.setName(scoreData[0]);
            newStarwarShip.setManifacturer(scoreData[1]);
            newStarwarShip.setCost_in_credits(scoreData[2]);
            newStarwarShip.setLength(scoreData[3]);
            newStarwarShip.setMax_atmosphering_speed(scoreData[4]);
            newStarwarShip.setCargo_capacity_kg(scoreData[5]);
            newStarwarShip.setHyperdrive_rating(scoreData[6]);
            newStarwarShip.setDocking_station_latityde(scoreData[7]);
            newStarwarShip.setDocking_station_longitude(scoreData[8]);

            mStarWarShips.add(newStarwarShip);
        }

        Log.d(TAG, mStarWarShips.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //TODO Sorting... no time...
//            Collections.sort(mStarWarShips.getStarwarShips(),new shipComparator());

            adapter.notifyDataSetChanged();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        //contition to check if there is a network and if the device is connected
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
}
