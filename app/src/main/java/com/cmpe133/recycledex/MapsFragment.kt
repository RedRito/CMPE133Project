package com.cmpe133.recycledex

import android.Manifest
import android.app.ActionBar
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import java.util.*

class MapsFragment : Fragment() {





    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sanjose = LatLng(37.3382, -121.8863)
        val sjmetalrecycling = LatLng(37.36115069325536, -121.89607100588645)
        val storyroad = LatLng(37.33479662190691,-121.85140220158094)
        val ranchtownRC  = LatLng(37.31564756725881,-121.90712595986436)
        val zoomlevel = 10f         //This is how close the map starts off
        googleMap.addMarker(MarkerOptions().position(sanjose).title("Marker in San Jose"))
        googleMap.addMarker(MarkerOptions().position(sjmetalrecycling).title("San Jose Metal Recycling"))
        //googleMap.addMarker(MarkerOptions().position(sjmetalrecycling).snippet("recyclign center"))
        googleMap.addMarker(MarkerOptions().position(storyroad).title("Story Road Recycling LLC"))
        googleMap.addMarker(MarkerOptions().position(ranchtownRC).title("Ranch Town Recycling Center Inc"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanjose, zoomlevel))
        setMapLongClick(googleMap)







    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View = layoutInflater.inflate(R.layout.fragment_maps, container, false)
        val mapinfo: CardView = rootView.findViewById(R.id.mapInfoCard)
        val map: FragmentContainerView = rootView.findViewById(R.id.map)
        val param = map.layoutParams
        param.height = ViewGroup.LayoutParams.MATCH_PARENT
        param.width = ViewGroup.LayoutParams.MATCH_PARENT
        map.layoutParams = param
        mapinfo.visibility = View.GONE

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)



    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            // A Snippet is Additional text that's displayed below the title.
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Dropped Pin")
                    .snippet(snippet)

            )
        }

    }








}