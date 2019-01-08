package kordoghli.firas.troc.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "Favoris"
val TABLE_NAME = "Favoris"
val COL_ID = "id"
val COL_ID_SERVICE = "idService"
val COL_TITRE = "titre"
val COL_DESCRIPTION = "description"
val COL_CATEGORIE = "categorie"
val COL_TYPE = "type"
val COL_IDUSER = "idUser"
val COL_ID_USER_FAVORIS = "idUserFavoris"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ID_SERVICE + " INTEGER," +
                COL_TITRE + " VARCHAR(256)," +
                COL_DESCRIPTION + " VARCHAR(256)," +
                COL_CATEGORIE + " VARCHAR(256)," +
                COL_TYPE + " VARCHAR(256)," +
                COL_IDUSER + " VARCHAR(256)," +
                COL_ID_USER_FAVORIS + " INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertData(service: ResponseClasses.Service, idUser: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID_SERVICE, service.id)
        cv.put(COL_TITRE, service.titre)
        cv.put(COL_DESCRIPTION, service.description)
        cv.put(COL_CATEGORIE, service.categorie)
        cv.put(COL_TYPE, service.type)
        cv.put(COL_IDUSER, service.idUser)
        cv.put(COL_ID_USER_FAVORIS, idUser)
        var result = db.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context, "Fail SQLIte", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Favoris ajouter", Toast.LENGTH_LONG).show()
        }
    }

    fun readData(idUser: String): ArrayList<ResponseClasses.Service> {
        var list: java.util.ArrayList<ResponseClasses.Service> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID_USER_FAVORIS + " = " + idUser + ";"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val service = ResponseClasses.Service(
                    result.getString(result.getColumnIndex(COL_ID_SERVICE)).toInt(),
                    result.getString(result.getColumnIndex(COL_TITRE)),
                    result.getString(result.getColumnIndex(COL_DESCRIPTION)),
                    result.getString(result.getColumnIndex(COL_CATEGORIE)),
                    result.getString(result.getColumnIndex(COL_TYPE)),
                    result.getString(result.getColumnIndex(COL_IDUSER)),
                    9.9F,
                    9.9F
                )
                list.add(service)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

}