package com.example.projetblooddonation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        supportMapFragment!!.getMapAsync { googleMap ->
            val location1 = LatLng(48.0061, 0.1996)
            googleMap.addMarker(
                MarkerOptions().position(location1).title("EFS Le Mans")
                    .snippet("28 Rue de l'Estérel, 72100 Le Mans\nhttps://dondesang.efs.sante.fr\n02 43 72 40 80")
            )

            val location2 = LatLng(48.0070, 0.2020)
            googleMap.addMarker(
                MarkerOptions().position(location2).title("Hôpital du Mans")
                    .snippet("194 Avenue Rubillard, 72100 Le Mans\nhttps://www.ch-lemans.fr\n02 43 43 43 43")
            )

            val location3 = LatLng(48.0000, 0.1800)
            googleMap.addMarker(
                MarkerOptions().position(location3).title("Clinique du Pré")
                    .snippet("rue de Guetteloup, 72000 Le Mans\nhttps://www.clinique-du-pre.com\n02 43 78 78 78")
            )

            val location4 = LatLng(48.0123, 0.2101)
            googleMap.addMarker(
                MarkerOptions().position(location4).title("Centre Municipal de Santé")
                    .snippet("Place d’Alger, 72100 Le Mans\nhttps://www.lemans.fr\n02 43 47 38 38")
            )

            val location5 = LatLng(48.0032, 0.1919)
            googleMap.addMarker(
                MarkerOptions().position(location5).title("Maison de santé - Le Mans Sud")
                    .snippet("123 Rue de la Solidarité, 72100 Le Mans\nhttps://www.msp-sud.fr\n02 43 88 88 88")
            )

            val location6 = LatLng(48.0150, 0.1980)
            googleMap.addMarker(
                MarkerOptions().position(location6).title("Don du Sang - Centre Mobile")
                    .snippet("Place des Jacobins, 72000 Le Mans\nhttps://dondesang.efs.sante.fr\n0800 109 900")
            )

            googleMap.setOnMapClickListener { }
        }
        return view
    }
}
