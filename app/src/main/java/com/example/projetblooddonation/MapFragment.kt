package com.example.projetblooddonation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
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
            // Coordonnées approximatives du centre du Mans
            val leMansCenter = LatLng(48.0061, 0.2006) // Centre Hospitalier du Mans, bon point de référence

            // Régler la position de la caméra sur Le Mans avec un niveau de zoom
            // Un zoom de 12.0 ou 13.0 est généralement bon pour voir une ville.
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(leMansCenter, 12.5f))

            // Le Mans Locations
            val hospitalLocation = LatLng(
                48.0061,
                0.2006
            ) // Centre Hospitalier du Mans
            googleMap.addMarker(
                MarkerOptions().position(hospitalLocation).title("Centre Hospitalier du Mans")
                    .snippet("194 Av. Rubillard, 72037 Le Mans\nhttps://www.ch-mans.fr/")
            )

            val efsLocation = LatLng(
                47.9947,
                0.1983
            ) // Etablissement Français du Sang (EFS) - Le Mans
            googleMap.addMarker(
                MarkerOptions().position(efsLocation).title("Etablissement Français du Sang (EFS)")
                    .snippet("185 Rue de Degré, 72100 Le Mans\nhttps://www.efs.sante.fr/don-de-sang/ou-donner/72/le-mans")
            )

            val cliniqueTertreRouge = LatLng(
                48.0006,
                0.1802
            ) // Clinique du Tertre Rouge
            googleMap.addMarker(
                MarkerOptions().position(cliniqueTertreRouge).title("Clinique du Tertre Rouge")
                    .snippet("1 All. de Longueterre, 72100 Le Mans")
            )

            val cliniqueSaintCome = LatLng(
                47.9897,
                0.2224
            ) // Clinique Saint-Côme
            googleMap.addMarker(
                MarkerOptions().position(cliniqueSaintCome).title("Clinique Saint-Côme")
                    .snippet("39 Rue de Guetteloup, 72100 Le Mans")
            )

            val polycliniqueMaine = LatLng(
                48.0175,
                0.1650
            ) // Polyclinique du Maine
            googleMap.addMarker(
                MarkerOptions().position(polycliniqueMaine).title("Polyclinique du Maine")
                    .snippet("14 Bd Pasteur, 72000 Le Mans")
            )

            val maisonMedicale = LatLng(
                47.9920,
                0.1540
            ) // Maison Médicale de Garde du Mans
            googleMap.addMarker(
                MarkerOptions().position(maisonMedicale).title("Maison Médicale de Garde du Mans")
                    .snippet("9 Rue de Degré, 72000 Le Mans")
            )

            val poleSanteSud =
                LatLng(48.0030, 0.2310) // Pôle Santé Sud
            googleMap.addMarker(
                MarkerOptions().position(poleSanteSud).title("Pôle Santé Sud")
                    .snippet("38 Rue de la Fuie, 72100 Le Mans")
            )

            val pharmacieHopital = LatLng(
                48.0100,
                0.1300
            ) // Pharmacie de l'Hôpital
            googleMap.addMarker(
                MarkerOptions().position(pharmacieHopital).title("Pharmacie de l'Hôpital")
                    .snippet("Place des Comtes du Maine, 72000 Le Mans")
            )

            val centreVaccination =
                LatLng(48.0010, 0.1700) // Centre de Vaccination Le Mans
            googleMap.addMarker(
                MarkerOptions().position(centreVaccination).title("Centre de Vaccination Le Mans")
                    .snippet("Various locations depending on current campaigns")
            )

            val laboratoireAnalyses =
                LatLng(47.9980, 0.2050) // Laboratoire d'Analyses Médicales
            googleMap.addMarker(
                MarkerOptions().position(laboratoireAnalyses).title("Laboratoire d'Analyses Médicales")
                    .snippet("Many locations across Le Mans, e.g., 20 Rue de la Mariette, 72100 Le Mans")
            )
            val maisonSante = LatLng(48.0065, 0.2100) // Maison de Santé
            googleMap.addMarker(MarkerOptions().position(maisonSante).title("Maison de Santé du Mans"))
            val cabinetMedical =
                LatLng(47.9850, 0.1750) // Cabinet Médical
            googleMap.addMarker(MarkerOptions().position(cabinetMedical).title("Cabinet Médical de Le Mans"))
            val urgencesLeMans =
                LatLng(48.0200, 0.1900) // Urgences Le Mans
            googleMap.addMarker(MarkerOptions().position(urgencesLeMans).title("Urgences Le Mans"))
            val cliniqueVeterinaire =
                LatLng(47.9700, 0.2300) // Clinique Vétérinaire
            googleMap.addMarker(MarkerOptions().position(cliniqueVeterinaire).title("Clinique Vétérinaire du Mans"))
            val ehpadLeMans =
                LatLng(48.0090, 0.1450) // EHPAD
            googleMap.addMarker(MarkerOptions().position(ehpadLeMans).title("EHPAD Le Mans"))
            val cabinetKinesitherapie =
                LatLng(47.9950, 0.2150) // Kinésithérapeute
            googleMap.addMarker(MarkerOptions().position(cabinetKinesitherapie).title("Cabinet Kinésithérapie"))
            val orthophoniste =
                LatLng(48.0120, 0.1800) // Orthophoniste
            googleMap.addMarker(MarkerOptions().position(orthophoniste).title("Orthophoniste Le Mans"))
            val dentiste =
                LatLng(47.9800, 0.1600) // Dentiste
            googleMap.addMarker(MarkerOptions().position(dentiste).title("Dentiste Le Mans"))
            val ophtalmologue =
                LatLng(47.9750, 0.2400) // Ophtalmologue
            googleMap.addMarker(MarkerOptions().position(ophtalmologue).title("Ophtalmologue Le Mans"))
            googleMap.setOnMapClickListener { }
        }
        return view
    }
}