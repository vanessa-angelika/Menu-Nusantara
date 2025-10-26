<?php
include 'koneksi.php';

if (isset($_GET['category'])) {
    $category = $_GET['category'];

    if ($category == 'Semua') {
        $sql = "SELECT * FROM menu";
        $result = $konek->query($sql);
        error_log("Query Executed: $sql"); // Log query
    } else {
        $stmt = $konek->prepare("SELECT * FROM menu WHERE Kategori = ?");
        $stmt->bind_param("s", $category);
        $stmt->execute();
        $result = $stmt->get_result();
        error_log("Prepared Statement Executed with Category: $category"); // Log kategori
    }

    $menu = array();

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $menu[] = $row;
            error_log("Data Row: " . json_encode($row)); // Log setiap row data
        }
    } else {
        error_log("No data found for category: " . $category); // Log jika tidak ada data
    }

    echo json_encode($menu);

    if ($category != 'Semua') {
        $stmt->close();
    }
} else {
    echo json_encode([]);
    error_log("Category parameter is missing in the request."); // Log jika parameter category tidak ada
}

$konek->close();
?>