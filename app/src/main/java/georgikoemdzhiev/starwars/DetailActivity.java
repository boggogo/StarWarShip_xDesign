package georgikoemdzhiev.starwars;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Subscription;
import rx.functions.Action1;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {
    StarWarShips starWarShipsl;
    private GoogleMap mMap;
    private static final String TAG = DetailActivity.class.getSimpleName();
    private int selectedShip;
    Subscription onlyFirstTimeSubscription;
    ReactiveLocationProvider locationProvider;
    private LocationManager locationManager;
    //api key = AIzaSyCqiRIWKHxd6rD-5Rv4a_3bUBZoMiiSTYE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);
        locationProvider = new ReactiveLocationProvider(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        starWarShipsl = MainActivity.mStarWarShips;
        Log.d(TAG, "selected ship: " + selectedShip);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getUserCurrentLocation();
    }

    private void getUserCurrentLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if( !isLocationServicesEnabled()) {
            alertForNoLocationEnabled();
        }else {
            LocationRequest oneTimeOnStartRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setNumUpdates(1)
                    .setInterval(0);

            onlyFirstTimeSubscription = locationProvider.getUpdatedLocation(oneTimeOnStartRequest)

                    .subscribe(new Action1<Location>() {
                        @Override
                        public void call(Location location) {
                            Log.d(TAG, "Getting location updates...");
                            selectedShip = getIntent().getIntExtra(Constants.selectedShip, 0);
                            StarwarShip ship = starWarShipsl.getStarwarShips().get(selectedShip);

                            double shipLat = Double.parseDouble(ship.getDocking_station_latityde());
                            double shipLon = Double.parseDouble(ship.getDocking_station_longitude());

                            Log.d(TAG, "LAT VALUE: " + ship.getDocking_station_latityde());
                            Log.d(TAG, "LONG VALUE: " + ship.getDocking_station_longitude());


                            // Add a marker in Sydney, Australia, and move the camera.
                            LatLng shiplatLong = new LatLng(shipLat, shipLon);
                            LatLng userCurrentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(shiplatLong).title(ship.getName()));
                            mMap.addMarker(new MarkerOptions().position(userCurrentLoc).title("YOU ARE HERE"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(shiplatLong));
                            float[] results = new float[1];
                            Toast.makeText(DetailActivity.this, "DISTANCE: " + getDistance(userCurrentLoc, shiplatLong), Toast.LENGTH_LONG).show();
//                        Log.d(TAG,"DISTANCE: "+results[0] + "");
                            onlyFirstTimeSubscription.unsubscribe();
                        }

                        ;

                    });
        }


    }

    public static String getDistance(LatLng my_latlong, LatLng frnd_latlong) {
        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        float distance = l1.distanceTo(l2);
        String dist = distance + " M";

        if (distance > 1000.0f) {
            distance = distance / 1000.0f;
            dist = distance + " KM";
        }
        return dist;
    }
    public boolean isLocationServicesEnabled() {
        return (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public void alertForNoLocationEnabled() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle(R.string.network_not_found_title);  // network not found
        builder.setMessage(R.string.network_not_found_message); // Want to enable?
        builder.setPositiveButton("Yes", null);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
