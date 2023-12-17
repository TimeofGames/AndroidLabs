import android.provider.BaseColumns

object DBContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_KEY_ID = "id"
        const val COLUMN_NAME_LOGIN = "login"
        const val COLUMN_NAME_PASS = "pass"
    }
}
