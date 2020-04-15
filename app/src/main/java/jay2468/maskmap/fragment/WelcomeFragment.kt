package jay2468.maskmap.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import jay2468.maskmap.R
import jay2468.maskmap.view.CommProgressDialog
import jay2468.maskmap.viewModel.WelcomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Thread.sleep

class WelcomeFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()
    private val welcomeViewModel: WelcomeViewModel by instance()
    private val commProgressDialog: CommProgressDialog by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.welcome, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        welcomeViewModel.also {
            it.loading.observe(viewLifecycleOwner, Observer<Boolean> { isLoading ->
                if (isLoading) {
                    commProgressDialog.show()
                } else {
                    commProgressDialog.dismiss()
                    CoroutineScope(Dispatchers.IO).launch {
                        sleep(500)
                        findNavController().navigate(R.id.action_welcomeFragment_to_mapsFragment)
                    }
                }
            })
        }
        welcomeViewModel.insertMaskData()
    }
}