package com.example.menunusantara.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.menunusantara.API.APIRequestData;
import com.example.menunusantara.API.RetroServer;
import com.example.menunusantara.Activity.EditMenuActivity;
import com.example.menunusantara.Model.MenuModel;
import com.example.menunusantara.Model.ResponseModel;
import com.example.menunusantara.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<MenuModel> menuList;
    private Context context;

    public MenuAdapter(List<MenuModel> menuList) {
        this.menuList = menuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        // Asumsi R.layout.single_menu_admin adalah layout dari single_menu.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.bind(menuList.get(position));

        // Click handler for box menu (tvBoxCard)
        holder.tvBoxCard.setOnClickListener(v -> {
            // Navigasi ke EditMenuActivity dan sembunyikan tombol submit
            navigateToEditMenu(menuList.get(position),false); // Sembunyikan submit (Mode Lihat)
        });

        // Click handler for edit button (ivEdit)
        holder.ivEdit.setOnClickListener(v -> {
            // Navigasi ke EditMenuActivity dan biarkan tombol submit terlihat
            navigateToEditMenu(menuList.get(position), true); // Tampilkan submit (Mode Edit)
        });

        holder.ivDelete.setOnClickListener(v -> {
            MenuModel menu = menuList.get(position);
            deleteMenu(menu.getId(), position);
        });
    }

    private void navigateToEditMenu(MenuModel menu, boolean showSubmit) {
        // Asumsi EditMenuActivity adalah ManageMenuActivity yang dibahas sebelumnya
        Intent intent = new Intent(context, EditMenuActivity.class);
        intent.putExtra("SHOW_SUBMIT", showSubmit);
        intent.putExtra("MENU_ID", menu.getId());
        intent.putExtra("MENU_NAME", menu.getNamaMakanan());
        intent.putExtra("MENU_KATEGORI", menu.getKategori());
        intent.putExtra("MENU_DESC", menu.getDeskripsi());
        intent.putExtra("MENU_TYPE", menu.getPorsi());
        intent.putExtra("MENU_DISCOUNT", menu.getPersenDiskon());
        intent.putExtra("MENU_PRICE", menu.getHargaAwal());
        intent.putExtra("MENU_IMAGE", menu.getLinkGambar());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void updateMenuList(List<MenuModel> newMenuList) {
        menuList = newMenuList;
        notifyDataSetChanged();
    }

    private void deleteMenu(int menuId, int position) {
        // Menampilkan dialog konfirmasi
        new AlertDialog.Builder(context)
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin menghapus menu ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Jika pengguna klik "Ya", lanjutkan untuk menghapus menu
                        performDelete(menuId, position);
                    }
                })
                .setNegativeButton("Tidak", null) // Jika klik "Tidak", dialog akan ditutup
                .show();
    }

    private void performDelete(int menuId, int position) {
        // Buat panggilan API untuk menghapus menu
        APIRequestData api = RetroServer.getClient().create(APIRequestData.class);

        // Panggil method deleteMenu dari API
        Call<ResponseModel> call = api.deleteMenu(menuId);

        // Menjalankan request secara asynchronous
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                // Cek apakah request berhasil
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Response success: " + response.body().getMessage());
                    if (response.body().isSuccess()) {
                        // Jika berhasil, hapus menu dari RecyclerView
                        menuList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Jika gagal karena menu ada dalam pesanan
                        // Catatan: Ini mengasumsikan server mengirim pesan spesifik ini
                        if (response.body().getMessage().equalsIgnoreCase("Menu tidak dapat dihapus karena terdapat dalam pesanan.")) {
                            Toast.makeText(context, "Menu tidak dapat dihapus karena terdapat dalam pesanan.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.e("API Error", "Error response: " + response.errorBody());
                    // Tampilkan pesan error jika respon tidak berhasil
                    Toast.makeText(context, "Gagal menghapus menu. Silakan coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                // Tangani kegagalan jika terjadi masalah dengan jaringan
                Log.e("API Failure", "Error: " + t.getMessage());
                Toast.makeText(context, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        private  final ConstraintLayout tvBoxCard;
        private final TextView tvMenuName;
        private final TextView tvKategori;
        private final TextView tvMenuPrice;
        private final TextView tvDiscountedPrice;
        private final TextView tvPersenDiskon;
        private final TextView tvPorsi;
        private final TextView tvDeskripsi;
        private final ImageView ivMenuImage;
        private final ImageView ivEdit;
        private final ImageView ivDelete;

        public MenuViewHolder(View itemView) {
            super(itemView);
            tvBoxCard = itemView.findViewById(R.id.card2);
            tvMenuName = itemView.findViewById(R.id.judulmenu);
            tvKategori = itemView.findViewById(R.id.kategorimenu);
            tvMenuPrice = itemView.findViewById(R.id.hargadiskon);
            tvDiscountedPrice = itemView.findViewById(R.id.hargamenu);
            tvPersenDiskon = itemView.findViewById(R.id.discount);
            tvPorsi = itemView.findViewById(R.id.porsimenu);
            tvDeskripsi = itemView.findViewById(R.id.deskripsimenu);
            ivMenuImage = itemView.findViewById(R.id.imagemenu);
            ivEdit = itemView.findViewById(R.id.edit);
            ivDelete = itemView.findViewById(R.id.delete);
        }

        public void bind(MenuModel menu) {
            tvMenuName.setText(menu.getNamaMakanan());
            tvKategori.setText(menu.getKategori());
            tvDeskripsi.setText(menu.getDeskripsi());
            tvPorsi.setText(menu.getPorsi());
            tvPersenDiskon.setText(menu.getPersenDiskon() + "%");

            // Format original price with thousand separator and two decimals
            String hargaAwalFormatted = formatCurrency(menu.getHargaAwal());
            // Harga Awal (dicoret)
            tvMenuPrice.setText(hargaAwalFormatted);
            tvMenuPrice.setPaintFlags(tvMenuPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            // Calculate and format discounted price
            double hargaAwal = menu.getHargaAwal();
            int diskon = menu.getPersenDiskon();
            // Perhitungan: hargaDiskon = hargaAwal - (hargaAwal * (diskon / 100.0))
            double hargaDiskon = hargaAwal - (hargaAwal * (diskon / 100.0));
            String hargaDiskonFormatted = formatCurrency(hargaDiskon);
            // Harga Diskon (harga akhir)
            tvDiscountedPrice.setText(hargaDiskonFormatted);

            // Load image using Glide
            Glide.with(itemView.getContext())
                    .load(menu.getLinkGambar())
                    .into(ivMenuImage);

            // Logging for debugging
            Log.d("MenuAdapterAdmin", "Nama: " + menu.getNamaMakanan());
            Log.d("MenuAdapterAdmin", "Deskripsi: " + menu.getDeskripsi());
            Log.d("MenuAdapterAdmin", "URL Gambar: " + menu.getLinkGambar());
        }

        private String formatCurrency(double amount) {
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMANY);
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            return "Rp " + formatter.format(amount);
        }
    }
}
