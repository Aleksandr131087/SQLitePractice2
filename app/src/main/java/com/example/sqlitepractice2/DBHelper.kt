package com.example.sqlitepractice2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "shopping.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_WEIGHT = "weight"
        const val COLUMN_PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "$COLUMN_NAME TEXT, $COLUMN_WEIGHT REAL, $COLUMN_PRICE REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addProduct(product: Product) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, product.productId)
        values.put(COLUMN_NAME, product.productName)
        values.put(COLUMN_WEIGHT, product.productWeight)
        values.put(COLUMN_PRICE, product.productPrice)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun readProduct(): MutableList<Product> {
        val productList: MutableList<Product> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return productList
        }

        var productId: Int
        var productName: String
        var productWeight: Double
        var productPrice: Double
        if(cursor.moveToFirst()){
            do {
                productId=cursor.getInt(cursor.getColumnIndex("id"))
                productName=cursor.getString(cursor.getColumnIndex("name"))
                productPrice=cursor.getDouble(cursor.getColumnIndex("price"))
                productWeight=cursor.getDouble(cursor.getColumnIndex("weight"))
val product = Product(productId = productId, productName = productName, productPrice = productPrice.toString()
    .toString(), productWeight = productWeight.toString()
)
                productList.add(product)
            }while (cursor.moveToNext())
        }
        return productList
    }

    fun updateProduct(product: Product) {
        val db = this.writableDatabase
        val ContentValues = ContentValues()
        ContentValues.put(COLUMN_ID, product.productId)
        ContentValues.put(COLUMN_NAME, product.productName)
        ContentValues.put(COLUMN_WEIGHT, product.productWeight)
        ContentValues.put(COLUMN_PRICE, product.productPrice)
        db.update(TABLE_NAME, ContentValues, "id=" + product.productId, null)
        db.close()
    }

    fun deleteProduct(product: Product) {
        val db = this.writableDatabase
        val ContentValues = ContentValues()
        ContentValues.put(COLUMN_ID, product.productId)
       db.delete(TABLE_NAME, "id=" + product.productId, null)
        db.close()
    }


}

