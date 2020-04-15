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
    private val address = MutableLiveData<List<MaskEntity>>()
    val getAddress: LiveData<List<MaskEntity>>
        get() = address

    private val nearByPharmacy = MutableLiveData<Result>()
    val getNearByPharmacy: LiveData<Result>
        get() = nearByPharmacy

    private val specificOne = MutableLiveData<MaskEntity?>()
    val getSpecificOne: LiveData<MaskEntity?>
        get() = specificOne

    suspend fun findNearbyPharmacy(origins: String, destinations: String, key: String) {
        mapRepository.distanceMatrix(origins, destinations, key, nearByPharmacy)
    }

    fun getAddressByCity(county: String, town: String) {
        mapRepository.getAdressByCity(county, town, address)
    }

    fun getAllAdress() {
        mapRepository.getAllAdress(address)
    }

    fun setSpecificOne(entity: MaskEntity?){
        specificOne?.value = entity
    }
}