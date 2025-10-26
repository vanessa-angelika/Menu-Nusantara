<?php
include 'koneksi.php';

$response = array();  // Menyiapkan array respons

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nama_makanan = $_POST["Nama_Makanan"];
    $kategori = $_POST["Kategori"];
    $harga_awal = $_POST["Harga_Awal"];
    $persen_diskon = $_POST["Persen_Diskon"];
    $porsi = $_POST["Porsi"];
    $deskripsi = $_POST["Deskripsi"];
    $link_gambar = $_POST["Link_Gambar"];

    // Query untuk menyisipkan data
    $sql = "INSERT INTO menu (Nama_Makanan, Kategori, Harga_Awal, Persen_Diskon, Porsi, Deskripsi, Link_Gambar) 
            VALUES ('$nama_makanan', '$kategori', '$harga_awal', '$persen_diskon', '$porsi', '$deskripsi', '$link_gambar')";

    // Eksekusi query
    if ($konek->query($sql) === TRUE) {
        $response['success'] = true;
        $response['message'] = "Menu berhasil ditambahkan!";
    } else {
        $response['success'] = false;
        $response['message'] = "Error: " . $sql . "<br>" . $konek->error;
    }
} else {
    $response['success'] = false;
    $response['message'] = "Metode permintaan tidak valid!";
}

$konek->close();

header('Content-Type: application/json');
echo json_encode($response);
?>