package br.com.vymajoris.helpradarprototype1.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import br.com.vymajoris.helpradarprototype1.R;


/**
 * Created by Del on 09.11.2014.
 */
public class MapFrag extends Fragment {    MapView mapView;
    GoogleMap map;

    ArrayList<LatLng> pointList = new ArrayList<LatLng>();
    Bundle outState;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        setHasOptionsMenu(true);


            outState = savedInstanceState;



        // Gets the MapView from the XML layout and creates it
        MapsInitializer.initialize(getActivity());

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) )
        {
            case ConnectionResult.SUCCESS:
                Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                mapView = (MapView) v.findViewById(R.id.map);

                mapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if(mapView!=null)
                {
                    map = mapView.getMap();
                    map.getUiSettings().setMyLocationButtonEnabled(false);
                    map.setMyLocationEnabled(true);
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(10, 10))
                            .title("Hello world"));

                    map.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

                        @Override
                        public void onMapClick(LatLng latLng) {


                            // Drawing the currently touched marker on the map
                            drawMarker(latLng);

                            // Adding the currently created marker position to the arraylist
                            pointList.add(latLng);

                            Toast.makeText(getActivity(), "Marker Added", Toast.LENGTH_SHORT).show();

                        }
                    });
                     // Restoring the markers on configuration changes
                    if(savedInstanceState!=null){
                        if(savedInstanceState.containsKey("points")){
                            pointList = savedInstanceState.getParcelableArrayList("points");
                            if(pointList!=null){
                                for(int i=0;i<pointList.size();i++){
                                    drawMarker(pointList.get(i));
                                }
                            }
                        }
                    }

                }
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default: Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
        }

        // Updates the location and zoom of the MapView

        return v;
    }

    public void drawMarker(LatLng latLng){

        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(latLng);

        // Setting a title for this marker
        markerOptions.title("Lat:"+latLng.latitude+","+"Lng:"+latLng.longitude);

        // Adding marker on the Google Map
        map.addMarker(markerOptions);

    }


    // A callback method, which is invoked on configuration is changed
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Adding the pointList arraylist to Bundle
        getFragmentManager().putFragment(outState,"myfragment",this);
        System.out.println("on Save");

            System.out.println("point list");

            outState.putParcelableArrayList("points", pointList);



        // Saving the bundle
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDetach(){
        super.onDetach();

    }


    @Override
    public void onPause() {
        mapView.onResume();
        System.out.println("on Pause");

        super.onPause();

    }

    @Override
    public void onResume() {
        mapView.onResume();
        System.out.println("on Resume");
        super.onResume();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("on destroy");




        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



}
