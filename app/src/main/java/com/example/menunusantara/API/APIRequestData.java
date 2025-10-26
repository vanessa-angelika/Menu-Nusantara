package com.example.menunusantara.API;

import com.example.menunusantara.Model.MenuModel;
import com.example.menunusantara.Model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIRequestData {
    // Get menu
    @GET("fetch_menu.php")
    Call<List<MenuModel>> getMenu(@Query("category") String category);

    // Create menu
    @FormUrlEncoded
    @POST("create_menu.php")
    Call<ResponseModel<MenuModel>> createMenu(
            @Field("Nama_Makanan") String namaMakanan,
            @Field("Kategori") String kategori,
            @Field("Harga_Awal") double hargaAwal, // Diubah ke double
            @Field("Persen_Diskon") int persenDiskon,
            @Field("Porsi") String porsi,
            @Field("Deskripsi") String deskripsi,
            @Field("Link_Gambar") String linkGambar
    );

    // Get menu (Get by ID)
    @FormUrlEncoded
    @POST("get_menu.php")
    Call<ResponseModel<MenuModel>> getMenuData(@Field("id") int id);

    // Delete menu
    @FormUrlEncoded
    @POST("delete_menu.php")
    Call<ResponseModel> deleteMenu(@Field("id") int id);

    // Update menu
    @FormUrlEncoded
    @POST("update_menu.php")
    Call<ResponseModel<MenuModel>> updateMenu(
            @Field("ID_Menu") int idMenu,
            @Field("Nama_Makanan") String namaMakanan,
            @Field("Kategori") String kategori,
            @Field("Harga_Awal") double hargaAwal, // Diubah ke double
            @Field("Persen_Diskon") int persenDiskon,
            @Field("Porsi") String porsi,
            @Field("Deskripsi") String deskripsi,
            @Field("Link_Gambar") String linkGambar
    );
}
