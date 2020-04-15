package jay2468.maskmap.net

import jay2468.maskmap.data.bean.Result
import kotlinx.coroutines.Deferred

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitInterface {
    @Headers("Accept: application/json, text/javascript, */*")
    @GET("json")
    fun distanceMatrix(
        @Query("origins") origins: String, @Query("destinations") destinations: String, @Query(
            "key"
        ) key: String
    ): Deferred<Response<Result>>
}

