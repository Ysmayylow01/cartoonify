package tm.hazar.myapplication

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CartoonApi {
    @Multipart
    @POST("/api/portrait/effects/portrait-animation")
    suspend fun cartoonify(
        @Header("ailabapi-api-key") apiKey: String,
        @Part image: MultipartBody.Part,
        @Part("type") type: RequestBody
    ): Response<CartoonResponse>
}
