package com.example.sqlitepractice2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {
var products: MutableList<Product> = mutableListOf()
    var listAdapter: MyListAdapter? = null
    val dataBase=DBHelper (this)
private lateinit var ID :EditText
    private lateinit var toolbar: Toolbar
    private lateinit var dbHelper: DBHelper
    private lateinit var ListViewLW: ListView
    private lateinit var productNameET: EditText
    private lateinit var productWeightET: EditText
    private lateinit var productPriceET: EditText
    private lateinit var saveButton: Button
    private lateinit var changeButton:Button
private lateinit var deleteButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        saveButton.setOnClickListener {
           saveRecord()
            }
        }

    override fun onResume() {
        super.onResume()
        changeButton.setOnClickListener {
            updateRecord()
        }

        deleteButton.setOnClickListener {
            deleteRecord()
        }
    }

    private fun deleteRecord() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val chooseDeleteId = dialogView.findViewById<EditText>(R.id.deleteIdET)
        dialogBuilder.setTitle("Удадить данные")
        dialogBuilder.setMessage("введите индентификатор")
        dialogBuilder.setPositiveButton("удалить") { _, _ ->
            val deleteId = chooseDeleteId.text.toString()
            if (deleteId.trim()!=""){
val product = Product(Integer.parseInt(deleteId), "", "", "")
                dataBase.deleteProduct(product)
                viewDataAdapter()
                Toast.makeText(applicationContext, "Запись удалена", Toast.LENGTH_LONG).show()
            }
        }
        dialogBuilder.setNegativeButton("Отмена"){_,_->}.create().show()
    }

    private fun init() {
        ID = findViewById(R.id.productIdET)
        changeButton = findViewById(R.id.changeButton)
        deleteButton = findViewById(R.id.deleteButton)
        dbHelper = DBHelper(this)
        ListViewLW = findViewById(R.id.ListViewLW)
        productNameET = findViewById(R.id.productNameET)
        productWeightET = findViewById(R.id.productWeightET)
        productPriceET = findViewById(R.id.productPriceET)
        saveButton = findViewById(R.id.saveButton)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Чек покупок"
        viewDataAdapter()
    }

    private fun viewDataAdapter(){
      products = dataBase.readProduct()
        listAdapter = MyListAdapter(this, products)
        ListViewLW.adapter = listAdapter
        listAdapter?.notifyDataSetChanged()
            }


    private fun saveRecord() {
        val id =ID.text.toString()
val name = productNameET.text.toString()
        val weight = productWeightET.text.toString()
        val price = productPriceET.text.toString()
if(id.trim()!=""&& name.trim()!=""&&weight.trim()!="" && price.trim()!=""){
    val product = Product(Integer.parseInt(id), name, weight, price)
    products.add(product)
    dataBase.addProduct(product)
    Toast.makeText(applicationContext, "Запись сохранена", Toast.LENGTH_LONG).show()
    productNameET.text.clear()
    productPriceET.text.clear()
    productWeightET.text.clear()
    ID.text.clear()
viewDataAdapter()

}

 }
        private fun updateRecord(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)
        val editId = dialogView.findViewById<EditText>(R.id.updateIdET)
        val ediName = dialogView.findViewById<EditText>(R.id.updateNameET)
        val editPrice = dialogView.findViewById<EditText>(R.id.updatePriceET)
        val editWeight=dialogView.findViewById<EditText>(R.id.updateWeightET)
        dialogBuilder.setTitle("Обновить данные")
        dialogBuilder.setMessage("введите данные ниже:")
        dialogBuilder.setPositiveButton("обновить"){_,_->
            val updateID = editId.text.toString()
            val updateName = ediName.text.toString()
            val updatePrice = editPrice.text.toString()
            val updateWeight = editWeight.text.toString()
            if (updateWeight.trim() != "" && updatePrice.trim() != "" && updateName.trim() !="" && updateID.trim()!=""){
val product = Product(Integer.parseInt(updateID), updateName, updatePrice, updateWeight)
                dataBase.updateProduct(product)
                viewDataAdapter()
                Toast.makeText(applicationContext, "Данные обновлены", Toast.LENGTH_LONG).show()
            }
        }
            dialogBuilder.setNegativeButton("Отмена"){dialog, which ->
            }
            dialogBuilder.create().show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}