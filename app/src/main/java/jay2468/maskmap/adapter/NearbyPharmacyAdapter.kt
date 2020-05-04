package jay2468.maskmap.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jay2468.maskmap.R
import jay2468.maskmap.data.db.entity.MaskEntity
import jay2468.maskmap.databinding.MaskListBinding
import jay2468.maskmap.view.MarkerDialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import androidx.constraintlayout.widget.ConstraintLayout
import jay2468.maskmap.viewModel.MapViewModel
import org.kodein.di.generic.instance

class NearbyPharmacyAdapter(private val context:Context ,private val entities:MutableList<MaskEntity>) : RecyclerView.Adapter<NearbyPharmacyAdapter.ViewHolder>(),KodeinAware {
    override val kodein by closestKodein(context)
    private val markerDialog: MarkerDialog by instance<MarkerDialog>()
    private val viewModel: MapViewModel by instance<MapViewModel>()

    inner class ViewHolder(private val binding: MaskListBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var constraintLayout :ConstraintLayout
        lateinit var locate: ImageView
        lateinit var tel: ImageView
        fun bind(entity: MaskEntity){
            with(binding){
                maskEntity = entity
                constraintLayout = root
                locate = btnLocate
                tel = btnTel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.mask_list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            val entity = entities[position]
            bind(entity)
            constraintLayout.setOnClickListener {
                markerDialog.maskEntity = entity
                markerDialog.show()
            }
            locate.setOnClickListener {
                viewModel.setSpecificOne(entity)
            }
            tel.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                with(intent){
                    val tel = Uri.parse("tel:${entity.phone}")
                    data = tel
                    context.startActivity(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return entities.size
    }
}