package jay2468.maskmap.viewModel.viewModelFactory

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jay2468.maskmap.data.db.entity.MaskEntity
import jay2468.maskmap.viewModel.MaskInfoViewModel

class ViewModelFactory(@param:NonNull private val mApplication: Application, private val maskEntity: MaskEntity?) :
    ViewModelProvider.AndroidViewModelFactory(mApplication) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MaskInfoViewModel(mApplication, maskEntity) as T
    }
}