package com.uabc.edu.sqlitekotlin

import AdminSQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.ContentValues
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boton1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("codigo", et1.getText().toString())
            registro.put("descripcion", et2.getText().toString())
            registro.put("precio", et3.getText().toString())
            bd.insert("articulos", null, registro)
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show()
        }

        boton2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select descripcion,precio from articulos where codigo=${et1.text.toString()}", null)
            if (fila.moveToFirst()) {
                et2.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un artículo con dicho código",  Toast.LENGTH_SHORT).show()
            bd.close()
        }

        boton3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select codigo,precio from articulos where descripcion='${et2.text.toString()}'", null)
            if (fila.moveToFirst()) {
                et1.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show()
            bd.close()
        }

        boton4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("articulos", "codigo=${et1.text.toString()}", null)
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            if (cant == 1)
                Toast.makeText(this, "Se borró el artículo con dicho código", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT).show()
        }

        boton5.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("descripcion", et2.text.toString())
            registro.put("precio", et3.text.toString())
            val cant = bd.update("articulos", registro, "codigo=${et1.text.toString()}", null)
            bd.close()
            if (cant == 1)
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "no existe un artículo con el código ingresado", Toast.LENGTH_SHORT).show()
        }
    }

}
