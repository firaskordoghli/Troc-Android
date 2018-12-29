package kordoghli.firas.troc.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.security.AccessControlContext

val DATABASE_NAME = "Favoris"
val TABLE_NAME = "Favoris"
val COL_ID = "id"
val COL_ID_SERVICE = "idService"
val COL_TITRE = "titre"
val COL_DESCRIPTION = "description"
val COL_CATEGORIE = "categorie"
val COL_TYPE = "type"
val COL_IDUSER = "idUser"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +"INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ID_SERVICE +"INTEGER," +
                COL_TITRE +" VARCHAR(256)," +
                COL_DESCRIPTION +" VARCHAR(256)," +
                COL_CATEGORIE +" VARCHAR(256)," +
                COL_TYPE +" VARCHAR(256)," +
                COL_IDUSER +" VARCHAR(256),"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(service: ResponseClasses.Service){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID_SERVICE,service.id)
        cv.put(COL_TITRE,service.titre)
        cv.put(COL_DESCRIPTION,service.description)
        cv.put(COL_CATEGORIE,service.categorie)
        cv.put(COL_TYPE,service.type)
        cv.put(COL_IDUSER,service.idUser)
        var result = db.insert(TABLE_NAME,null,cv)
        if (result == -1.toLong() ){
            Toast.makeText(context,"Fail SQLIte",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context,"succes SQLIte",Toast.LENGTH_LONG).show()
        }
    }

}