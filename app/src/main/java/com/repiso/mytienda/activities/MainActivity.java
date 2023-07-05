package com.repiso.mytienda.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.repiso.mytienda.R;
import com.repiso.mytienda.adapters.CategoryAdapter;
import com.repiso.mytienda.adapters.ProductAdapter;
import com.repiso.mytienda.databinding.ActivityMainBinding;
import com.repiso.mytienda.models.Category;
import com.repiso.mytienda.models.Product;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCategories();
        initProducts();
        initSlider();

    }

    private void initCategories(){
        categories=new ArrayList<>();
        categories.add(new Category(1,"Deportes","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","#6ae76f","breve descripción"));
        categories.add(new Category(2,"Deportes","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","#6ae76f","breve descripción"));
        categories.add(new Category(3,"Deportes","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","#6ae76f","breve descripción"));
        categories.add(new Category(4,"Deportes","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","#6ae76f","breve descripción"));
        categories.add(new Category(5,"Deportes","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","#6ae76f","breve descripción"));
        categories.add(new Category(6,"Deportes","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","#6ae76f","breve descripción"));
        categories.add(new Category(7,"Deportes","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","#6ae76f","breve descripción"));
        categories.add(new Category(8,"Deportes","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","#6ae76f","breve descripción"));
        //Instanciamos el adapter y el gridlayout
        categoryAdapter=new CategoryAdapter(this, categories);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 4);
        //Asociamos el adapter y layout al RecyclerView
        binding.recyclerViewCategory.setLayoutManager(gridLayoutManager);
        binding.recyclerViewCategory.setAdapter(categoryAdapter);
    }

    private void initProducts(){
        products=new ArrayList<>();
        products.add(new Product("producto","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","disponible",20.00,15.00,20,1));
        products.add(new Product("producto","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","disponible",20.00,15.00,20,2));
        products.add(new Product("producto","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","disponible",20.00,15.00,20,3));
        products.add(new Product("producto","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","disponible",20.00,15.00,20,4));
        products.add(new Product("producto","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","disponible",20.00,15.00,20,5));
        products.add(new Product("producto","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","disponible",20.00,15.00,20,6));
        products.add(new Product("producto","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","disponible",20.00,15.00,20,7));
        products.add(new Product("producto","https://img.freepik.com/iconos-gratis/androide_318-674214.jpg","disponible",20.00,15.00,20,8));
        //Instanciamos el adapter y el gridlayout
        productAdapter=new ProductAdapter(products,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 2);
        //Asociamos el adapter y layout al RecyclerView
        binding.recyclerViewProducts.setLayoutManager(gridLayoutManager);
        binding.recyclerViewProducts.setAdapter(productAdapter);
    }

    private void initSlider(){
        //ViewPager
        binding.carousel.registerLifecycle(getLifecycle());
        binding.carousel.addData(new CarouselItem("https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080"));
        binding.carousel.addData(new CarouselItem("https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080"));
        binding.carousel.addData(new CarouselItem("https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080"));
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
}