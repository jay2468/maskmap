package jay2468.maskmap.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import jay2468.maskmap.R
import jay2468.maskmap.adapter.NearbyPharmacyAdapter
import jay2468.maskmap.data.db.entity.MaskEntity
import jay2468.maskmap.databinding.NearbyPharmacyBinding
import jay2468.maskmap.viewModel.MapViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NearbyPharmacyFragment : Fragment(),KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModel: MapViewModel by instance<MapViewModel>()
    private lateinit var binding: NearbyPharmacyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.nearby_pharmacy, null, false)

        with(binding){
            btnBack.setOnClickListener { findNavController().popBackStack() }
            recyclerView.layoutManager = LinearLayoutManager(context)

            val arguments = NearbyPharmacyFragmentArgs.fromBundle(arguments!!).entities
            recyclerView.adapter = NearbyPharmacyAdapter(context!!,arguments as MutableList<MaskEntity>)
        }

        viewModel.specificOne.observe(viewLifecycleOwner, Observer { if(it!=null) findNavController().popBackStack() })

        return binding.root
    }
}