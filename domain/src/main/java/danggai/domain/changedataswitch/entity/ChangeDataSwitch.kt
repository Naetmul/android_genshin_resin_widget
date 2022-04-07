package danggai.domain.changedataswitch.entity

data class ChangeDataSwitch (
    val retcode: String,
    val message: String
) {
    companion object {
        val EMPTY = ChangeDataSwitch(
            retcode = "",
            message = ""
        )
    }
}