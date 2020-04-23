package jay2468.maskmap.viewModel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jay2468.maskmap.common.maskPointJson
import jay2468.maskmap.data.bean.Points
import jay2468.maskmap.data.db.dao.MaskDao
import jay2468.maskmap.data.db.entity.MaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class WelcomeViewModel(@param:NonNull private val mApplication: Application, private val maskDao: MaskDao) :
        AndroidViewModel(mApplication) {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun insertMaskData() {
        _loading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val body = Jsoup.connect(maskPointJson)
                    .timeout(30000)
                    .maxBodySize(0)
                    .execute()
                    .body()
            val resultType = object : TypeToken<Points>() {}.type
            val points = Gson().fromJson<Points>(body, resultType)
            points.features.forEach {
                with(it) {
                    val propertie = properties
                    val geometry = geometry
                    with(propertie) {
                        val maskEntity = MaskEntity(id, name, phone, address, mask_adult, mask_child, updated, available, note,
                                custom_note, website, county, town, cunli, service_periods, geometry.coordinates[1], geometry.coordinates[0])
                        maskDao.insert(maskEntity)
                    }
                }
            }
            _loading.postValue(false)
        }
    }
}