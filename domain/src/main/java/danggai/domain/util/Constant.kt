package danggai.domain.util

object Constant {

    /* URL */

    const val OS_TAKUMI_URL = "https://bbs-api-os.hoyoverse.com"
    const val CN_TAKUMI_URL = "https://api-takumi.hoyoverse.com"

    const val OS_SALT = "6cqshh5dhw73bzxn20oexa9k516chk7s"
    const val CN_SALT = "xV8v4Qu54lUKrEYFZkJhB8cuOh9Asafs"

    const val OS_GENSHIN_CHECK_IN_URL = "https://hk4e-api-os.hoyoverse.com/event/sol/sign"
    const val OS_GENSHIN_ACT_ID = "e202102251931481"
    const val CN_GENSHIN_CHECK_IN_URL = "https://api-takumi.hoyoverse.com/event/bbs_sign_reward/"
    const val CN_GENSHIN_ACT_ID = "e202009291139501"

    const val OS_HONKAI_3RD_CHECK_IN_URL = "https://sg-public-api.hoyolab.com/event/mani/sign"
    const val OS_HONKAI_3RD_ACT_ID = "e202110291205111"

    const val HOW_CAN_I_GET_COOKIE_URL = "https://github.com/danggai/android_genshin_resin_widget/blob/master/how_to_get_hoyolab_cookie.md"

    const val SERVER_CN_GF_01 = "cn_gf01"
    const val SERVER_CN_QD_01 = "cn_qd01"
    const val SERVER_OS_USA = "os_usa"
    const val SERVER_OS_EURO = "os_euro"
    const val SERVER_OS_ASIA = "os_asia"
    const val SERVER_OS_CHT = "os_cht"

    const val LANG_KO_KR = "ko-kr"
    const val LANG_EN_US = "en-us"

    const val GAME_ID_HONKAI_3RD = 1
    const val GAME_ID_GENSHIN_IMPACT = 2

    /* HTTP STATUS CODE */

    const val META_CODE_SUCCESS = 200
    const val META_CODE_BAD_REQUEST = 400
    const val META_CODE_NOT_FOUND = 404
    const val META_CODE_CLIENT_ERROR = 444
    const val META_CODE_SERVER_ERROR = 500


    /* RETCODE */

    const val RETCODE_SUCCESS = "0"
    const val RETCODE_ERROR_CHARACTOR_INFO = "1009"
    const val RETCODE_ERROR_INTERNAL_DATABASE_ERROR = "-1"
    const val RETCODE_ERROR_TOO_MANY_REQUESTS = "10101"
    const val RETCODE_ERROR_NOT_LOGGED_IN = "-100"
    const val RETCODE_ERROR_NOT_LOGGED_IN_2 = "10001"
    const val RETCODE_ERROR_NOT_LOGGED_IN_3 = "10103"
    const val RETCODE_ERROR_DATA_NOT_PUBLIC = "10102"
    const val RETCODE_ERROR_WRONG_ACCOUNT = "10104"
    const val RETCODE_ERROR_ACCOUNT_NOT_FOUND = "-10002"
    const val RETCODE_ERROR_INVALID_LANGUAGE = "-108"
    const val RETCODE_ERROR_INVALID_INPUT_FORMAT = "-502"

    const val RETCODE_ERROR_CLAIMED_DAILY_REWARD = "-5003"
    const val RETCODE_ERROR_CHECKED_INTO_HOYOLAB = "2001"
    const val RETCODE_ERROR_TOO_FAST = "-1004"


    /* API NAME */

    const val API_NAME_DAILY_NOTE = "Daily Note"
    const val API_NAME_CHANGE_DATA_SWITCH = "Change Data Switch"
    const val API_NAME_GET_GAME_RECORD_CARD = "Get Game Record Card"
    const val API_NAME_CHARACTERS = "Characters"
    const val API_NAME_CHECK_IN = "Check In"



    /* WORKER */

    const val WORKER_UNIQUE_NAME_AUTO_REFRESH = "AutoRefreshWork"
    const val WORKER_UNIQUE_NAME_AUTO_CHECK_IN = "AutoCheckInWork"
    const val WORKER_UNIQUE_NAME_TALENT_WIDGET_REFRESH = "TalentWidgetRefreshWork"



    /* FORMAT, REGEX */

    const val PATTERN_ENG_NUM_ONLY = "^[a-zA-Z0-9]+$"
    const val PATTERN_NUM_ONLY = "[^\\d]"

    const val DATE_FORMAT_SYNC_TIME = "MM/dd HH:mm"



    /* PREFERENCE */

    const val PREF_COOKIE = "PREF_COOKIE"
    const val PREF_UID = "PREF_UID"

    const val PREF_FIRST_LAUNCH = "PREF_FIRST_LAUNCH"

    const val PREF_IS_VALID_USERDATA = "PREF_IS_VALID_USERDATA"

    const val PREF_WIDGET_SETTINGS = "PREF_WIDGET_SETTINGS"
    const val PREF_CHECK_IN_SETTINGS = "PREF_CHECK_IN_SETTINGS"
    const val PREF_RESIN_WIDGET_DESIGN_SETTINGS = "PREF_RESIN_WIDGET_DESIGN_SETTINGS"
    const val PREF_DETAIL_WIDGET_DESIGN_SETTINGS = "PREF_DETAIL_WIDGET_DESIGN_SETTINGS"
    const val PREF_SELECTED_CHARACTER_ID_LIST = "PREF_SELECTED_CHARACTER_ID_LIST"
    const val PREF_DAILY_NOTE_DATA = "PREF_DAILY_NOTE_DATA"

    /*resin widget settings*/
    const val PREF_SERVER = "PREF_SERVER"
    const val PREF_AUTO_REFRESH_PERIOD = "PREF_AUTO_REFRESH_PERIOD"
    const val PREF_NOTI_EACH_40_RESIN = "PREF_NOTI_EACH_40_RESIN"
    const val PREF_NOTI_140_RESIN = "PREF_NOTI_140_RESIN"
    const val PREF_NOTI_CUSTOM_RESIN_BOOLEAN = "PREF_NOTI_CUSTOM_RESIN_BOOLEAN"
    const val PREF_NOTI_CUSTOM_TARGET_RESIN = "PREF_NOTI_CUSTOM_RESIN_INT"
    const val PREF_NOTI_EXPEDITION_DONE = "PREF_NOTI_EXPEDITION_DONE"
    const val PREF_NOTI_HOME_COIN_FULL = "PREF_NOTI_HOME_COIN_FULL"

    /*check in settings*/
    const val PREF_ENABLE_GENSHIN_AUTO_CHECK_IN = "PREF_ENABLE_AUTO_CHECK_IN"
    const val PREF_ENABLE_HONKAI_3RD_AUTO_CHECK_IN = "PREF_ENABLE_AUTO_CHECK_IN_HONKAI_3RD"
    const val PREF_NOTI_CHECK_IN_SUCCESS = "PREF_NOTI_CHECK_IN_SUCCESS"
    const val PREF_NOTI_CHECK_IN_FAILED = "PREF_NOTI_CHECK_IN_FAILED"

    /*resin widget design settings*/
    const val PREF_WIDGET_THEME = "PREF_WIDGET_THEME"
    const val PREF_WIDGET_RESIN_TIME_NOTATION = "PREF_TIME_NOTATION"
    const val PREF_WIDGET_RESIN_IMAGE_VISIBILITY = "PREF_WIDGET_RESIN_IMAGE_VISIBILITY"
    const val PREF_WIDGET_RESIN_FONT_SIZE = "PREF_WIDGET_RESIN_FONT_SIZE"
    const val PREF_WIDGET_BACKGROUND_TRANSPARENCY = "PREF_WIDGET_BACKGR OUND_TRANSPARENCY"


    /*resin widget design settings*/
    /*위젯 테마*/
    const val PREF_WIDGET_DETAIL_TIME_NOTATION = "PREF_DETAIL_TIME_NOTATION"
    const val PREF_WIDGET_RESIN_DATA_VISIBILITY = "PREF_WIDGET_RESIN_DATA_VISIBILITY"
    const val PREF_WIDGET_DAILY_COMMISSION_DATA_VISIBILITY = "PREF_WIDGET_DAILY_COMMISSION_DATA_VISIBILITY"
    const val PREF_WIDGET_WEEKLY_BOSS_DATA_VISIBILITY = "PREF_WIDGET_WEEKLY_BOSS_DATA_VISIBILITY"
    const val PREF_WIDGET_REALM_CURRENCY_DATA_VISIBILITY = "PREF_WIDGET_REALM_CURRENCY_DATA_VISIBILITY"
    const val PREF_WIDGET_EXPEDITION_DATA_VISIBILITY = "PREF_WIDGET_EXPEDITION_DATA_VISIBILITY"
    const val PREF_WIDGET_DETAIL_FONT_SIZE = "PREF_WIDGET_DETAIL_FONT_SIZE"
    /*bg trans*/

    const val PREF_CURRENT_RESIN = "PREF_CURRENT_RESIN"
    const val PREF_MAX_RESIN = "PREF_MAX_RESIN"
    const val PREF_RESIN_RECOVERY_TIME = "PREF_RESIN_RECOVERY_TIME"

    const val PREF_CURRENT_DAILY_COMMISSION = "PREF_CURRENT_DAILY_COMMISSION"
    const val PREF_MAX_DAILY_COMMISSION = "PREF_MAX_DAILY_COMMISSION"
    const val PREF_GET_DAILY_COMMISSION_REWARD = "PREF_GET_DAILY_COMMISSION_REWARD"

    const val PREF_CURRENT_WEEKLY_BOSS = "PREF_CURRENT_WEEKLY_BOSS"
    const val PREF_MAX_WEEKLY_BOSS = "PREF_MAX_WEEKLY_BOSS"

    const val PREF_CURRENT_HOME_COIN = "PREF_CURRENT_HOME_COIN"
    const val PREF_MAX_HOME_COIN = "PREF_MAX_HOME_COIN"
    const val PREF_HOME_COIN_RECOVERY_TIME = "PREF_HOME_COIN_RECOVERY_TIME"

    const val PREF_CURRENT_EXPEDITION = "PREF_CURRENT_EXPEDITION"
    const val PREF_MAX_EXPEDITION = "PREF_MAX_EXPEDITION"
    const val PREF_EXPEDITION_TIME = "PREF_EXPEDITION_TIME"

    const val PREF_RECENT_SYNC_TIME = "PREF_RECENT_SYNC_TIME"

    const val PREF_CHECKED_UPDATE_NOTE = "PREF_CHECKED_UPDATE_NOTE"

    const val PREF_LOCALE = "PREF_LOCALE"

    const val PREF_DEFAULT_REFRESH_PERIOD = 15L
    const val PREF_DEFAULT_WIDGET_BACKGROUND_TRANSPARENCY = 170
    const val PREF_DEFAULT_WIDGET_RESIN_FONT_SIZE = 30
    const val PREF_DEFAULT_WIDGET_DETAIL_FONT_SIZE = 12

    enum class Server(val pref: Int) {
        ASIA(PREF_SERVER_ASIA),
        USA(PREF_SERVER_USA),
        EUROPE(PREF_SERVER_EUROPE),
        CHT(PREF_SERVER_CHT),
    }
    const val PREF_SERVER_ASIA = 0
    const val PREF_SERVER_USA = 1
    const val PREF_SERVER_EUROPE = 2
    const val PREF_SERVER_CHT = 3

    enum class TimeNotation(val pref: Int) {
        REMAIN_TIME(PREF_TIME_NOTATION_REMAIN_TIME),
        FULL_CHARGE_TIME(PREF_TIME_NOTATION_FULL_CHARGE_TIME),
        DISABLE_TIME(PREF_TIME_NOTATION_DISABLE)
    }
    const val PREF_TIME_NOTATION_DEFAULT = -1
    const val PREF_TIME_NOTATION_REMAIN_TIME = 0
    const val PREF_TIME_NOTATION_FULL_CHARGE_TIME = 1
    const val PREF_TIME_NOTATION_DISABLE = 2

    enum class WidgetTheme(val pref: Int) {
        AUTOMATIC(PREF_WIDGET_THEME_AUTOMATIC),
        LIGHT(PREF_WIDGET_THEME_LIGHT),
        DARK(PREF_WIDGET_THEME_DARK)
    }
    const val PREF_WIDGET_THEME_AUTOMATIC = 0
    const val PREF_WIDGET_THEME_LIGHT = 1
    const val PREF_WIDGET_THEME_DARK = 2

    const val TALENT_AREA_MONDSTADT = 0
    const val TALENT_AREA_LIYUE = 1
    const val TALENT_AREA_INAZUMA = 2
    const val TALENT_AREA_SUMERU = 3

    const val TALENT_DATE_MONTHU = 0
    const val TALENT_DATE_TUEFRI = 1
    const val TALENT_DATE_WEDSAT = 2
    const val TALENT_DATE_ALL = 3

    const val TIME_TYPE_MAX = 0
    const val TIME_TYPE_DONE = 1

    enum class ResinImageVisibility(val pref: Int) {
        VISIBLE(PREF_WIDGET_RESIN_IMAGE_VISIBLE),
        INVISIBLE(PREF_WIDGET_RESIN_IMAGE_INVISIBLE)
    }
    const val PREF_WIDGET_RESIN_IMAGE_VISIBLE = 0
    const val PREF_WIDGET_RESIN_IMAGE_INVISIBLE = 1

    enum class Locale(val index: Int, val locale: String, val lang: String) {
        ENGLISH(PREF_LANG_ENG, "en", "en-us"),
        KOREAN(PREF_LANG_KOR, "ko", "ko-kr")
    }
    const val PREF_LANG_ENG = 0
    const val PREF_LANG_KOR = 1

    enum class NotiType {
        RESIN_EACH_40,
        RESIN_140,
        RESIN_CUSTOM,
        CHECK_IN_GENSHIN_SUCCESS,
        CHECK_IN_GENSHIN_ALREADY,
        CHECK_IN_GENSHIN_FAILED,
        CHECK_IN_GENSHIN_ACCOUNT_NOT_FOUND,
        CHECK_IN_HONKAI_3RD_SUCCESS,
        CHECK_IN_HONKAI_3RD_ALREADY,
        CHECK_IN_HONKAI_3RD_FAILED,
        CHECK_IN_HONKAI_3RD_ACCOUNT_NOT_FOUND,
        EXPEDITION_DONE,
        REALM_CURRENCY_FULL,
    }

    enum class WorkDataType(val key: String) {
        GENSHIN_CHECK_IN_RET_CODE("genshinCheckIn"),
        HONKAI_3RD_CHECK_IN_RET_CODE("honkai3rdCheckIn"),
    }


    /* ID */

    const val PUSH_CHANNEL_DEFAULT_ID = "DEFAULT"
    const val PUSH_CHANNEL_RESIN_NOTI_ID = "RESIN_NOTIFICATION"
    const val PUSH_CHANNEL_GENSHIN_CHECK_IN_NOTI_ID = "PUSH_CHANNEL_GENSHIN_CHECK_IN_NOTI_ID"
    const val PUSH_CHANNEL_HONKAI_3RD_CHECK_IN_NOTI_ID = "PUSH_CHANNEL_HONKAI_3RD_CHECK_IN_NOTI_ID"
    const val PUSH_CHANNEL_EXPEDITION_NOTI_ID = "EXPEDITION_NOTIFICATION"
    const val PUSH_CHANNEL_REALM_CURRENCY_NOTI_ID = "REALM_CURRENCY_NOTIFICATION"

    const val PUSH_CHANNEL_CHECK_IN_PROGRESS_NOTI_ID = "PUSH_CHANNEL_CHECK_IN_PROGRESS_NOTI_ID"
    const val PUSH_CHANNEL_CHECK_IN_PROGRESS_NOTI_NAME = "AUTO CHECK IN NOTIFICATION"



    /* TIMEZONE */

    const val CHINA_TIMEZONE = "Asia/Shanghai"
    const val KOREA_TIMEZONE = "Asia/Seoul"



    /* ACTION */

    const val ACTION_RESIN_WIDGET_REFRESH_UI = "danggai.app.resinwidget.refresh.resin.ui"
    const val ACTION_RESIN_WIDGET_REFRESH_DATA = "danggai.app.resinwidget.refresh.resin.data"
    const val ACTION_TALENT_WIDGET_REFRESH = "danggai.app.resinwidget.refresh.talent"
    const val ACTION_ON_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED"

    const val ACTION_APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"

    const val BACK_BUTTON_INTERVAL: Long = 1000


    /* CHARACTER ID */

    const val ID_KATE = 10000001
    const val ID_AYAKA = 10000002
    const val ID_JEAN = 10000003
    const val ID_AITHER = 10000005
    const val ID_LISA = 10000006
    const val ID_LUMINE = 10000007
    const val ID_BARBARA = 10000014
    const val ID_KEAYA = 10000015
    const val ID_DILUC = 10000016
    const val ID_RAZOR = 10000020
    const val ID_AMBER = 10000021
    const val ID_VENTI = 10000022
    const val ID_XIANGLING = 10000023
    const val ID_BEIDOU = 10000024
    const val ID_XINGQIU = 10000025
    const val ID_XIAO = 10000026
    const val ID_NINGGUANG = 10000027
    const val ID_KLEE = 10000029
    const val ID_ZHONGLI = 10000030
    const val ID_FISCHL = 10000031
    const val ID_BENNETT = 10000032
    const val ID_CHILDE = 10000033
    const val ID_NOELLE = 10000034
    const val ID_QIQI = 10000035
    const val ID_CHONGYUN = 10000036
    const val ID_GANYU = 10000037
    const val ID_ALBEDO = 10000038
    const val ID_DIONA = 10000039
    const val ID_MONA = 10000041
    const val ID_KEQING = 10000042
    const val ID_SUCROSE = 10000043
    const val ID_XINYAN = 10000044
    const val ID_ROSARIA = 10000045
    const val ID_HUTAO = 10000046
    const val ID_KAZUHA = 10000047
    const val ID_YANFEI = 10000048
    const val ID_YOIMIYA = 10000049
    const val ID_THOMA = 10000050
    const val ID_EULA = 10000051
    const val ID_RAIDEN = 10000052
    const val ID_SAYU = 10000053
    const val ID_KOKOMI = 10000054
    const val ID_GOROU = 10000055
    const val ID_SARA = 10000056
    const val ID_ITTO = 10000057
    const val ID_YAE = 10000058
    const val ID_HEIZOU = 10000059
    const val ID_YERAN = 10000060
    const val ID_ALOY = 10000062
    const val ID_SHENHE = 10000063
    const val ID_YUNJIN = 10000064
    const val ID_KUKI = 10000065
    const val ID_AYATO = 10000066
    const val ID_COLLEI = 10000067
    const val ID_DORI = 10000068
    const val ID_TIGHNARI = 10000069
    const val ID_NILOU = 10000070
    const val ID_CYNO = 10000071
    const val ID_CANDACE = 10000072
    const val ID_NAHIDA = 10000073
    const val ID_LAYLA = 10000074
}