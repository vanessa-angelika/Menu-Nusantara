package com.example.menunusantara.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
// Import disesuaikan ke package menunusantara
import com.example.menunusantara.API.APIRequestData
import com.example.menunusantara.API.RetroServer
import com.example.menunusantara.Adapter.MenuAdapter // Menggunakan MenuAdapter sesuai Canvas
import com.example.menunusantara.Model.MenuModel
import com.example.menunusantara.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() { // Nama kelas diubah menjadi MainActivity
    private lateinit var rvMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter // Menggunakan MenuAdapter
    private var menuList: List<MenuModel> = listOf()

    private lateinit var btnAll: TextView
    private lateinit var btnFood: TextView
    private lateinit var btnDrink: TextView
    private lateinit var btnCake: TextView

    private lateinit var btnBack: Button
    private lateinit var btnTambah: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_data_menu)

        rvMenu = findViewById(R.id.list_menu)
        rvMenu.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        menuAdapter = MenuAdapter(menuList) // Menggunakan MenuAdapter
        rvMenu.adapter = menuAdapter

        btnAll = findViewById(R.id.allkategori)
        btnFood = findViewById(R.id.makanankategori)
        btnDrink = findViewById(R.id.minumankategori)
        btnCake = findViewById(R.id.kuekategori)

        btnTambah = findViewById(R.id.banner2)

        btnTambah.setOnClickListener {
            val intent = Intent(this, AddMenuActivity::class.java)
            startActivity(intent)
        }

        btnAll.setOnClickListener {
            fetchMenuData("Semua")
            selectButton(it)
        }
        btnFood.setOnClickListener {
            fetchMenuData("Makanan")
            selectButton(it)
        }
        btnDrink.setOnClickListener {
            fetchMenuData("Minuman")
            selectButton(it)
        }
        btnCake.setOnClickListener {
            fetchMenuData("Kue")
            selectButton(it)
        }

        // Load the default menu for "Semua" category and set the default button selection
        fetchMenuData("Semua")
        selectButton(btnAll)
    }

    override fun onResume() {
        super.onResume()
        fetchMenuData("Semua")
    }

    private fun fetchMenuData(category: String) {
        val retrofit = RetroServer.getClient()
        val apiRequestData = retrofit.create(APIRequestData::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = apiRequestData.getMenu(category)
                call.enqueue(object : Callback<List<MenuModel>> {
                    override fun onResponse(
                        call: Call<List<MenuModel>>,
                        response: Response<List<MenuModel>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            menuList = response.body()!!
                            runOnUiThread {
                                menuAdapter.updateMenuList(menuList)
                            }
                        } else {
                            Log.e("HomeUser", "Error: ${response.errorBody()?.string()}")
                            Toast.makeText(
                                this@MainActivity, // Referensi konteks disesuaikan
                                "Gagal mendapatkan data menu!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<List<MenuModel>>, t: Throwable) {
                        Log.e("HomeUser", "Network Error: ${t.message}")
                        Toast.makeText(
                            this@MainActivity, // Referensi konteks disesuaikan
                            "Gagal Terhubung ke Server!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            } catch (e: Exception) {
                runOnUiThread {
                    Log.e("MenuData", "Exception: ${e.message}") // Logging exception
                    Toast.makeText(this@MainActivity, "Gagal Terhubung ke Server!", Toast.LENGTH_SHORT) // Referensi konteks disesuaikan
                        .show()
                }
            }
        }
    }

    private fun selectButton(selectedButton: View) {
        // Reset all buttons to default state
        btnAll.setBackgroundResource(R.drawable.btn_circle_three)
        btnAll.setTextColor(resources.getColor(R.color.steel_teal))
        btnFood.setBackgroundResource(R.drawable.btn_circle_three)
        btnFood.setTextColor(resources.getColor(R.color.steel_teal))
        btnDrink.setBackgroundResource(R.drawable.btn_circle_three)
        btnDrink.setTextColor(resources.getColor(R.color.steel_teal))
        btnCake.setBackgroundResource(R.drawable.btn_circle_three)
        btnCake.setTextColor(resources.getColor(R.color.steel_teal))
        // Set the selected button to selected state
        when (selectedButton.id) {
            R.id.allkategori -> {
                btnAll.setBackgroundResource(R.drawable.vector_categories)
                btnAll.setTextColor(resources.getColor(R.color.white))
            }
            R.id.makanankategori -> {
                btnFood.setBackgroundResource(R.drawable.vector_categories)
                btnFood.setTextColor(resources.getColor(R.color.white))
            }
            R.id.minumankategori -> {
                btnDrink.setBackgroundResource(R.drawable.vector_categories)
                btnDrink.setTextColor(resources.getColor(R.color.white))
            }
            R.id.kuekategori -> {
                btnCake.setBackgroundResource(R.drawable.vector_categories)
                btnCake.setTextColor(resources.getColor(R.color.white))
            }
        }
    }
}
