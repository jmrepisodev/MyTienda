package com.repiso.mytienda.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.repiso.mytienda.R;
import com.repiso.mytienda.fragments.HelpFragment;
import com.repiso.mytienda.fragments.HomeFragment;
import com.repiso.mytienda.fragments.ProfileFragment;

import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Agrega el título y el botón de retorno a la barra de menú
        getSupportActionBar().setTitle("MyStore");

        //Menú inferior
        bottomNavigationView = findViewById(R.id.bottomBar);

        //Fragments
        HomeFragment homeFragment= new HomeFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        HelpFragment helpFragment=new HelpFragment();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.item_home) {
                    setFragment(homeFragment);

                } else if (itemId == R.id.item_help) {
                    setFragment(helpFragment);
                } else if (itemId == R.id.item_profile) {

                    setFragment(profileFragment);
                }

                return true;
            }
        });

        if(savedInstanceState==null){
            setFragment(homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.item_home);
        }

        Cart cart = TinyCartHelper.getCart();

        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            quantity += item.getValue();
        }

        /*
        if(quantity!=0){
            BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.item_cart);
            badge.setVisible(true);
            badge.setNumber(quantity);
        }
        */


    }

    /**
     * Inicializa y reemplaza un fragment por otro en el contenedor FrameLayout
     * @param fragment
     */
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //obtenemos el item del carrito del menú
        final MenuItem itemCart = menu.findItem(R.id.cart);
        View view = itemCart.getActionView();

        TextView cartBadgeTextView = view.findViewById(R.id.cart_badge_text_view);
        cartBadgeTextView.setText(String.valueOf(quantity));
        cartBadgeTextView.setVisibility(quantity == 0 ? View.GONE : View.VISIBLE);

        //barra de búsqueda
        final MenuItem itemSearch = menu.findItem(R.id.search);
        SearchView searchView=(SearchView) itemSearch.getActionView();
        searchView.setQueryHint("Busca tus productos");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(itemCart);
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



}