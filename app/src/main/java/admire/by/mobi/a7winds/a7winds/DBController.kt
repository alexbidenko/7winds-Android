package admire.by.mobi.a7winds.a7winds

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.json.JSONArray
import org.json.JSONObject

class DBController(context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var db: SQLiteDatabase? = null

    override fun onCreate(db:SQLiteDatabase) {
    }

    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int) {
        onCreate(db)
    }

    fun startDB(sqlite: SQLiteDatabase) {
        this.db = sqlite
    }

    fun setDBPersons(data: JSONArray) {
        db!!.execSQL("drop table if exists $TABLE_PERSONS")
        db!!.execSQL(
            "create table $TABLE_PERSONS (" +
                    "$KEY_APP_ID integer primary key, " +
                    "$KEY_ID integer, " +
                    "$KEY_NAME text, " +
                    "$KEY_WHO text, " +
                    "$KEY_PROFILE text, " +
                    "$KEY_TEL text, " +
                    "$KEY_EMAIL text, " +
                    "$KEY_AVATAR text, " +
                    "$KEY_STATUS integer" +
                    ");"
        )
        val contentValues = ContentValues()

        for(i in 0 until data.length()) {
            val cash = data.getJSONObject(i)
            contentValues.put(KEY_ID, cash.getInt("id"))
            contentValues.put(KEY_NAME, cash.getString("name"))
            contentValues.put(KEY_WHO, cash.getString("who"))
            contentValues.put(KEY_PROFILE, cash.getJSONArray("profile").toString())
            contentValues.put(KEY_TEL, cash.getString("tel"))
            contentValues.put(KEY_EMAIL, cash.getString("email"))
            contentValues.put(KEY_AVATAR, cash.getString("avatar"))
            contentValues.put(KEY_STATUS, cash.getInt("status"))
            db!!.insert(TABLE_PERSONS, null, contentValues)
            contentValues.clear()
        }
    }

    fun getDBPersons(): JSONArray {
        val cursor = db!!.query(TABLE_PERSONS, null, null, null, null, null, null)
        val ans = JSONArray()

        if(cursor.moveToFirst()) {
            do {
                val data = JSONObject()
                data.put("id", cursor.getInt(cursor.getColumnIndex(KEY_ID)))
                data.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)))
                data.put("who", cursor.getString(cursor.getColumnIndex(KEY_WHO)))
                data.put("profile", JSONArray(cursor.getString(cursor.getColumnIndex(KEY_PROFILE))))
                data.put("tel", cursor.getString(cursor.getColumnIndex(KEY_TEL)))
                data.put("email", cursor.getString(cursor.getColumnIndex(KEY_EMAIL)))
                data.put("avatar", cursor.getString(cursor.getColumnIndex(KEY_AVATAR)))
                data.put("status", cursor.getInt(cursor.getColumnIndex(KEY_STATUS)))
                ans.put(data)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return ans
    }

    fun setDBProjects(data: JSONArray) {
        db!!.execSQL("drop table if exists $TABLE_PROJECTS")
        db!!.execSQL(
            "create table $TABLE_PROJECTS (" +
                    "$KEY_APP_ID integer primary key, " +
                    "$KEY_ID integer, " +
                    "$KEY_TITLE text, " +
                    "$KEY_WORKERS text, " +
                    "$KEY_PLATFORM text, " +
                    "$KEY_START integer, " +
                    "$KEY_END integer, " +
                    "$KEY_AVATAR text" +
                    ");"
        )
        val contentValues = ContentValues()

        for(i in 0 until data.length()) {
            val cash = data.getJSONObject(i)
            contentValues.put(KEY_ID, cash.getInt("id"))
            contentValues.put(KEY_TITLE, cash.getString("title"))
            contentValues.put(KEY_WORKERS, cash.getJSONArray("workers").toString())
            contentValues.put(KEY_PLATFORM, cash.getJSONArray("platform").toString())
            contentValues.put(KEY_START, cash.getLong("start"))
            contentValues.put(KEY_END, cash.getLong("end"))
            contentValues.put(KEY_AVATAR, cash.getString("avatar"))
            db!!.insert(TABLE_PROJECTS, null, contentValues)
            contentValues.clear()
        }
    }

    fun getDBProjects(): JSONArray {
        val cursor = db!!.query(TABLE_PROJECTS, null, null, null, null, null, null)
        val ans = JSONArray()

        if(cursor.moveToFirst()) {
            do {
                val data = JSONObject()
                data.put("id", cursor.getInt(cursor.getColumnIndex(KEY_ID)))
                data.put("title", cursor.getString(cursor.getColumnIndex(KEY_TITLE)))
                data.put("workers", JSONArray(cursor.getString(cursor.getColumnIndex(KEY_WORKERS))))
                data.put("platform", JSONArray(cursor.getString(cursor.getColumnIndex(KEY_PLATFORM))))
                data.put("start", cursor.getLong(cursor.getColumnIndex(KEY_START)))
                data.put("end", cursor.getLong(cursor.getColumnIndex(KEY_END)))
                data.put("avatar", cursor.getString(cursor.getColumnIndex(KEY_AVATAR)))
                ans.put(data)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return ans
    }

    fun setDBPlans(data: JSONArray) {
        db!!.execSQL("drop table if exists $TABLE_PLANS")
        db!!.execSQL(
            "create table $TABLE_PLANS (" +
                    "$KEY_APP_ID integer primary key, " +
                    "$KEY_ID integer, " +
                    "$KEY_PROJECT_ID integer, " +
                    "$KEY_TIME integer" +
                    ");"
        )
        val contentValues = ContentValues()

        for(i in 0 until data.length()) {
            val cash = data.getJSONObject(i)
            contentValues.put(KEY_ID, cash.getInt("id"))
            contentValues.put(KEY_PROJECT_ID, cash.getInt("project_id"))
            contentValues.put(KEY_TIME, cash.getLong("time"))
            db!!.insert(TABLE_PLANS, null, contentValues)
            contentValues.clear()
        }
    }

    fun getDBPlans(): JSONArray {
        val cursor = db!!.query(TABLE_PLANS, null, null, null, null, null, null)
        val ans = JSONArray()

        if(cursor.moveToFirst()) {
            do {
                val data = JSONObject()
                data.put("id", cursor.getInt(cursor.getColumnIndex(KEY_ID)))
                data.put("project_id", cursor.getInt(cursor.getColumnIndex(KEY_PROJECT_ID)))
                data.put("time", cursor.getLong(cursor.getColumnIndex(KEY_TIME)))
                ans.put(data)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return ans
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "AppDataController"
        val TABLE_PERSONS = "table_persons"
        val TABLE_PROJECTS = "table_projects"
        val TABLE_PLANS = "table_plans"

        val KEY_APP_ID = "app_id"
        val KEY_ID = "id"

        val KEY_NAME = "name"
        val KEY_WHO = "who"
        val KEY_PROFILE = "profile"
        val KEY_TEL = "tel"
        val KEY_EMAIL = "email"
        val KEY_AVATAR = "avatar"
        val KEY_STATUS = "status"

        val KEY_TITLE = "title"
        val KEY_WORKERS = "workers"
        val KEY_PLATFORM = "platform"
        val KEY_START = "start"
        val KEY_END = "end"

        val KEY_PROJECT_ID = "project_id"
        val KEY_TIME = "time"
    }
}