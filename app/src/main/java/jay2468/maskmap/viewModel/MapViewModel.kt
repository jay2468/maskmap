package jay2468.maskmap.viewModel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import jay2468.maskmap.data.bean.Result
import jay2468.maskmap.data.db.entity.MaskEntity
import jay2468.maskmap.data.repository.MapRepository

class MapViewModel(@param:NonNull private val mApplication: Application, private val mapRepository: MapRepository) :
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
        mapRepository.getAdressByCity(county, town, _address)
    }

    fun getAllAdress() {
        mapRepository.getAllAdress(_address)
    }

    fun setSpecificOne(entity: MaskEntity?){
        _specificOne.value = entity
    }
}