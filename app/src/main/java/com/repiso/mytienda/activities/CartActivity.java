package com.repiso.mytienda.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.repiso.mytienda.R;
import com.repiso.mytienda.adapters.CartAdapter;
import com.repiso.mytienda.databinding.ActivityCartBinding;
import com.repiso.mytienda.models.Product;

import java.util.ArrayList;
import java.util.Map;

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

        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);

            products.add(product);
        }

        cartAdapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText(String.format("%.2f",cart.getTotalPrice()));
            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        binding.cartList.setLayoutManager(linearLayoutManager);
        binding.cartList.setAdapter(cartAdapter);
        binding.cartList.addItemDecoration(dividerItemDecoration);

        binding.subtotal.setText(String.format("%.2f",cart.getTotalPrice()));

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}