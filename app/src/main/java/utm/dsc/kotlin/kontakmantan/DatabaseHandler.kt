package utm.dsc.kotlin.kontakmantan

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteQueryBuilder

import android.util.Log
import java.util.ArrayList

class DatabaseHandler : SQLiteOpenHelper {
    companion object {

        val Tag = "DatabaseHandler"
        val DBName = "ContactDB"
        val DBVersion = 1

        val tableName = "phoneTable"
        val ConID = "id"
        val FirstName = "fname"
        val LastName = "lname"
        val Number = "number"
        val Email = "email"
    }

    var context: Context? = null
    var sqlObj: SQLiteDatabase

    constructor(context: Context) : super(context, DBName, null, DBVersion) {
        this.context = context
        sqlObj = this.writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase?) {

        var sql1: String = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
                "(" + ConID + " INTEGER PRIMARY KEY," +
                FirstName + " TEXT, " + LastName + " TEXT, " + Email +
                " TEXT," + Number + " TEXT );"

        db!!.execSQL(sql1);
        Log.d(Tag, "Database created!!!")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("Drop table IF EXISTS " + tableName)
        onCreate(db)
    }

    fun AddContact(values: ContentValues): String {
        var Msg: String = "error";
        val ID = sqlObj!!.insert(tableName, "", values)
        if (ID > 0) {
            Msg = "ok"
        }
        return Msg
    }

    fun FetchContacts(keyword: String): ArrayList<ContactData> {
        var arraylist = ArrayList<ContactData>()
        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf("id", "fname", "lname", "email", "number")
        val rowSelArg = arrayOf(keyword)
        val cur = sqb.query(sqlObj, cols, "fname like ?", rowSelArg, null, null, "fname")
        if (cur.moveToFirst()) {
            do {
                val id = cur.getInt(cur.getColumnIndex("id"))
                val fname = cur.getString(cur.getColumnIndex("fname"))
                val lname = cur.getString(cur.getColumnIndex("lname"))
                val email = cur.getString(cur.getColumnIndex("email"))
                val number = cur.getString(cur.getColumnIndex("number"))

                arraylist.add(ContactData(id, fname, lname, email, number))

            } while (cur.moveToNext())
        }
        var count: Int = arraylist.size
        return arraylist
    }

    fun UpdateContact(values: ContentValues, id: Int): String {
        var selectionArs = arrayOf(id.toString())
        val i = sqlObj!!.update(tableName, values, "id=?", selectionArs)
        if (i > 0) {
            return "ok";
        } else {
            return "error";
        }
    }

    fun RemoveContact(id: Int): String {

        var selectionArs = arrayOf(id.toString())

        val i = sqlObj!!.delete(tableName, "id=?", selectionArs)
        if (i > 0) {
            return "ok";
        } else {

            return "error";
        }
    }
}