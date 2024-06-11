import com.example.sjsk.firestore.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


//API의 엔드포인트를 Kotlin 인터페이스로 정의
interface ApiService {
    companion object {
        private const val myApiKey = "uK%2B%2FhBson5QHf%2BFyd3Cv5%2FUNEj3OCsnVXZ08WSg4yycSgBrCsApgv%2FuXcFjAtj2iIVSMLiho25FtL8bgR0aNlg%3D%3D"
    }

    @GET("getFoodNtrCpntDbInq")
    fun getData(
        @Query("serviceKey", encoded = true) apiKey: String = myApiKey, // API 키를 요청에 포함
        @Query("FOOD_NM_KR", encoded = true) foodName: String = "포카칩",
    ): Call<ApiResponse>
}

// 함수 내부에서 인코딩된 값으로 API를 호출하는 예
class Repository {
    fun fetchFoodData(apiService: ApiService, foodName: String): Call<ApiResponse> {
        val encodedFoodName = encodeValue(foodName)
        return apiService.getData(foodName = encodedFoodName)
    }
}

fun encodeValue(value: String): String {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}