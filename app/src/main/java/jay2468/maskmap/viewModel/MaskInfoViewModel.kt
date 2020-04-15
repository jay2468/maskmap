package jay2468.maskmap.viewModel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import jay2468.maskmap.data.db.entity.MaskEntity

class MaskInfoViewModel(@param:NonNull private val mApplication: Application, maskEntity: MaskEntity?) :
    AndroidViewModel(mApplication) {
    val name: String? = maskEntity?.name
    val address: String? = maskEntity?.address
    val phone: String? = maskEntity?.phone
    val mask_adult: String? = maskEntity?.mask_adult.toString()
    val mask_child: String? = maskEntity?.mask_child.toString()
    val updated: String? = maskEntity?.updated
    val available: String? = maskEntity?.available
    val note:String? = maskEntity?.note
}