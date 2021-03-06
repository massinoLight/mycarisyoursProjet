package com.example.mycarisyours.map


import android.Manifest
import android.content.Context
import com.example.mycarisyours.R
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.jetbrains.anko.toast


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    companion object {

        const val saintMauricelat: Double = 48.815859
        const val saintMauricelng: Double =  2.459580
        const val nanterrelat: Double = 48.891661
        const val nanterrelng: Double = 2.194803
        const val universitelat=48.946109
        const val universitelng=2.364236

        const val paris13eme_lat=48.820591
        const val paris13eme_lng=2.361080

        const val didrot_lat=48.827731
        const val didrot_lng=2.382438

        const val upmc_lat=48.847153
        const val upmc_lng= 2.357542

        const val paleseau_lat=48.725394
        const val paleseau_lng= 2.261020


        const val sacréCoeur_lat=48.888478
        const val sacréCoeur_lng= 2.333990

        const val homeSweetHome_lat=36.759471
        const val homeSweetHome_lng= 5.084948

        const val hassi_Messaoud_lat= 31.699021
        const val hassi_Messaoud_lng= 6.065078


        const val noisy_Legrand_lat= 48.846207
        const val noisy_Legrand_lng= 2.554102

        const val tour_effel_lat= 48.860461
        const val tour_effel_lng= 2.297380

        const val bobiby_lat= 48.909800
        const val bobiby_lng= 2.452776

        const val bondy_lat= 48.903386
        const val bony_lng= 2.484783

    }

    //deux variables pour controler l'accée a internet et au gps
    var gps_enabled:Boolean?=false
    var network_enabled:Boolean?=false
    var lm: LocationManager? = null

    private val PERMISSION_CODE = 1000
    var mapFragment : SupportMapFragment?=null

    lateinit var apiClient: GoogleApiClient
    var mLocation: Location? =null
    var lat: Double = 0.0
    var lng: Double = 0.0



    lateinit var mLocationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?


        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        apiClient=GoogleApiClient.Builder(applicationContext).addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED ||
                checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
                /**
                 * on verif a chaque fois si les permissions sont accordées
                 * */
                Log.i("XXXX","les permissions n'etaient pas accordées elles doivent l'etre")


            }
            else{
                /***
                 * si les permissions sont accordées
                 * ont verifie si le gps et l'accé
                 * a internet sont dispo
                 * */


                Log.i("XXXX","on check la localisation et internet")
                gps_enabled = lm?.isProviderEnabled(LocationManager.GPS_PROVIDER)
                network_enabled = lm?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            }
        }
        else{

            toast("ancien telephone je ne  sais pas quoi faire ")

        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Ajouter quelque marker a notre MAP
        /**
         * I love  Sydney <3
         * */
        var options: MarkerOptions= MarkerOptions()
        var options1: MarkerOptions= MarkerOptions()
        var options2: MarkerOptions= MarkerOptions()
        var options3: MarkerOptions= MarkerOptions()
        var options4: MarkerOptions= MarkerOptions()
        var options5: MarkerOptions= MarkerOptions()
        var options6: MarkerOptions= MarkerOptions()
        var options7: MarkerOptions= MarkerOptions()
        var options8: MarkerOptions= MarkerOptions()
        var options9: MarkerOptions= MarkerOptions()
        var options10: MarkerOptions= MarkerOptions()
        var options11: MarkerOptions= MarkerOptions()
        var options12: MarkerOptions= MarkerOptions()

        var homesweethome: MarkerOptions= MarkerOptions()
        val sydney = LatLng(lat,lng)
        val laFac = LatLng(saintMauricelat, saintMauricelng)
        val nanterre = LatLng(nanterrelat, nanterrelng)
        val saintMaurice = LatLng(saintMauricelat,saintMauricelng)
        val paleseau = LatLng(paleseau_lat, paleseau_lng)
        val sacreCoeur = LatLng(sacréCoeur_lat, sacréCoeur_lng)
        val homeSweetHomepos = LatLng(homeSweetHome_lat, homeSweetHome_lng)

        val hassi = LatLng(hassi_Messaoud_lat, hassi_Messaoud_lng)
        val paris13eme = LatLng(paris13eme_lat, paris13eme_lng)
        val didrot = LatLng(didrot_lat, didrot_lng)
        val upmc = LatLng(upmc_lat, upmc_lng)

        val noisy = LatLng(noisy_Legrand_lat, noisy_Legrand_lng)
        val bobiny = LatLng(bobiby_lat, bobiby_lng)
        val bondy = LatLng(bondy_lat, bony_lng)
        val tour = LatLng(tour_effel_lat, tour_effel_lng)

        options.title("vous etes ici")
        options.snippet("snippet")
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.voitureicon))
        options.position(sydney)


        options1.title("Saint Denis Université")
        options1.snippet("Citroen c5")
        options1.icon(BitmapDescriptorFactory.fromResource(R.drawable.veloicon))
        options1.position(laFac)

        options2.title("Nanterre")
        options2.snippet("Citroen c5")
        options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.voitureicon))
        options2.position(nanterre)

        options3.title("Saint Maurice")
        options3.snippet("Toyota Yaris")
        options3.icon(BitmapDescriptorFactory.fromResource(R.drawable.voitureicon))
        options3.position(saintMaurice)

        options4.title("paris 13eme")
        options4.snippet("Renault twingo")
        options4.icon(BitmapDescriptorFactory.fromResource(R.drawable.trotinetteicon))
        options4.position(paris13eme)

        options5.title("Paris Didrot")
        options5.snippet("Renault capture")
        options5.icon(BitmapDescriptorFactory.fromResource(R.drawable.trotinetteicon))
        options5.position(didrot)

        options6.title("Jussieu")
        options6.snippet("Ford Fiesta")
        options6.icon(BitmapDescriptorFactory.fromResource(R.drawable.voitureicon))
        options6.position(upmc)

        options7.title("paleseau")
        options7.snippet("DACIA Logan")
        options7.icon(BitmapDescriptorFactory.fromResource(R.drawable.utilitaireicon))
        options7.position(paleseau)



        options8.title("Sacré Coeur")
        options8.snippet("Porche Cayenne")
        options8.icon(BitmapDescriptorFactory.fromResource(R.drawable.motoicon))
        options8.position(sacreCoeur)


        options9.title("La Toyota de Kiki ")
        options9.snippet("(Anoucha)")
        options9.icon(BitmapDescriptorFactory.fromResource(R.drawable.voitureicon))
        options9.position(hassi)



        homesweethome.title("Houma cherchour")
        homesweethome.snippet("Toyota de Maman ")
        homesweethome.icon(BitmapDescriptorFactory.fromResource(R.drawable.voitureicon))
        homesweethome.position(homeSweetHomepos)

        options9.title("noisy le grand")
        options9.snippet("utilitaire")
        options9.icon(BitmapDescriptorFactory.fromResource(R.drawable.utilitaireicon))
        options9.position(noisy)


        options10.title("bobiny")
        options10.snippet("utilitaire")
        options10.icon(BitmapDescriptorFactory.fromResource(R.drawable.voitureicon))
        options10.position(bobiny)

        options11.title("bondy")
        options11.snippet("trottinette")
        options11.icon(BitmapDescriptorFactory.fromResource(R.drawable.utilitaireicon))
        options11.position(bondy)


        options12.title("tour eiffel")
        options12.snippet("trottinette")
        options12.icon(BitmapDescriptorFactory.fromResource(R.drawable.trotinetteicon))
        options12.position(tour)



        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
        mMap.setMyLocationEnabled(true)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,20F))
        mMap.addMarker(options)
        mMap.addMarker(options1)
        mMap.addMarker(options2)
        mMap.addMarker(options3)
        mMap.addMarker(options4)
        mMap.addMarker(options5)
        mMap.addMarker(options6)
        mMap.addMarker(options7)
        mMap.addMarker(options8)
        mMap.addMarker(options9)
        mMap.addMarker(options10)
        mMap.addMarker(options11)
        mMap.addMarker(options12)
        mMap.addMarker(homesweethome)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Connection to Google API Failed")    }

    override fun onConnectionSuspended(p0: Int) {
     toast("Service  indisponible pour le moment")
    }

    override fun onConnected(p0: Bundle?) {
       Log.i("XXXXXXXXXXXXXX","On va check les permissions")
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED ||
                checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
                Log.i("XXXX","les permissions n'etaient pas accordées elles doivent l'etre")


            }
            else{

                if ((gps_enabled!!)&&(network_enabled!!))
                {
                    Log.i("XXXXxxxx","on va recup le localisation")
                    mLocation=LocationServices.FusedLocationApi.getLastLocation(apiClient)
                    if (mLocation!=null){
                        lat= mLocation!!.latitude
                        lng= mLocation!!.longitude
                        Log.i("XXXX","on est dans le if")
                        mapFragment?.getMapAsync(this)


                    }
                    else{
                        lat=universitelat
                        lng=universitelng

                        Log.i("XXXX","position affécté manuélement")
                        toast("la position a etait affecté manuélement")

                    }

                }
                else{
                    Log.i("XXXX","la localisation est désactivé")
                    toast("localisation indisponible")

                }


            }
        }

        else{

            Log.i("XXXXxxxx","ancien telephone je ne sais pas quoi faire")

        }

    }



    override fun onStart() {
        super.onStart()
        apiClient.connect()
    }

    override fun onStop() {
        super.onStop()
        apiClient.disconnect()
    }


    override fun onResume() {
        super.onResume()
        apiClient.connect()
    }
    override fun onPause() {
        super.onPause()
        apiClient.disconnect()
    }


    override fun onDestroy() {
        super.onDestroy()
        apiClient.disconnect()
    }


    override fun onRestart() {
        super.onRestart()
        apiClient.connect()
    }

}

