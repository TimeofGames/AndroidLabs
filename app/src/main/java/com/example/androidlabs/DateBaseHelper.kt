import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Users.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = "CREATE TABLE ${DBContract.UserEntry.TABLE_NAME} (" +
                "${DBContract.UserEntry.COLUMN_NAME_KEY_ID} INTEGER PRIMARY KEY," +
                "${DBContract.UserEntry.COLUMN_NAME_LOGIN} TEXT," +
                "${DBContract.UserEntry.COLUMN_NAME_PASS} TEXT)"
        db.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBContract.UserEntry.TABLE_NAME}")
        onCreate(db)
    }

    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.login)
        values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.pass)
        db.insert(DBContract.UserEntry.TABLE_NAME, null, values)
    }

    fun updateUser(user: User, oldUser: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.login)
        values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.pass)
        db.update(DBContract.UserEntry.TABLE_NAME, values, "login=?", arrayOf(oldUser.login.toString()))
    }

    @SuppressLint("Recycle")
    fun findUser(user:User): Boolean{
        return writableDatabase.rawQuery("SELECT * FROM ${DBContract.UserEntry.TABLE_NAME} WHERE login = '${user.login}' AND pass = '${user.pass}'", null).moveToFirst()
    }

    fun deleteAll() {
        writableDatabase.execSQL("DELETE FROM ${DBContract.UserEntry.TABLE_NAME}")
    }

    fun deleteUser(user: User) {
        writableDatabase.execSQL("DELETE FROM ${DBContract.UserEntry.TABLE_NAME} WHERE login = ${user.login}")
    }


    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val selectQuery = "SELECT * FROM ${DBContract.UserEntry.TABLE_NAME}"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_NAME_KEY_ID)
                ).toInt()
                val login = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_NAME_LOGIN)
                )
                val pass = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_NAME_PASS)
                )
                val user = User(id = id, login = login, pass = pass)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }


}
