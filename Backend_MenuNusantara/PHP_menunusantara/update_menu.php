<?php
require("koneksi.php");

$response = array();

// Cek apakah request method adalah POST
if($_SERVER['REQUEST_METHOD'] == 'POST'){

    // Ambil parameter yang diperlukan (misalnya 'id')
    $idMenu = $_POST["ID_Menu"];
    $namaMakanan = $_POST["Nama_Makanan"];
    $kategori = $_POST["Kategori"];
    $hargaAwal = $_POST["Harga_Awal"];
    $persenDiskon = $_POST["Persen_Diskon"];
    $porsi = $_POST["Porsi"];
    $deskripsi = $_POST["Deskripsi"];
    $linkGambar = $_POST["Link_Gambar"];

    // Query untuk mengupdate data menu berdasarkan ID_Menu
    $perintah = "UPDATE menu SET 
                    Nama_Makanan = '$namaMakanan', 
                    Kategori = '$kategori', 
                    Harga_Awal = '$hargaAwal', 
                    Persen_Diskon = '$persenDiskon', 
                    Porsi = '$porsi', 
                    Deskripsi = '$deskripsi', 
                    Link_Gambar = '$linkGambar' 
                    WHERE ID_Menu = '$idMenu'";

    // Eksekusi query
    $eksekusi = mysqli_query($konek, $perintah);
    $cek = mysqli_affected_rows($konek);

    // Jika data berhasil diupdate
    if($cek > 0){
        $response["success"] = true;
        $response["message"] = "Menu berhasil diperbarui";
    }
    // Jika tidak ada perubahan
    else{
        $response["success"] = false;
        $response["message"] = "Tidak ada perubahan pada data";
    }
}
// Jika request method bukan POST
else{
    $response["success"] = false;
    $response["message"] = "Tidak Ada Post Data";
}

// Kirimkan response dalam bentuk JSON
echo json_encode($response);

// Tutup koneksi ke database
mysqli_close($konek);
?>