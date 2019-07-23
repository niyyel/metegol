package com.pixel.web.metegol;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class Mapa extends Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_mapa, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    Boolean actualPosition=true;
    JSONObject jso;
    Double longitudOrigen, latitudOrigen;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-9.317677, -76.003064)).title("Gras Sintético Deportivo El Morumbi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        //mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-9.317677, -76.003064)).title("Gras Sintético Deportivo El Morumbi").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon)));
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(-9.317677, -76.003064)).zoom(16).bearing(0).tilt(45).zoom(15).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-9.317677, -76.003064)));
        //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-9.317677, -76.003064), 20));

        //mostrar boton de mi ubicacion y botones de zoom
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener(){
            @Override
            public void onMyLocationChange(Location location){
                if(actualPosition){
                    latitudOrigen =location.getLatitude();
                    longitudOrigen=location.getLongitude();
                    actualPosition=false;

                    LatLng miPosicion = new LatLng(latitudOrigen,longitudOrigen);

                    mGoogleMap.addMarker(new MarkerOptions().position(miPosicion));

                    CameraPosition cameraPosition=new CameraPosition.Builder()
                            .target(new LatLng(latitudOrigen,longitudOrigen))
                            .zoom(15)
                            .build();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    String url="https://maps.googleapis.com/maps/api/directions/json?origin="+latitudOrigen+","+longitudOrigen+"&destination=-9.317677,-76.003064&key=AIzaSyDRsv05_H83daNQi7IE7KEs6cdI7Y0dwD8";

                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                jso = new JSONObject(response);
                                trazarRuta(jso);
                                Log.i("jsonRuta:",""+response);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    queue.add(stringRequest);
                    //String ruta
                }

            }

        });
        // miUbicacionx();

    }

    private void trazarRuta(JSONObject jso) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try{
            jRoutes =jso.getJSONArray("routes");
            for (int i=0; i <jRoutes.length();i++){
                jLegs=((JSONObject)(jRoutes.get(i))).getJSONArray("legs");
                for (int k=0; k <jRoutes.length();k++){
                    jSteps=((JSONObject)jLegs.get(k)).getJSONArray("steps");
                    for (int j=0; j <jRoutes.length();j++) {
                        String polyline =""+((JSONObject)((JSONObject)jSteps.get(j)).get("polyline")).get("points");
                        Log.i("end", ""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        Log.i("list",""+list);
                        mGoogleMap.addPolyline(new PolylineOptions().addAll(list).color(Color.CYAN).width(5));
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
