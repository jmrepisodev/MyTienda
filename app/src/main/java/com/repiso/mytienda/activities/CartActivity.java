package com.repiso.mytienda.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;
import com.repiso.mytienda.R;
import com.repiso.mytienda.adapters.CartAdapter;
import com.repiso.mytienda.databinding.ActivityCartBinding;
import com.repiso.mytienda.models.Product;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    CartAdapter cartAdapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Cart cart = TinyCartHelper.getCart(); //carrito de la compra

        products=new ArrayList<>();

        products.add(new Product("nombre", "https://img.freepik.com/iconos-gratis/androide_318-674214.jpg?w=2000", "estado", 25,40,56,1));
        products.add(new Product("nombre", "https://img.freepik.com/iconos-gratis/androide_318-674214.jpg?w=2000", "estado", 25,40,56,2));
        products.add(new Product("nombre", "https://img.freepik.com/iconos-gratis/androide_318-674214.jpg?w=2000", "estado", 25,40,56,3));

        cartAdapter=new CartAdapter(this, products);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        binding.cartList.setLayoutManager(linearLayoutManager);
        binding.cartList.setAdapter(cartAdapter);
        binding.cartList.addItemDecoration(dividerItemDecoration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}