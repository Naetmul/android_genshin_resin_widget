package danggai.app.resinwidget.data.api

import danggai.app.resinwidget.data.res.ResChangeDataSwitch
import danggai.app.resinwidget.data.res.ResDailyNote
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @Headers(
        "x-rpc-client_type: 4",
        "x-rpc-app_version: 1.5.0",
        "x-rpc-language: ko-kr",
    )
    @GET("/game_record/genshin/api/dailyNote?server=os_asia")
    fun dailyNote(@Query("role_id") uid: String, @Header("Cookie") cookie: String, @Header("DS") ds: String): Observable<Response<ResDailyNote.Data>>

    @Headers(
        "x-rpc-client_type: 4",
        "x-rpc-app_version: 1.5.0",
        "x-rpc-language: ko-kr",
    )
    @POST("/game_record/card/wapi/changeDataSwitch")
    fun changeDataSwitch(@Query("game_id") gameId: Int, @Query("switch_id") switchId: Int, @Query("is_public") isPublic: Boolean, @Header("Cookie") cookie: String, @Header("DS") ds: String): Observable<Response<ResChangeDataSwitch.Data>>
    /* 원신 기준, game_id:2,switch_id:1~4 */

}