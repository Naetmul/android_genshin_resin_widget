package danggai.domain.local

import danggai.domain.util.Constant
import java.util.*

data class DailyNoteSettings(
    val autoRefreshPeriod: Long,
    val notiEach40Resin: Boolean,
    val noti140Resin: Boolean,
    val notiCustomResin: Boolean,
    val customResin: Int,
    val notiExpedition: Boolean,
    val notiHomeCoin: Boolean,
    val notiDailyYet: Boolean,
    val notiDailyYetTime: Int,
    val notiWeeklyYet: Boolean,
    val notiWeeklyYetDay: Int,
    val notiWeeklyYetTime: Int,
) {
    companion object {
        val EMPTY = DailyNoteSettings (
            autoRefreshPeriod = Constant.PREF_DEFAULT_REFRESH_PERIOD,
            notiEach40Resin = false,
            noti140Resin = false,
            notiCustomResin = false,
            customResin = 0,
            notiExpedition = false,
            notiHomeCoin = false,
            notiDailyYet = false,
            notiDailyYetTime = 21,
            notiWeeklyYet = false,
            notiWeeklyYetDay = Calendar.SUNDAY,
            notiWeeklyYetTime = 21
        )
    }
}
