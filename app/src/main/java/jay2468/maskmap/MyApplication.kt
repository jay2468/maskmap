package jay2468.maskmap

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import jay2468.maskmap.data.db.MaskDatabase
import jay2468.maskmap.data.repository.MapRepository
import jay2468.maskmap.viewModel.MapViewModel
import jay2468.maskmap.viewModel.WelcomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class MyApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { MaskDatabase(instance()) }
        bind() from singleton { instance<MaskDatabase>().getMaskDao() }
        bind() from singleton { WelcomeViewModel(instance(), instance()) }
        bind() from singleton { MapViewModel(instance(), instance()) }
        bind() from singleton { MapRepository(instance()) }
    }
}