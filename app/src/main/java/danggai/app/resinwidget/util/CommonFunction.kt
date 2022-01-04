package danggai.app.resinwidget.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.work.*
import com.google.firebase.crashlytics.CustomKeysAndValues
import com.google.firebase.crashlytics.FirebaseCrashlytics
import danggai.app.resinwidget.BuildConfig
import danggai.app.resinwidget.Constant
import danggai.app.resinwidget.R
import danggai.app.resinwidget.worker.CheckInWorker
import danggai.app.resinwidget.worker.DailyNoteWorker
import java.lang.NumberFormatException
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.streams.asSequence


object CommonFunction {

    fun getGenshinDS(): String {
        val source = "abcdefghijklmnopqrstuvwxyz"

        val t = System.currentTimeMillis() / 1000L
        val r = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Random().ints(6, 0, source.length)
                .asSequence()
                .map(source::get)
                .joinToString("")
        } else {
            "abcdef"
        }
        val hash = encodeToMD5("salt=${Constant.OS_SALT}&t=$t&r=$r")
        return "${t},${r},${hash}"
    }

    fun startOneTimeCheckInWorker(context: Context) {
        log.e()

        if (!PreferenceManager.getBooleanIsValidUserData(context)) {
            log.e()
            return
        }

        val workManager = WorkManager.getInstance(context)
        val workRequest = OneTimeWorkRequestBuilder<CheckInWorker>()
            .setInputData(workDataOf(Constant.ARG_IS_ONE_TIME to true))
            .build()
        workManager.enqueue(workRequest)
    }

    fun startUniquePeriodicCheckInWorker(context: Context, isRetry: Boolean) {
        if (!PreferenceManager.getBooleanIsValidUserData(context)) {
            log.e()
            return
        }

        log.e("isRetry -> $isRetry")

        val delay: Long = if (isRetry) { 15L } else { calculateDelayUntilChinaMidnight(Calendar.getInstance()) }
        log.e(delay)

        val workManager = WorkManager.getInstance(context)
        val workRequest = PeriodicWorkRequestBuilder<CheckInWorker>(delay, TimeUnit.MINUTES)
            .setInitialDelay(delay, TimeUnit.MINUTES)
            .setInputData(workDataOf(Constant.ARG_IS_ONE_TIME to false))
            .build()
        workManager.enqueueUniquePeriodicWork(Constant.WORKER_UNIQUE_NAME_AUTO_CHECK_IN, ExistingPeriodicWorkPolicy.REPLACE, workRequest)
    }

    fun shutdownRefreshWorker(context: Context) {
//        if (!PreferenceManager.getBooleanAutoRefresh(context)) return
        log.e()

        val workManager = WorkManager.getInstance(context)

        workManager.cancelUniqueWork(Constant.WORKER_UNIQUE_NAME_AUTO_REFRESH)
    }

    fun startOneTimeRefreshWorker(context: Context) {
//        if (!PreferenceManager.getBooleanAutoRefresh(context)) return
        log.e()

        if (!PreferenceManager.getBooleanIsValidUserData(context)) {
            log.e()
            return
        }

        val workManager = WorkManager.getInstance(context)
        val workRequest = OneTimeWorkRequestBuilder<DailyNoteWorker>()
            .setInputData(workDataOf(Constant.ARG_IS_ONE_TIME to true))
            .build()
        workManager.enqueue(workRequest)
    }

    fun startUniquePeriodicRefreshWorker(context: Context) {
        val period = PreferenceManager.getLongAutoRefreshPeriod(context)

        startUniquePeriodicRefreshWorker(context, period)
    }

    fun startUniquePeriodicRefreshWorker(context: Context, period: Long) {
        if (PreferenceManager.getLongAutoRefreshPeriod(context) == -1L ||
            !PreferenceManager.getBooleanIsValidUserData(context)) {
                log.e()
                return
        }

        log.e("period -> $period")

        val workManager = WorkManager.getInstance(context)
        val workRequest = PeriodicWorkRequestBuilder<DailyNoteWorker>(period, TimeUnit.MINUTES)
            .setInitialDelay(period, TimeUnit.MINUTES)
            .setInputData(workDataOf(Constant.ARG_IS_ONE_TIME to false))
            .build()
        workManager.enqueueUniquePeriodicWork(Constant.WORKER_UNIQUE_NAME_AUTO_REFRESH, ExistingPeriodicWorkPolicy.REPLACE, workRequest)
    }

    private fun encodeToMD5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
            .lowercase(Locale.getDefault())
    }

    fun getIntentAppWidgetUiUpdate(): Intent {
        val broadcast= Intent()
        broadcast.action = Constant.ACTION_APPWIDGET_UPDATE
        broadcast.putExtra(Constant.REFRESH_DATA, false)
        broadcast.putExtra(Constant.REFRESH_UI, true)

        return broadcast
    }

    fun getTimeSyncTimeFormat(): String {
        return SimpleDateFormat(Constant.DATE_FORMAT_SYNC_TIME).format(Date())
    }

    fun sendCrashlyticsApiLog(apiName: String, metaCode: Int, retCode: String?) {
        if (BuildConfig.DEBUG) return

        log.e()
        val keysAndValues = CustomKeysAndValues.Builder()
            .putString("api name", apiName)
            .putInt("meta code", metaCode)
            .putString("ret code", retCode?:"")
            .build()

        FirebaseCrashlytics.getInstance().setCustomKeys(keysAndValues)
    }

    fun secondToRemainTime(context: Context, _second: String): String {
        var hour: Int = 0
        var minute: Int = 0
        var second: Int = 0

        try {
            hour = _second.toInt() / 3600
            minute = (_second.toInt() - hour * 3600) / 60
            second = _second.toInt() % 600
        } catch (e: Exception) {
            hour = 0
            minute = 0
            second = 0
        }

        return String.format(context.getString(R.string.widget_ui_remain_time), hour, minute)
    }

    fun secondToFullChargeTime(context: Context, second: String): String {
        val cal = Calendar.getInstance()
        val date= Date()
        cal.time = date

        try {
            if (second.toInt() == 0)
                return context.getString(R.string.widget_ui_max_time_resin_max)

            if (second.toInt() > 144000 || second.toInt() < -144000)
                return String.format(context.getString(R.string.widget_ui_max_time), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))

            cal.add(Calendar.SECOND, second.toInt())

            val minute = String.format("%02d", cal.get(Calendar.MINUTE))

            return String.format(context.getString(R.string.widget_ui_max_time), cal.get(Calendar.HOUR_OF_DAY), minute)
        } catch (e: NumberFormatException) {
            return context.getString(R.string.widget_ui_max_time_resin_max)
        }
    }

    fun sendNotification(id: Int, context: Context, title: String, msg: String) {
        var notificationId = ""
        var notificationDesc = ""

        when (id) {
            in 1..9 -> {
                notificationId = Constant.PUSH_CHANNEL_RESIN_NOTI_ID
                notificationDesc = context.getString(R.string.push_resin_noti_description)
            }
            in 10..19 -> {
                notificationId = Constant.PUSH_CHANNEL_CHECK_IN_ID
                notificationDesc = context.getString(R.string.push_checkin_description)
            }
            else -> {
                notificationId = Constant.PUSH_CHANNEL_DEFAULT_ID
                notificationDesc = context.getString(R.string.push_default_noti_description)
            }
        }

        val notificationManager: NotificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) ?: return

        val builder = NotificationCompat.Builder(context, notificationId)
            .setSmallIcon(R.drawable.resin)
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(notificationId, title, NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = notificationDesc
                }
            )
        }

        notificationManager.notify(id, builder.build())
    }

    private fun calculateDelayUntilChinaMidnight(startCalendar: Calendar): Long {
        val targetCalendar = Calendar.getInstance()
        targetCalendar.timeZone = TimeZone.getTimeZone(Constant.CHINA_TIMEZONE)
        targetCalendar.set(Calendar.MINUTE, 1)
        targetCalendar.set(Calendar.HOUR, 0)
        targetCalendar.set(Calendar.AM_PM, Calendar.AM)

        if (startCalendar.get(Calendar.HOUR_OF_DAY) >= 1) targetCalendar.add(Calendar.DAY_OF_YEAR, 1)

        val delay = (targetCalendar.time.time - startCalendar.time.time) / 60000

        log.e("now time -> ${startCalendar.time}")
        log.e("target time -> ${targetCalendar.time}")
        log.e("delayed -> ${delay / 60}h ${delay % 60}m")

        return if (delay < 0) {
            targetCalendar.add(Calendar.DAY_OF_YEAR, 1)
            (targetCalendar.time.time - startCalendar.time.time) / 60000
        } else {
            delay
        }
    }

    fun Context.isDarkMode(): Boolean {
        return resources.configuration.uiMode and UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }

    fun setWidgetTheme(view: RemoteViews, context: Context) {
        val bgColor: Int =  if (PreferenceManager.getIntWidgetTheme(context) == Constant.PREF_WIDGET_THEME_LIGHT) {
            ColorUtils.setAlphaComponent(ContextCompat.getColor(context, R.color.white), PreferenceManager.getIntBackgroundTransparency(context))
        } else if  ((PreferenceManager.getIntWidgetTheme(context) == Constant.PREF_WIDGET_THEME_DARK) || context.isDarkMode()) {
            ColorUtils.setAlphaComponent(ContextCompat.getColor(context, R.color.black), PreferenceManager.getIntBackgroundTransparency(context))
        } else {
            ColorUtils.setAlphaComponent(ContextCompat.getColor(context, R.color.white), PreferenceManager.getIntBackgroundTransparency(context))
        }

        val mainFontColor: Int =  if (PreferenceManager.getIntWidgetTheme(context) == Constant.PREF_WIDGET_THEME_LIGHT) {
            ContextCompat.getColor(context, R.color.widget_font_main_light)
        } else if  ((PreferenceManager.getIntWidgetTheme(context) == Constant.PREF_WIDGET_THEME_DARK) || context.isDarkMode()) {
            ContextCompat.getColor(context, R.color.widget_font_main_dark)
        } else {
            ContextCompat.getColor(context, R.color.widget_font_main_light)
        }

        val subFontColor: Int =  if (PreferenceManager.getIntWidgetTheme(context) == Constant.PREF_WIDGET_THEME_LIGHT) {
            ContextCompat.getColor(context, R.color.widget_font_sub_light)
        } else if  ((PreferenceManager.getIntWidgetTheme(context) == Constant.PREF_WIDGET_THEME_DARK) || context.isDarkMode()) {
            ContextCompat.getColor(context, R.color.widget_font_sub_dark)
        } else {
            ContextCompat.getColor(context, R.color.widget_font_sub_light)
        }

//        view.set(R.id.ll_body, R.drawable.rounded_square_5dp)
//        view.setInt(R.id.ll_body, "setColorFilter", getColor(context, R.color.white))
        view.setInt(R.id.ll_root, "setBackgroundColor", bgColor)

        view.setTextColor(R.id.tv_resin, mainFontColor)
        view.setTextColor(R.id.tv_resin_max, mainFontColor)
        view.setTextColor(R.id.tv_remain_time, mainFontColor)
        view.setInt(R.id.iv_refersh, "setColorFilter", subFontColor)
        view.setTextColor(R.id.tv_sync_time, subFontColor)
    }

    fun setDailyNoteData(context: Context, dailyNote: DailyNote) {
        log.e()

        PreferenceManager.setIntCurrentResin(context, dailyNote.current_resin)
        PreferenceManager.setIntMaxResin(context, dailyNote.max_resin)
        PreferenceManager.setStringResinRecoveryTime(context, dailyNote.resin_recovery_time?:"-1")
        PreferenceManager.setStringRecentSyncTime(context, getTimeSyncTimeFormat())

        PreferenceManager.setIntCurrentDailyCommission(context, dailyNote.finished_task_num)
        PreferenceManager.setIntMaxDailyCommission(context, dailyNote.total_task_num)
        PreferenceManager.setBooleanGetDailyCommissionReward(context, dailyNote.is_extra_task_reward_received)

        PreferenceManager.setIntCurrentWeeklyBoss(context, dailyNote.remain_resin_discount_num)
        PreferenceManager.setIntMaxWeeklyBoss(context, dailyNote.resin_discount_num_limit)

        PreferenceManager.setIntCurrentExpedition(context, dailyNote.current_expedition_num)
        PreferenceManager.setIntMaxExpedition(context, dailyNote.max_expedition_num)

        val expeditionTime: String = try {
            if (dailyNote.expeditions == null || dailyNote.expeditions.isEmpty()) "0"
            else dailyNote.expeditions.maxOf { it.remained_time }
        } catch (e: Exception) {
            "0"
        }

        PreferenceManager.setStringExpeditionTime(context, expeditionTime)
    }

}