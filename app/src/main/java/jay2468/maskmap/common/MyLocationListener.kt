package jay2468.maskmap.common

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import jay2468.maskmap.R

class MyLocationListener(private val context: Context,private val locationManager: LocationManager) : LocationListener{
    private val latLng = MutableLiveData<LatLng>()
    val getLatLng get() = latLng

    fun getLastDeviceLocation(){
            try {
                val providers = locationManager.getProviders(true)
                var bestLocation: Location? = null
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    for (provider in providers) {
                        val location = locationManager.getLastKnownLocation(provider) ?: continue
                        if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                            bestLocation = location
                        }
                    }
                    if (bestLocation != null) {
                        latLng.postValue(LatLng(bestLocation.latitude,bestLocation.longitude))
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
                    }
                }else
                    Toast.makeText(context,context.resources.getString(R.string.gps_fail),Toast.LENGTH_SHORT).show()
            }catch (e:SecurityException){
                e.printStackTrace()
            }
    }

    override fun onLocationChanged(location: Location?) {
        if(location!=null){
            latLng.postValue(LatLng(location.latitude,location.longitude))
            locationManager.removeUpdates(this)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}