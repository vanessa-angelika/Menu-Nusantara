<?php
require("koneksi.php");

$response = array();

// Cek apakah request method adalah POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Ambil parameter 'id' dari POST
    if (isset($_POST["id"])) {
        $id = $_POST["id"];

        // Query untuk mengambil data berdasarkan ID
        $perintah = "SELECT ID_Menu, Nama_Makanan, Kategori, Harga_Awal, Persen_Diskon, Porsi, Deskripsi, Link_Gambar 
                        FROM menu WHERE ID_Menu = '$id'";
        $eksekusi = mysqli_query($konek, $perintah);
        $cek = mysqli_affected_rows($konek);

        // Jika data ditemukan
        if ($cek > 0) {
            $response["success"] = true;
            $response["message"] = "Data Ditemukan";
            $response["data"] = null;  // Set data to null initially

            // Ambil data dari query dan simpan dalam array (langsung assign)
            $ambil = mysqli_fetch_object($eksekusi);
            $response["data"] = array(
                "ID_Menu" => $ambil->ID_Menu,
                "Nama_Makanan" => $ambil->Nama_Makanan,
                "Kategori" => $ambil->Kategori,
                "Harga_Awal" => $ambil->Harga_Awal,
                "Persen_Diskon" => $ambil->Persen_Diskon,
                "Porsi" => $ambil->Porsi,
                "Deskripsi" => $ambil->Deskripsi,
                "Link_Gambar" => $ambil->Link_Gambar
            );
        } else {
            // Jika data tidak ditemukan
            $response["success"] = false;
            $response["message"] = "Data Tidak Tersedia";
            $response["data"] = null;  // Set data to null
        }
    } else {
        // Jika parameter 'id' tidak ditemukan dalam POST
        $response["success"] = false;
        $response["message"] = "ID Tidak Ditemukan";
        $response["data"] = null;
    }
}
// Jika request method bukan POST
else {
    $response["success"] = false;
    $response["message"] = "Tidak Ada Post Data";
    $response["data"] = null;
}

// Kirimkan response dalam bentuk JSON
echo json_encode($response);

// Tutup koneksi ke database
mysqli_close($konek);
?>