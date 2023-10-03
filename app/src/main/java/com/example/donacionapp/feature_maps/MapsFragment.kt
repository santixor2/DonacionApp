package com.example.donacionapp.feature_maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.donacionapp.R
import com.example.donacionapp.feature_donors.domain.Donors
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.get

class MapsFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val db = get<FirebaseFirestore>()

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { nmap ->
        nmap.isMyLocationEnabled = true
        nmap.uiSettings.isZoomControlsEnabled = true
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return@OnMapReadyCallback
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            val user = FirebaseAuth.getInstance().currentUser
            val dato = hashMapOf(
                "latitude" to location.latitude,
                "longitude" to location.longitude
            )
            if (user != null) {
                db.collection("donantes").document(user.uid).update(dato as Map<String, Any>).addOnSuccessListener {
                    Log.d("prueba1","error")
                }.addOnFailureListener { error->
                    Log.d("prueba2", error.toString())
                }
            }
            val listService = mutableListOf<Donors>()
            db.collection("donantes").addSnapshotListener{result, e ->
                if(e != null){
                    Log.d("mapas","error ", e)
                    return@addSnapshotListener
                }
                if (result != null) {
                    listService.addAll(result.toObjects(Donors::class.java))
                }
                for( service in listService){
                    val ubication = LatLng(service.latitude,service.longitude)
                    val ubicacion2 = LatLng(location.latitude,location.longitude)
                    if (user != null) {
                        nmap.addMarker(MarkerOptions()
                            .position(ubicacion2)
                            .title(service.email)
                            .snippet("Tipo de sangre : ${service.blood}")
                            .icon(BitmapFromVector(requireContext(), R.drawable.blooddonors2)))
                    }
                    nmap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion2, 14f))
                    nmap.addMarker(MarkerOptions()
                        .position(ubication)
                        .title(service.email)
                        .snippet("Tipo de sangre : ${service.blood}")
                        .icon(BitmapFromVector(requireContext(), R.drawable.blooddonors2)))

                }

            }

//            val dato = hashMapOf(
//                "latitude" to location.latitude,
//                "longitude" to location.latitude
//            )
//            db.collection("donantes")
//                .add(dato)
//            val user = FirebaseAuth.getInstance().currentUser
//            if (location != null) {
//                val ubication = LatLng(location.latitude, location.longitude)
//                nmap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubication, 14f))
//                if (user != null) {
//                    nmap.addMarker(MarkerOptions().position(ubication).title("${user.email}"))
//                }
//            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    private fun BitmapFromVector(context: Context, vectorResId:Int): BitmapDescriptor? {
        //drawable generator
        var vectorDrawable: Drawable
        vectorDrawable= ContextCompat.getDrawable(context,vectorResId)!!
        vectorDrawable.setBounds(0,0,vectorDrawable.intrinsicWidth,vectorDrawable.intrinsicHeight)
        //bitmap genarator
        var bitmap: Bitmap
        bitmap= Bitmap.createBitmap(vectorDrawable.intrinsicWidth,vectorDrawable.intrinsicHeight,Bitmap.Config.ARGB_8888)
        //canvas genaret
        var canvas: Canvas
        //pass bitmap in canvas constructor
        canvas= Canvas(bitmap)
        //pass canvas in drawable
        vectorDrawable.draw(canvas)
        //return BitmapDescriptorFactory
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}