package com.repiso.mytienda.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.repiso.mytienda.R;
import com.repiso.mytienda.databinding.ActivityProductDetailBinding;
import com.repiso.mytienda.models.Product;
import com.repiso.mytienda.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    ActivityProductDetailBinding binding;
    Product currentProduct;
    private int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        int id = getIntent().getIntExtra("id",0);
        double price = getIntent().getDoubleExtra("price",0);

        binding.productName.setText(name);
        //Colocar Scroll sobre TextView
        binding.productDescription.setMovementMethod(new ScrollingMovementMethod());


        Glide.with(this)
                .load(image)
                .into(binding.productImage);

        getProductDetails(id);


        //Agrega el título y el botón de retorno a la barra de menú
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Cart cart = TinyCartHelper.getCart();

        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            quantity += item.getValue();
        }

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct,1);

                binding.btnAddToCart.setEnabled(false);
                binding.btnAddToCart.setText("Agregado al carrito");

                quantity++;
                invalidateOptionsMenu();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);

        //obtenemos el item del carrito del menú
        final MenuItem menuItem = menu.findItem(R.id.cart);
        View view = menuItem.getActionView();

        TextView cartBadgeTextView = view.findViewById(R.id.cart_badge_text_view);
        cartBadgeTextView.setText(String.valueOf(quantity));
        cartBadgeTextView.setVisibility(quantity == 0 ? View.GONE : View.VISIBLE);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart) {
            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void getProductDetails(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = Constants.GET_PRODUCT_DETAILS_URL + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("status").equals("success")) {
                        JSONObject product = object.getJSONObject("product");

                        binding.productName.setText(product.getString("name"));
                        binding.productPrice.setText(String.valueOf(product.getDouble("price")));
                        binding.productDescription.setText(
                                Html.fromHtml(product.getString("description"))
                        );

                        currentProduct = new Product(
                                product.getString("name"),
                                Constants.PRODUCTS_IMAGE_URL + product.getString("image"),
                                product.getString("status"),
                                product.getDouble("price"),
                                product.getDouble("price_discount"),
                                product.getInt("stock"),
                                product.getInt("id")
                        );

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

}