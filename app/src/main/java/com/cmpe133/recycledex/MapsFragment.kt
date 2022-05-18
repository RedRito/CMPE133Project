package com.cmpe133.recycledex

import android.Manifest
import android.app.ActionBar
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_maps.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MapsFragment : Fragment() {


    var isSearched = false
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var favArrayList: ArrayList<Centers>
    private lateinit var jankList: ArrayList<Centers>

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

        database = FirebaseDatabase.getInstance().getReference("Centers")
        //grabs all centers in database and sets the googlemap
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (CenterSnapshot in snapshot.children) {
                        val center = CenterSnapshot.getValue(Centers::class.java)
                        val lat = center!!.lat
                        val lon = center!!.lon
                        val name = center!!.name
                        val mapLocation = LatLng(lat,lon)
                        googleMap.addMarker(MarkerOptions().position(mapLocation).title(name))

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        //start the camera on San Jose, with a 10F zoom
        val zoomlevel = 10f         //This is how close the map starts off
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.3382, -121.8863), zoomlevel))
        setMapLongClick(googleMap)
    }


    //Function to get user data based on their login information
    //Gets their favorite centers and adds it to the favorite array list
    fun getUserData(uid: String)
    {
        database = FirebaseDatabase.getInstance().getReference("Users")

        database.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for(centers in snapshot.child("favCenters").children)
                    {
                        val center = centers.getValue(Centers::class.java)
                        favArrayList.add(center!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = layoutInflater.inflate(R.layout.fragment_maps, container, false)
        val mapinfo: CardView = rootView.findViewById(R.id.mapInfoCard)
        val map: FragmentContainerView = rootView.findViewById(R.id.map)
        favArrayList = arrayListOf<Centers>()
        jankList = arrayListOf<Centers>()
        firebaseAuth = FirebaseAuth.getInstance()

        var uid : String? = null
        if(firebaseAuth.currentUser != null)
        {
            uid = firebaseAuth.currentUser?.uid
            getUserData(uid!!)
        }




        val nameText: TextView = rootView.findViewById(R.id.rcName)
        val locationText: TextView = rootView.findViewById(R.id.rcLocationInfo)
        val hoursText: TextView = rootView.findViewById(R.id.rcHoursInfo)
        val phoneText: TextView = rootView.findViewById(R.id.rcPhoneInfo)
        val mats: TextView = rootView.findViewById(R.id.rcMaterialsInfo)
        val website: TextView = rootView.findViewById(R.id.rcWebInfo)
        val fav: ImageButton = rootView.findViewById(R.id.favoriteButton)


        val args = this.arguments
        if(args?.get("rcname") != null)
        {
            //get the args
            val rcname = args?.get("rcname").toString()
            val location = args?.get("location").toString()
            val hours = args?.get("hours").toString()
            val phone = args?.get("phone").toString()
            val materials = args?.get("materials").toString()
            val link = args?.get("link").toString()
            val lat = args?.getDouble("lat")
            val long = args?.getDouble("lon")


            //Set the text in the card above
            nameText.text = rcname
            locationText.text = location
            hoursText.text = hours
            phoneText.text = phone
            mats.text = materials
            website.text = link
            isSearched = true

            fun checkIfExists(text: String) : Boolean{
                for(centers in favArrayList)
                {
                    if(centers.name.contains(text))
                    {
                        return true
                    }
                }
                return false
            }
            fun removeCenter(text: String){
                jankList.clear()
                for(centers in favArrayList)
                {
                    if(centers.name.contains(text))
                    {
                        jankList.add(centers)
                    }
                }
                favArrayList.removeAll(jankList)
            }

            //FAVORITES BUTTON
            fav.setOnClickListener{
                val center = Centers(rcname, location, hours, lat!!, long!!, materials, phone, link)
                //CENTER
                if(uid != null)
                {
                    if(!checkIfExists(rcname)) //CHECK IF ALREADY IN USER FAVORITES
                    {

                        favArrayList.add(center)
                        val user = mapOf<String, ArrayList<Centers>>(
                            "favCenters" to favArrayList
                        )
                        database.child(uid!!).updateChildren(user).addOnSuccessListener {
                            Toast.makeText(context, "Favorited!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else //DELETES FROM USER FAVORITES
                    {

                        removeCenter(rcname)
                        val user = mapOf<String, ArrayList<Centers>>(
                            "favCenters" to favArrayList
                        )
                        database.child(uid!!).updateChildren(user).addOnSuccessListener {
                            Toast.makeText(context, "Unfavorited", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, "Not Logged in", Toast.LENGTH_SHORT).show()
                }
            }

            //get the mapp ready
            val callback2 = OnMapReadyCallback {
                googleMap ->
                val zoomlevel = 10f
                val searched = LatLng(lat!!, long!!)
                googleMap.addMarker(MarkerOptions().position(searched).title(rcname))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searched, zoomlevel))
                setMapLongClick(googleMap)
            }
            //create the map
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback2)


        }
        else
        {
            //hide the card
            val param = map.layoutParams
            param.height = ViewGroup.LayoutParams.MATCH_PARENT
            param.width = ViewGroup.LayoutParams.MATCH_PARENT
            map.layoutParams = param
            mapinfo.visibility = View.GONE
        }



        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isSearched)
        {
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }
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