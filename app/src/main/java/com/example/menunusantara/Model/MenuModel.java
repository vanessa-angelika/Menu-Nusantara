package com.example.menunusantara.Model;

import com.google.gson.annotations.SerializedName;

public class MenuModel {
    @SerializedName("ID_Menu")
    private int id;
    @SerializedName("Nama_Makanan")
    private String namaMakanan;
    @SerializedName("Kategori")
    private String kategori;
    @SerializedName("Harga_Awal")
    private double hargaAwal;
    @SerializedName("Persen_Diskon")
    private int persenDiskon;
    @SerializedName("Porsi")
    private String porsi;
    @SerializedName("Deskripsi")
    private String deskripsi;
    @SerializedName("Link_Gambar")
    private String linkGambar;

    // Tambahkan field baru untuk jumlah menu
    private int jumlahMenu; // NEW FIELD ADDED

    public MenuModel(int id, String namaMakanan, String kategori, double hargaAwal, int persenDiskon, String porsi, String deskripsi, String linkGambar) {
        this.id = id;
        this.namaMakanan = namaMakanan;
        this.kategori = kategori;
        this.hargaAwal = hargaAwal;
        this.persenDiskon = persenDiskon;
        this.porsi = porsi;
        this.deskripsi = deskripsi;
        this.linkGambar = linkGambar;
    }

    // Getters and Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNamaMakanan() { return namaMakanan; }

    public void setNamaMakanan(String namaMakanan) { this.namaMakanan = namaMakanan; }

    public String getKategori() { return kategori; }

    public void setKategori(String kategori) { this.kategori = kategori; }

    public double getHargaAwal() { return hargaAwal; }

    public void setHargaAwal(double hargaAwal) { this.hargaAwal = hargaAwal; }

    public int getPersenDiskon() { return persenDiskon; }

    public void setPersenDiskon(int persenDiskon) { this.persenDiskon = persenDiskon; }

    public String getPorsi() { return porsi; }

    public void setPorsi(String porsi) { this.porsi = porsi; }

    public String getDeskripsi() { return deskripsi; }

    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getLinkGambar() { return linkGambar; }

    public void setLinkGambar(String linkGambar) { this.linkGambar = linkGambar; }

    // Getter dan Setter untuk field jumlahMenu
    public int getJumlahMenu() { return jumlahMenu; }

    public void setJumlahMenu(int jumlahMenu) { this.jumlahMenu = jumlahMenu; }
}