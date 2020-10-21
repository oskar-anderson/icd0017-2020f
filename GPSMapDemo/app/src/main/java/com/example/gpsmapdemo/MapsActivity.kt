package com.example.gpsmapdemo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap

    private lateinit var locationManager: LocationManager;
    private var provider : String? = ""
    private var location: Location? = null
    private val polylineOptions = PolylineOptions().width(10f)
        .color(Color.RED)
    private  var polyLine : Polyline? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager;
        var criteria = Criteria();

        Log.d("Provider", provider!!)

        provider = locationManager.getBestProvider(criteria, true)
        if (provider != null){
            if (
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            location = locationManager.getLastKnownLocation(provider!!);
        } else {

        }


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("onMapReady", "---")
        mMap = googleMap
        var markerLatLng = LatLng(59.4713933, 24.458064)
        if (location != null) {
            Log.d("onMapReady", "existing")
            markerLatLng = LatLng(location!!.latitude, location!!.longitude)
        } else {
            mMap.addMarker(MarkerOptions().position(markerLatLng).title("Marker"))
            mMap.moveCamera(CameraUpdateFactory.zoomBy(15f))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng))
        }
    }

    override fun onResume() {
        super.onResume()
        if (locationManager != null && provider != null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            locationManager.requestLocationUpdates(provider!!, 400, 1f, this)
        }

    }

    override fun onPause() {
        super.onPause()
        if (locationManager != null && provider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            locationManager.removeUpdates(this)
        }
    }

    override fun onLocationChanged(location: Location) {
        if (mMap == null) return
        var markerLatLng = LatLng(location!!.latitude, location!!.longitude)
        mMap.addMarker(MarkerOptions().position(markerLatLng).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng))
    }


}