package com.example.sqlitepractice2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter (private val context: Context, productList: MutableList<Product>):
ArrayAdapter<Product>(context, R.layout.list_item, productList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val product = getItem(position)
var view = convertView
if (view==null){
    view=LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
    }
        val idText = view?.findViewById<TextView>(R.id.idTV)
        val nameText = view?.findViewById<TextView>(R.id.nameTV)
        val priceText = view?.findViewById<TextView>(R.id.priceTV)
        val weightText = view?.findViewById<TextView>(R.id.weightTV)
        idText?.text = "Id: ${product?.productId}"
        nameText?.text = "Имя: ${product?.productName}"
        weightText?.text = "Вес: ${product?.productWeight}"
        priceText?.text = "Цена: ${product?.productPrice}"

        return view!!
    }
}

