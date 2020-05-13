package jay2468.maskmap.viewModel

import android.app.Application
import android.location.Address
import android.location.Location
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jay2468.maskmap.R
import jay2468.maskmap.data.bean.Result
import jay2468.maskmap.data.db.entity.MaskEntity
import jay2468.maskmap.data.repository.MapRepository
import kotlinx.coroutines.*

class MapViewModel(
    @param:NonNull private val mApplication: Application,
    private val mapRepository: MapRepository
) :
    AndroidViewModel(mApplication) {
    private val _address = MutableLiveData<List<MaskEntity>>()
    val address: LiveData<List<MaskEntity>>
        get() = _address

    private val _nearByPharmacy = MutableLiveData<Result>()
    val nearByPharmacy: LiveData<Result>
        get() = _nearByPharmacy

    private val _specificOne = MutableLiveData<MaskEntity?>()
    val specificOne: LiveData<MaskEntity?>
        get() = _specificOne

    suspend fun findNearbyPharmacy(origins: String, destinations: String, key: String) {
        mapRepository.distanceMatrix(origins, destinations, key, _nearByPharmacy)
    }

    fun getAddressByCity(county: String, town: String) {
        viewModelScope.launch {
            _address.postValue(mapRepository.getAdressByCity(county, town))
        }
    }

    fun getPharmacyByName(name: String) {
        viewModelScope.launch {
            val result = mapRepository.getPharmacyByName(name)
            if (result == null)
                Toast.makeText(mApplication, mApplication.resources.getString(R.string.no_data), Toast.LENGTH_SHORT).show()
            else
                _specificOne.postValue(result)
        }
    }

    fun getNearByAdress(currentAddress: List<Address>) {
        viewModelScope.launch {
            val addressList = mapRepository.getAllAdress()
            val result = FloatArray(3)
            val nearbyPharmacyList: MutableList<MaskEntity> = mutableListOf()
            var nearbyPharmacyCount = 0
            for (entity in addressList) {
                Location.distanceBetween(
                    currentAddress[0].latitude,
                    currentAddress[0].longitude, entity.Latitude, entity.Longitude, result
                )
                if (result[0] < 600) {
                    nearbyPharmacyCount++
                    nearbyPharmacyList.add(entity)
                }
            }
            _address.postValue(nearbyPharmacyList)
            Toast.makeText(mApplication, mApplication.resources.getString(R.string.nearby_pharmacy, nearbyPharmacyCount), Toast.LENGTH_SHORT).show()
        }
    }

    fun setSpecificOne(entity: MaskEntity?) {
        _specificOne.value = entity
    }
}