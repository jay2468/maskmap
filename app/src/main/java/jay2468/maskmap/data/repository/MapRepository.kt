package jay2468.maskmap.data.repository

import androidx.lifecycle.MutableLiveData
import jay2468.maskmap.data.bean.Result
import jay2468.maskmap.net.RetrofitUtil
import jay2468.maskmap.data.db.dao.MaskDao
import jay2468.maskmap.data.db.entity.MaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapRepository(private val maskDao: MaskDao) {
    suspend fun distanceMatrix(
        origins: String,
        destinations: String,
        key: String,
        nearByPharmacy: MutableLiveData<Result>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result =
                RetrofitUtil.getRetrofitService().distanceMatrix(origins, destinations, key).await()
            if (result.isSuccessful)
                nearByPharmacy.postValue(result.body())
        }
    }

    fun getAdressByCity(county: String, town: String, address: MutableLiveData<List<MaskEntity>>) {
        CoroutineScope(Dispatchers.IO).launch {
            address.postValue(maskDao.getAdressByCity(county, town))
        }
    }

    fun getAllAdress(address: MutableLiveData<List<MaskEntity>>){
        CoroutineScope(Dispatchers.IO).launch {
            address.postValue(maskDao.getAllAdress())
        }
    }
}