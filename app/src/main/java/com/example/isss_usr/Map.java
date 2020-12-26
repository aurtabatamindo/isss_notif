package com.example.isss_usr;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;


public class Map extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener {

    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
//    private LocationChangeListeningActivityLocationCallback callback =
//            new LocationChangeListeningActivityLocationCallback(this);
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_map);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull MapboxMap mapboxMap) {
//
//                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
//                    @Override
//                    public void onStyleLoaded(@NonNull Style style) {
//
//                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
//
//
//                    }
//                });
//
//            }
//        });


        Button btn = (Button) findViewById(R.id.btn_back);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Map.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

//    @SuppressLint("MissingPermission")
//    private void initLocationEngine() {
//        locationEngine = LocationEngineProvider.getBestLocationEngine(this);
//
//        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
//                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
//                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();
//
//        locationEngine.requestLocationUpdates(request, (LocationEngineCallback<LocationEngineResult>) callback, getMainLooper());
//        locationEngine.getLastLocation((LocationEngineCallback<LocationEngineResult>) callback);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
       // Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            //Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }
//    private static class LocationChangeListeningActivityLocationCallback
//            implements LocationEngineCallback<LocationEngineResult> {
//
//        private final WeakReference<Map> activityWeakReference;
//
//        LocationChangeListeningActivityLocationCallback(Map activity) {
//            this.activityWeakReference = new WeakReference<>(activity);
//        }
//
//        /**
//         * The LocationEngineCallback interface's method which fires when the device's location has changed.
//         *
//         * @param result the LocationEngineResult object which has the last known location within it.
//         */
//        @Override
//        public void onSuccess(LocationEngineResult result) {
//            Map activity = activityWeakReference.get();
//
//            if (activity != null) {
//                Location location = result.getLastLocation();
//
//                if (location == null) {
//                    return;
//                }
//
//                // Create a Toast which displays the new location's coordinates
////                Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
////                        String.valueOf(result.getLastLocation().getLatitude()),
////                        String.valueOf(result.getLastLocation().getLongitude())),
////                        Toast.LENGTH_SHORT).show();
//
//                // Pass the new location to the Maps SDK's LocationComponent
//                if (activity.mapboxMap != null && result.getLastLocation() != null) {
//                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
//                }
//            }
//        }
//
//        /**
//         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
//         *
//         * @param exception the exception message
//         */
//        @Override
//        public void onFailure(@NonNull Exception exception) {
//            Map activity = activityWeakReference.get();
//            if (activity != null) {
//                Toast.makeText(activity, exception.getLocalizedMessage(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
    // Add the mapView lifecycle to the activity's lifecycle methods
@Override
@SuppressWarnings( {"MissingPermission"})
protected void onStart() {
    super.onStart();
    mapView.onStart();
}

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}