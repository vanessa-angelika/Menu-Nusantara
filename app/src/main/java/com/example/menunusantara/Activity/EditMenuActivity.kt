package com.example.menunusantara.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

// Import disesuaikan ke package menunusantara
import com.example.menunusantara.R
import com.example.menunusantara.API.APIRequestData
import com.example.menunusantara.API.RetroServer
import com.example.menunusantara.Model.MenuModel
import com.example.menunusantara.Model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMenuActivity : AppCompatActivity() {
    private lateinit var nameMenuEditText: EditText
    private lateinit var descMenuEditText: EditText
    private lateinit var linkImageEditText: EditText
    private lateinit var categoriesMenuSpinner: Spinner
    private lateinit var sizeMenuSpinner: Spinner
    private lateinit var priceEditText: EditText
    private lateinit var discountEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var backBtn: Button

    private var menuId: Int = -1 // To store ID_Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_manage_menu)

        // Initialize views
        nameMenuEditText = findViewById(R.id.name_menu)
        descMenuEditText = findViewById(R.id.desc_menu)
        linkImageEditText = findViewById(R.id.link_img)
        priceEditText = findViewById(R.id.price_menu)
        categoriesMenuSpinner = findViewById(R.id.categories_menu)
        discountEditText = findViewById(R.id.discount)
        sizeMenuSpinner = findViewById(R.id.size_menu)
        submitButton = findViewById(R.id.submit)
        backBtn = findViewById(R.id.back)

        // Get the SHOW_SUBMIT extra from the Intent
        val showSubmit = intent.getBooleanExtra("SHOW_SUBMIT", true) // Akses intent yang benar

        // Set submit button visibility based on the value of showSubmit
        submitButton.visibility = if (showSubmit) {
            View.VISIBLE
        } else {
            View.GONE
        }

        // Get menuId from Intent
        menuId = intent.getIntExtra("MENU_ID", -1) // Akses intent yang benar

        // Set up spinners
        val categoriesAdapter = ArrayAdapter.createFromResource(
            this, R.array.categories_menu, android.R.layout.simple_spinner_item // Menggunakan android.R
        )
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Menggunakan android.R
        categoriesMenuSpinner.adapter = categoriesAdapter

        val sizeAdapter = ArrayAdapter.createFromResource(
            this, R.array.size_menu, android.R.layout.simple_spinner_item // Menggunakan android.R
        )
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Menggunakan android.R
        sizeMenuSpinner.adapter = sizeAdapter

        // Set color and font type
        val spinnerTextColor = ContextCompat.getColor(this, com.example.menunusantara.R.color.steel_teal)
        val spinnerTextTypeface = ResourcesCompat.getFont(this, com.example.menunusantara.R.font.poppinsmedium)

        categoriesMenuSpinner.viewTreeObserver.addOnGlobalLayoutListener {
            val view = categoriesMenuSpinner.selectedView
            if (view is TextView) {
                view.setTextColor(spinnerTextColor)
                view.typeface = spinnerTextTypeface
                view.textSize = 16f
            }
        }

        sizeMenuSpinner.viewTreeObserver.addOnGlobalLayoutListener {
            val view = sizeMenuSpinner.selectedView
            if (view is TextView) {
                view.setTextColor(spinnerTextColor)
                view.typeface = spinnerTextTypeface
                view.textSize = 16f
            }
        }

        // Set click listener for the back button
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // Diubah ke MainActivity
            startActivity(intent) // Akses Activity yang benar
            finish()
        }

        // Set click listener for the submit button
        submitButton.setOnClickListener {
            updateMenuData()
        }

        // Fetch existing menu data and populate the fields
        if (menuId != -1) {
            getMenuData()
        } else {
            Toast.makeText(this, "ID Menu tidak valid.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getMenuData() {
        val api = RetroServer.getClient().create(APIRequestData::class.java)

        // Gunakan parameter GET dengan ID
        val call = api.getMenuData(menuId) // menuId digunakan untuk mencari menu

        // Perbaiki Callback untuk ResponseModel<MenuModel>
        call.enqueue(object : Callback<ResponseModel<MenuModel>> {
            override fun onResponse(call: Call<ResponseModel<MenuModel>>, response: Response<ResponseModel<MenuModel>>) {
                if (response.isSuccessful && response.body() != null) {
                    val menu = response.body()?.data // Ambil menu pertama jika ada
                    if (menu != null) {
                        nameMenuEditText.setText(menu.getNamaMakanan())
                        descMenuEditText.setText(menu.getDeskripsi())
                        linkImageEditText.setText(menu.getLinkGambar())
                        priceEditText.setText(menu.getHargaAwal().toString())
                        discountEditText.setText(menu.getPersenDiskon().toString())

                        // Set spinner values
                        setSpinnerValue(categoriesMenuSpinner, menu.getKategori())
                        setSpinnerValue(sizeMenuSpinner, menu.getPorsi())
                    } else {
                        Toast.makeText(this@EditMenuActivity, "Data menu tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@EditMenuActivity, "Gagal mengambil data menu", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel<MenuModel>>, t: Throwable) {
                Log.e("API_Error", "Request failed: ${t.message}")
                Toast.makeText(this@EditMenuActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setSpinnerValue(spinner: Spinner, value: String) {
        val adapter = spinner.adapter as? ArrayAdapter<String>
        if (adapter != null) {
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i) == value) {
                    spinner.setSelection(i)
                    break
                }
            }
        }
    }

    private fun updateMenuData() {
        val nameMenu = nameMenuEditText.text.toString()
        val descMenu = descMenuEditText.text.toString()
        val linkImage = linkImageEditText.text.toString()
        val priceMenu = priceEditText.text.toString()
        val discountMenu = discountEditText.text.toString()
        val categoryMenu = categoriesMenuSpinner.selectedItem?.toString()
        val sizeMenu = sizeMenuSpinner.selectedItem?.toString()

        if (nameMenu.isEmpty() || descMenu.isEmpty() || linkImage.isEmpty() ||
            priceMenu.isEmpty() || discountMenu.isEmpty() ||
            categoryMenu.isNullOrEmpty() || sizeMenu.isNullOrEmpty()) {
            Toast.makeText(this, "Tolong isi semua yang ada pada kolom", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // KRUSIAL: Ubah ke Double
            val priceMenuDouble = priceMenu.toDouble()
            val discountMenuInt = discountMenu.toInt()

            val api = RetroServer.getClient().create(APIRequestData::class.java)
            // Menggunakan priceMenuDouble
            val call = api.updateMenu(menuId, nameMenu, categoryMenu, priceMenuDouble, discountMenuInt, sizeMenu, descMenu, linkImage)

            call.enqueue(object : Callback<ResponseModel<MenuModel>> {
                override fun onResponse(call: Call<ResponseModel<MenuModel>>, response: Response<ResponseModel<MenuModel>>) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        if (responseBody.isSuccess) {
                            Toast.makeText(this@EditMenuActivity, "Menu berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                            finish() // Close the activity and go back to previous activity
                        } else {
                            Toast.makeText(this@EditMenuActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@EditMenuActivity, "Gagal memperbarui menu: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseModel<MenuModel>>, t: Throwable) {
                    Toast.makeText(this@EditMenuActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Harga harus berupa angka desimal dan Diskon harus berupa angka bulat.", Toast.LENGTH_LONG).show()
        }
    }
}
