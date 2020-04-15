package jay2468.maskmap.activity

import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jay2468.maskmap.R
import jay2468.maskmap.common.MyLocationListener
import jay2468.maskmap.view.CommProgressDialog
import jay2468.maskmap.view.MarkerDialog
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class MainActivity : AppCompatActivity(), KodeinAware {
    private val _parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein)
        bind() from singleton { CommProgressDialog.createDialog(this@MainActivity, R.drawable.im_loading_anim) }
        bind() from singleton { MarkerDialog(this@MainActivity) }
        bind() from singleton { MyLocationListener(this@MainActivity,getSystemService(Context.LOCATION_SERVICE) as LocationManager) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}