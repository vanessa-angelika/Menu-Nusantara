<?php
require("koneksi.php");

$response = array();

// Cek apakah request method adalah POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Ambil parameter 'id' dari POST
    if (isset($_POST["id"])) {
        $id = $_POST["id"];

        // Cek apakah menu ada pada tabel detail_pesanan
        $checkQuery = "SELECT * FROM detail_pesanan WHERE ID_Menu = '$id'";
        $checkResult = mysqli_query($konek, $checkQuery);

        // Jika ada data pada detail_pesanan
        if (mysqli_num_rows($checkResult) > 0) {
            $response["success"] = false;
            $response["message"] = "Menu tidak dapat dihapus karena terdapat dalam pesanan.";
        } else {
            // Jika menu tidak ada pada detail_pesanan, lakukan penghapusan
            $deleteQuery = "DELETE FROM menu WHERE ID_Menu = '$id'";
            $deleteResult = mysqli_query($konek, $deleteQuery);

            // Cek apakah query berhasil dieksekusi
            if ($deleteResult) {
                $response["success"] = true;
                $response["message"] = "Menu berhasil dihapus";
            } else {
                $response["success"] = false;
                $response["message"] = "Gagal menghapus menu";
            }
        }
    } else {
        // Jika parameter 'id' tidak ditemukan dalam POST
        $response["success"] = false;
        $response["message"] = "ID Tidak Ditemukan";
    }
}
// Jika request method bukan POST
else {
    $response["success"] = false;
    $response["message"] = "Tidak Ada Post Data";
}

// Kirimkan response dalam bentuk JSON
echo json_encode($response);

// Tutup koneksi ke database
mysqli_close($konek);
?>