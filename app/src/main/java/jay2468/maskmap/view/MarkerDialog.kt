package jay2468.maskmap.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import jay2468.maskmap.R
import jay2468.maskmap.data.db.entity.MaskEntity
import jay2468.maskmap.databinding.MaskDialogBinding

class MarkerDialog(private val mContext: Activity) : Dialog(mContext){
    var maskEntity: MaskEntity? = null
    private lateinit var binding: MaskDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.mask_dialog,
            null,
            false
        )
        with(binding) {
            btnClose.setOnClickListener { dismiss() }
            btnTel.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                with(intent){
                    val tel = Uri.parse("tel:${maskEntity?.phone}")
                    data = tel
                    context.startActivity(this)
                }
            }
            setContentView(root)
        }

        run {
            val display = mContext.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val height = size.y
            val width = size.x
            val params = window?.attributes
            params?.width = (width * 0.9).toInt()
            params?.height = (height * 0.8).toInt()
            window?.attributes = params
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        val viewModel =
//            ViewModelProvider(
//                mContext as AppCompatActivity,
//                ViewModelFactory(Application(), maskEntity)
//            ).get(
//                maskEntity!!.id,
//                MaskInfoViewModel::class.java
//            )
//        binding.viewModel = viewModel
        binding.maskEntity = maskEntity
    }
}