package com.fconde.cashcolombinoapp.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.fconde.cashcolombinoapp.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String empresa = "Cash Colombino";
    private double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(getIntent().getExtras() != null){
            empresa = getIntent().getExtras().getString("empresa");
        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        switch (empresa){
            case "Cash Colombino":
                latitud = 37.27005;
                longitud = -6.914534;
                break;
            case "Cash Barea":
                latitud = 37.3844671;
                longitud = -5.9572327;
                break;
            case "Manuel Barea":
                latitud = 37.3844671;
                longitud = -5.9572327;
                break;
            case "Cash Extremeño":
                latitud = 38.672472;
                longitud = -6.389109;
                break;
            default:
                empresa = "Cash Colombino";
                latitud = 37.27005;
                longitud = -6.914534;
                break;
        }

        Toast.makeText(this, String.valueOf(longitud), Toast.LENGTH_SHORT).show();

        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng position = new LatLng(latitud,longitud);
        mMap.addMarker(new MarkerOptions().position(position).title(empresa));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
