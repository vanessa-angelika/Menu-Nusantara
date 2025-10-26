package com.example.menunusantara.Activity

import android.content.Intent
import android.os.Bundle
//import android.telecom.Call
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
import com.example.menunusantara.API.APIRequestData
import com.example.menunusantara.API.RetroServer
import com.example.menunusantara.Model.MenuModel
import com.example.menunusantara.Model.ResponseModel
import com.example.menunusantara.R
//import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMenuActivity : AppCompatActivity() { // Nama kelas diubah menjadi AddMenuActivity
    private lateinit var nameMenuEditText: EditText
    private lateinit var descMenuEditText: EditText
    private lateinit var linkImageEditText: EditText
    private lateinit var categoriesMenuSpinner: Spinner
    private lateinit var sizeMenuSpinner: Spinner
    private lateinit var priceEditText: EditText
    private lateinit var discountEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var backBtn: Button

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

        // Di halaman Add, tombol submit selalu terlihat.
        // Hanya disembunyikan di EditMenuActivity jika user tidak memiliki hak akses.
        submitButton.visibility = View.VISIBLE

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set up spinners. Menggunakan android.R.layout.simple_spinner_item
        val categoriesAdapter = ArrayAdapter.createFromResource(
            this, R.array.categories_menu, android.R.layout.simple_spinner_item
        )
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriesMenuSpinner.adapter = categoriesAdapter

        val sizeAdapter = ArrayAdapter.createFromResource(
            this, R.array.size_menu, android.R.layout.simple_spinner_item
        )
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizeMenuSpinner.adapter = sizeAdapter

        // Set color and font type
        val spinnerTextColor = ContextCompat.getColor(this, com.example.menunusantara.R.color.steel_teal)
        val spinnerTextTypeface = ResourcesCompat.getFont(this, com.example.menunusantara.R.font.poppinsmedium)

        // Logika untuk mengatur tampilan Spinner
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

        // Set click listener
        submitButton.setOnClickListener {
            submitMenuData()
        }
    }

    private fun submitMenuData() {
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
            val call = api.createMenu(nameMenu, categoryMenu, priceMenuDouble, discountMenuInt, sizeMenu, descMenu, linkImage)

            call.enqueue(object : Callback<ResponseModel<MenuModel>> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseModel<MenuModel>>,
                    response: Response<ResponseModel<MenuModel>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        if (responseBody.isSuccess) {
                            Toast.makeText(this@AddMenuActivity, "Menu berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                            clearFields()
                            // Kembali ke MainActivity setelah berhasil
                            val intent = Intent(this@AddMenuActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@AddMenuActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@AddMenuActivity, "Gagal menambahkan menu: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<ResponseModel<MenuModel>>,
                    t: Throwable
                ) {
                    Toast.makeText(this@AddMenuActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Harga harus berupa angka desimal dan Diskon harus berupa angka bulat.", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        nameMenuEditText.text.clear()
        descMenuEditText.text.clear()
        linkImageEditText.text.clear()
        priceEditText.text.clear()
        discountEditText.text.clear()
        categoriesMenuSpinner.setSelection(0)
        sizeMenuSpinner.setSelection(0)
    }
}
