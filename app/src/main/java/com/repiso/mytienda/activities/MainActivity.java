package com.repiso.mytienda.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.repiso.mytienda.R;
import com.repiso.mytienda.adapters.CategoryAdapter;
import com.repiso.mytienda.adapters.ProductAdapter;
import com.repiso.mytienda.databinding.ActivityMainBinding;
import com.repiso.mytienda.models.Category;
import com.repiso.mytienda.models.Product;
import com.repiso.mytienda.utils.Constants;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando datos...");

        //Inflate menu and setup OnMenuItemClickListener
        binding.searchBar.inflateMenu(R.menu.cart_menu, R.drawable.ic_cart);
        binding.searchBar.getMenu().setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(),"Menú lateral",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                switch (buttonCode){
                    case MaterialSearchBar.BUTTON_NAVIGATION:
                        Toast.makeText(getApplicationContext(),"Menú navegación principal",Toast.LENGTH_SHORT).show();
                        break;
                    case MaterialSearchBar.BUTTON_SPEECH:
                        break;
                }
            }
        });

        initCategories();
        initProducts();
        initSlider();

        progressDialog.dismiss();

    }

    /**
     * Carga la lista de categorías
     */
    private void initCategories(){
        categories=new ArrayList<>();

        getCategories();

        //Instanciamos el adapter y el gridlayout
        categoryAdapter=new CategoryAdapter(this, categories);
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 4);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        //Asociamos el adapter y layout al RecyclerView
        binding.recyclerViewCategory.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCategory.setAdapter(categoryAdapter);

    }

    private void initProducts(){
        products=new ArrayList<>();

        getRecentProducts();

        //Instanciamos el adapter y el gridlayout
        productAdapter=new ProductAdapter(products,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 2);
        //Asociamos el adapter y layout al RecyclerView
        binding.recyclerViewProducts.setLayoutManager(gridLayoutManager);
        binding.recyclerViewProducts.setAdapter(productAdapter);
    }

    private void initSlider() {
        getRecentOffers();
        /*
        ImageCarousel carousel = findViewById(R.id.carousel);
        // Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragments it will be viewLifecycleOwner/getViewLifecycleOwner().
        carousel.registerLifecycle(getLifecycle());

        List<CarouselItem> list = new ArrayList<>();

        // Image URL with caption
        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
                        "Photo by Aaron Wu on Unsplash"
                )
        );

        // Just image URL
        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"
                )
        );

        carousel.setData(list);
        */

    }

    /**
     * Obtiene la lista de categorías
     */
    private void getCategories() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("categorías", response);
                    //Transforma String a JSON
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")) {
                        JSONArray categoriesArray = mainObj.getJSONArray("categories");
                        for(int i =0; i< categoriesArray.length(); i++) {
                            JSONObject object = categoriesArray.getJSONObject(i);
                            Category category = new Category(
                                    object.getInt("id"),
                                    object.getString("name"),
                                    Constants.CATEGORIES_IMAGE_URL + object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief")
                            );
                            categories.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(),"Error: No ha sido posible descargar las categorías",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: No ha sido posible descargar las categorías",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    /**
     * Obtiene una lista de productos
     */
    private void getRecentProducts() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = Constants.GET_PRODUCTS_URL + "?count=8";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("status").equals("success")){
                        JSONArray productsArray = object.getJSONArray("products");
                        for(int i =0; i< productsArray.length(); i++) {
                            JSONObject childObj = productsArray.getJSONObject(i);
                            Product product = new Product(
                                    childObj.getString("name"),
                                    Constants.PRODUCTS_IMAGE_URL + childObj.getString("image"),
                                    childObj.getString("status"),
                                    childObj.getDouble("price"),
                                    childObj.getDouble("price_discount"),
                                    childObj.getInt("stock"),
                                    childObj.getInt("id")

                            );
                            products.add(product);
                        }
                        productAdapter.notifyDataSetChanged();
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

    /**
     * Obtiene una lista de ofertas recientes
     */
    private void getRecentOffers() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")) {
                    JSONArray offerArray = object.getJSONArray("news_infos");
                    for(int i =0; i < offerArray.length(); i++) {
                        JSONObject childObj =  offerArray.getJSONObject(i);
                        binding.carousel.addData(
                                new CarouselItem(
                                        Constants.NEWS_IMAGE_URL + childObj.getString("image"),
                                        childObj.getString("title")
                                )
                        );
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {});
        queue.add(request);
    }

}