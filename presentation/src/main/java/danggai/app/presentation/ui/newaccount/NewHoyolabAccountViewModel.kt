package danggai.app.presentation.ui.newaccount

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danggai.app.presentation.R
import danggai.app.presentation.core.BaseViewModel
import danggai.app.presentation.util.CommonFunction
import danggai.app.presentation.util.Event
import danggai.app.presentation.util.log
import danggai.domain.core.ApiResult
import danggai.domain.db.account.entity.Account
import danggai.domain.db.account.usecase.AccountDaoUseCase
import danggai.domain.network.changedataswitch.usecase.ChangeDataSwitchUseCase
import danggai.domain.network.dailynote.usecase.DailyNoteUseCase
import danggai.domain.network.getgamerecordcard.usecase.GetGameRecordCardUseCase
import danggai.domain.resource.repository.ResourceProviderRepository
import danggai.domain.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewHoyolabAccountViewModel @Inject constructor(
    private val resource: ResourceProviderRepository,
    private val accountDao: AccountDaoUseCase,
    private val getGameRecordCard: GetGameRecordCardUseCase,
    private val changeDataSwitch: ChangeDataSwitchUseCase,
    private val dailyNote: DailyNoteUseCase,
) : BaseViewModel() {
    val sfProgress = MutableStateFlow(false)

    val sfServer = MutableStateFlow(0)
    val sfNickname = MutableStateFlow("")
    val sfHoyolabCookie = MutableStateFlow("")
    val sfGenshinUid = MutableStateFlow("")
    val sfNoGenshinAccount = MutableStateFlow(false)
    val sfEnableGenshinAutoCheckIn = MutableStateFlow(false)
    val sfEnableHonkai3rdAutoCheckIn = MutableStateFlow(false)
    val sfEnableHonkaiSRAutoCheckIn = MutableStateFlow(false)

    private var _dailyNotePrivateErrorCount = 0
    val dailyNotePrivateErrorCount
        get() = _dailyNotePrivateErrorCount

    private fun getUid(
        hoyolabUid: String,
        cookie: String,
        ds: String
    ) {
        viewModelScope.launch {
            getGameRecordCard(
                hoyolabUid = hoyolabUid,
                cookie = cookie,
                ds = ds,
                onStart = {
                    CoroutineScope(Dispatchers.Main).launch {
                        sfProgress.value = true
                    }
                },
                onComplete = {
                    CoroutineScope(Dispatchers.Main).launch {
                        sfProgress.value = false
                    }
                }
            ).collect {
                log.e(it)
                when (it) {
                    is ApiResult.Success -> {
                        when (it.data.retcode) {
                            Constant.RETCODE_SUCCESS -> {
                                if (it.data.data.list.any { gameRecordCard -> gameRecordCard.game_id == 2 }) {
                                    log.e()
                                    it.data.data.list.forEach { recordCard ->
                                        if (recordCard.game_id == Constant.GAME_ID_GENSHIN_IMPACT) {
                                            sfGenshinUid.value = recordCard.game_role_id
                                            sfNickname.value = recordCard.nickname

                                            when (recordCard.region) {
                                                Constant.SERVER_OS_ASIA -> sfServer.value = Constant.Server.ASIA.pref
                                                Constant.SERVER_OS_USA -> sfServer.value = Constant.Server.USA.pref
                                                Constant.SERVER_OS_EURO -> sfServer.value = Constant.Server.EUROPE.pref
                                                Constant.SERVER_OS_CHT -> sfServer.value = Constant.Server.CHT.pref
                                            }
                                        }
                                    }
                                    makeToast(resource.getString(R.string.msg_toast_get_uid_success))
                                } else if (it.data.data.list.isNotEmpty()) {
                                    log.e()
                                    makeToast(resource.getString(R.string.msg_toast_get_uid_error_genshin_data_not_exists))
                                } else {
                                    log.e()
                                    makeToast(resource.getString(R.string.msg_toast_get_uid_error_card_list_empty))
                                }
                            }
                            else -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_GET_GAME_RECORD_CARD, it.code, it.data.retcode)
                                makeToast(String.format(resource.getString(R.string.msg_toast_get_uid_error_include_error_code), it.data.retcode))
                            }
                        }
                    }
                    is ApiResult.Failure -> {
                        it.message.let { msg ->
                            log.e(msg)
                            CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_GET_GAME_RECORD_CARD, it.code, null)
                            makeToast(resource.getString(R.string.msg_toast_common_network_error))
                        }
                    }
                    is ApiResult.Error -> {
                        CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_GET_GAME_RECORD_CARD, null, null)
                        makeToast(resource.getString(R.string.msg_toast_get_uid_error))
                    }
                    is ApiResult.Null -> {
                        CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_GET_GAME_RECORD_CARD, null, null)
                        makeToast(resource.getString(R.string.msg_toast_common_body_null_error))
                    }
                }
            }
        }
    }

    private fun makeBattleChroniclePublic(
        gameId: Int,
        switchId: Int,
        isPublic: Boolean,
        cookie: String,
        ds: String
    ) {
        viewModelScope.launch {
            changeDataSwitch(
                gameId = gameId,
                switchId = switchId,
                isPublic = isPublic,
                cookie = cookie,
                ds = ds,
                onStart = {
                    CoroutineScope(Dispatchers.Main).launch {
                        sfProgress.value = true
                    }
                },
                onComplete = {
                    CoroutineScope(Dispatchers.Main).launch {
                        sfProgress.value = false
                    }
                }
            ).collect {
                log.e(it)
                when (it) {
                    is ApiResult.Success -> {
                        when (it.data.retcode) {
                            Constant.RETCODE_SUCCESS -> {
                                log.e()
                                makeToast(resource.getString(R.string.msg_toast_change_data_switch_success))
                            }
                            else -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_CHANGE_DATA_SWITCH, it.code, it.data.retcode)
                                makeToast(String.format(resource.getString(R.string.msg_toast_change_data_switch_error_include_error_code), it.data.retcode))
                            }
                        }
                    }
                    is ApiResult.Failure -> {
                        it.message.let { msg ->
                            log.e(msg)
                            CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_CHANGE_DATA_SWITCH, it.code, null)
                            makeToast(resource.getString(R.string.msg_toast_common_network_error))
                        }
                    }
                    is ApiResult.Error -> {
                        CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_CHANGE_DATA_SWITCH, null, null)
                        makeToast(resource.getString(R.string.msg_toast_change_data_switch_error))
                    }
                    is ApiResult.Null -> {
                        CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_CHANGE_DATA_SWITCH, null, null)
                        makeToast(resource.getString(R.string.msg_toast_common_body_null_error))
                    }
                }
            }
        }
    }

    private fun dailyNote(
        uid: String,
        server: String,
        cookie: String,
        ds: String
    ) {
        viewModelScope.launch {
            dailyNote(
                uid = uid,
                server = server,
                cookie = cookie,
                ds = ds,
                onStart = {
                    CoroutineScope(Dispatchers.Main).launch {
                        sfProgress.value = true
                    }
                },
                onComplete = {
                    CoroutineScope(Dispatchers.Main).launch {
                        sfProgress.value = false
                    }
                }
            ).collect {
                log.e(it)
                when (it) {
                    is ApiResult.Success -> {
                        when (it.data.retcode) {
                            Constant.RETCODE_SUCCESS -> {
                                log.e()
                                makeToast(resource.getString(R.string.msg_toast_dailynote_success))

                                val account = Account(
                                    sfNickname.value,
                                    sfHoyolabCookie.value,
                                    sfGenshinUid.value,
                                    sfServer.value,
                                    sfEnableGenshinAutoCheckIn.value,
                                    sfEnableHonkai3rdAutoCheckIn.value,
                                    sfEnableHonkaiSRAutoCheckIn.value,
                                    false
                                )

                                insertAccount(account)
                            }
                            Constant.RETCODE_ERROR_CHARACTOR_INFO -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_charactor_info))
                            }
                            Constant.RETCODE_ERROR_INTERNAL_DATABASE_ERROR -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_internal_database_error))
                            }
                            Constant.RETCODE_ERROR_TOO_MANY_REQUESTS -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_too_many_requests))
                            }
                            Constant.RETCODE_ERROR_NOT_LOGGED_IN,
                            Constant.RETCODE_ERROR_NOT_LOGGED_IN_2-> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_not_logged_in))
                            }
                            Constant.RETCODE_ERROR_NOT_LOGGED_IN_3 -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_not_logged_in_3))
                            }
                            Constant.RETCODE_ERROR_WRONG_ACCOUNT -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_wrong_account))
                            }
                            Constant.RETCODE_ERROR_DATA_NOT_PUBLIC -> {
                                log.e()
                                _dailyNotePrivateErrorCount += 1
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                sendEvent(Event.WhenDailyNoteIsPrivate())
                            }
                            Constant.RETCODE_ERROR_ACCOUNT_NOT_FOUND -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_account_not_found))
                            }
                            Constant.RETCODE_ERROR_INVALID_LANGUAGE -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_invalid_language))
                            }
                            Constant.RETCODE_ERROR_INVALID_INPUT_FORMAT -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(resource.getString(R.string.msg_toast_dailynote_error_invalid_input))
                            }
                            else -> {
                                log.e()
                                CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, it.data.retcode)
                                makeToast(String.format(resource.getString(R.string.msg_toast_dailynote_error_include_error_code), it.data.retcode))
                            }
                        }
                    }
                    is ApiResult.Failure -> {
                        it.message.let { msg ->
                            log.e(msg)
                            CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, it.code, null)
                            makeToast(resource.getString(R.string.msg_toast_common_network_error))
                        }
                    }
                    is ApiResult.Error -> {
                        CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, null, null)
                        makeToast(resource.getString(R.string.msg_toast_dailynote_error))
                    }
                    is ApiResult.Null -> {
                        CommonFunction.sendCrashlyticsApiLog(Constant.API_NAME_DAILY_NOTE, null,null)
                        makeToast(resource.getString(R.string.msg_toast_common_body_null_error))
                    }
                }
            }
        }
    }

    private fun insertAccount(
        account: Account
    ) {
        viewModelScope.launch {
            accountDao.insertAccount(account).collect {
                log.e()
                sendEvent(Event.FinishThisActivity())
            }
        }
    }

    fun onClickSetServer(server: Constant.Server) {
        log.e("server -> $server")
        sfServer.value = server.pref
    }

    fun makeDailyNotePublic() {
        log.e()
        makeBattleChroniclePublic(
            gameId = 2,
            switchId = 3,
            isPublic = true,
            cookie = sfHoyolabCookie.value,
            ds = CommonFunction.getGenshinDS()
        )
    }

    fun onClickGetCookie() {
        log.e()
        sendEvent(Event.GetCookie())
    }

    fun onClickNoGenshinAccount() {
        if (sfNoGenshinAccount.value) {
            sfNickname.value = resource.getString(R.string.guest)
            sfGenshinUid.value = "-1"
        } else {
            sfNickname.value = ""
            sfGenshinUid.value = ""
        }
    }

    fun onClickGetUid() {
        log.e()
        if (!sfHoyolabCookie.value.contains(("ltuid="))) {
            makeToast(resource.getString(R.string.msg_toast_get_uid_error_no_ltuid))
            return
        }

        val cookieData = mutableMapOf<String, String>()

        sfHoyolabCookie.value.split(";").onEach { item ->
            if (item == "") return@onEach

            val parsedKeyValue = item.trim().split("=")
            cookieData[parsedKeyValue[0]] = parsedKeyValue[1]
        }

        getUid(
            cookieData["ltuid"]?:"",
            sfHoyolabCookie.value,
            CommonFunction.getGenshinDS()
        )
    }

    fun onClickSave() {
        log.e()

        if (sfNoGenshinAccount.value) {
            insertAccount(
                Account.GUEST.copy(
                    nickname = resource.getString(R.string.guest),
                    cookie = sfHoyolabCookie.value,
                    enable_genshin_checkin = sfEnableGenshinAutoCheckIn.value,
                    enable_honkai3rd_checkin = sfEnableHonkai3rdAutoCheckIn.value,
                    enable_honkai_sr_checkin = sfEnableHonkaiSRAutoCheckIn.value
                )
            )
        } else {
            sfGenshinUid.value = sfGenshinUid.value.trim()
            sfHoyolabCookie.value = sfHoyolabCookie.value.trim()

            dailyNote(
                sfGenshinUid.value,
                when (sfServer.value) {
                    Constant.PREF_SERVER_ASIA -> Constant.SERVER_OS_ASIA
                    Constant.PREF_SERVER_EUROPE -> Constant.SERVER_OS_EURO
                    Constant.PREF_SERVER_USA -> Constant.SERVER_OS_USA
                    Constant.PREF_SERVER_CHT -> Constant.SERVER_OS_CHT
                    else -> Constant.SERVER_OS_ASIA
                },
                sfHoyolabCookie.value,
                CommonFunction.getGenshinDS()
            )
        }
    }

    fun selectAccountByUid(uid: String) {
        log.e()

        viewModelScope.launch {
            accountDao.selectAccountByUid(uid).collect { account ->
                log.e(account)
                sfHoyolabCookie.value = account.cookie
                sfGenshinUid.value = account.genshin_uid
                sfNickname.value = account.nickname
                sfServer.value = account.server
                sfEnableGenshinAutoCheckIn.value = account.enable_genshin_checkin
                sfEnableHonkai3rdAutoCheckIn.value = account.enable_honkai3rd_checkin
                sfEnableHonkaiSRAutoCheckIn.value = account.enable_honkai_sr_checkin

                if (account.genshin_uid == "-1") sfNoGenshinAccount.value = true
            }
        }
    }
}