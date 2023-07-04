package com.repiso.mytienda.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.repiso.mytienda.R;
import com.repiso.mytienda.adapters.CategoryAdapter;
import com.repiso.mytienda.databinding.ActivityMainBinding;
import com.repiso.mytienda.models.Category;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categories=new ArrayList<>();
        categories.add(new Category(1,"Deportes","https://tutorials.mianasad.com/ecommerce/uploads/category/1688480003229.png","#6ae76f","breve descripción"));
        categories.add(new Category(2,"Deportes","https://tutorials.mianasad.com/ecommerce/uploads/category/1688480003229.png","#6ae76f","breve descripción"));
        categories.add(new Category(3,"Deportes","https://tutorials.mianasad.com/ecommerce/uploads/category/1688480003229.png","#6ae76f","breve descripción"));
        categories.add(new Category(4,"Deportes","https://tutorials.mianasad.com/ecommerce/uploads/category/1688480003229.png","#6ae76f","breve descripción"));
        //Instanciamos el adapter y el gridlayout
        categoryAdapter=new CategoryAdapter(this, categories);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 4);
        //Asociamos el adapter y layout al RecyclerView
        binding.recyclerViewCategory.setLayoutManager(gridLayoutManager);
        binding.recyclerViewCategory.setAdapter(categoryAdapter);
    }
}