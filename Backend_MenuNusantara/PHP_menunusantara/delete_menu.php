<?php
require("koneksi.php");

$response = array();

// Cek apakah request method adalah POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Ambil parameter 'id' dari POST
    if (isset($_POST["id"])) {
        $id = $_POST["id"];

        // Lakukan penghapusan data langsung
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
    } else {
        $response["success"] = false;
        $response["message"] = "ID Tidak Ditemukan";
    }
} else {
    $response["success"] = false;
    $response["message"] = "Tidak Ada Post Data";
}

// Kirimkan response dalam bentuk JSON
echo json_encode($response);

// Tutup koneksi ke database
mysqli_close($konek);
?>
