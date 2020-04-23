package jay2468.maskmap.common

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import jay2468.maskmap.viewModel.WelcomeViewModel
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MyWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params), KodeinAware {
    override val kodein by closestKodein(context)
    private val welcomeViewModel: WelcomeViewModel by instance()
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        welcomeViewModel.insertMaskData()
        Result.success()
    }
}