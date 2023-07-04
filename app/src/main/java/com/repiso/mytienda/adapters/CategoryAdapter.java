package com.repiso.mytienda.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.repiso.mytienda.R;
import com.repiso.mytienda.databinding.ItemCategoriesBinding;
import com.repiso.mytienda.models.Category;

import java.util.ArrayList;

/**
 * Adapta el modelo de datos a la vista
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context context;
    private ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    /**
     * Infla (renderiza) el layout (archivo xml) que representa a nuestros elementos, y devuelve una instancia de la clase ViewHolder
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories, parent, false));
    }

    /**
     * Asigna valores a cada elemento de la lista
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categories.get(position);
        holder.binding.label.setText(category.getName());
        //holder.binding.image.setImageResource(category.getIcon());

        //Glide es un marco que permite la carga de imágenes y administración de medios de forma ágil y fluida.
        //Permite la carga de imágenes de URL
        Glide.with(context)
                .load(category.getIcon())
                .into(holder.binding.image);

        holder.binding.image.setBackgroundColor(Color.parseColor(category.getColor()));


    }

    /**
     * Devuelve el número de elementos
     * @return
     */
    @Override
    public int getItemCount() {
        return categories.size();
    }

    /**
     * Obtiene las referencias de los componentes visuales (views) de cada elemento de la lista
     */
    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        ItemCategoriesBinding binding;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            //TextView tv_name=(TextView) itemView.findViewById(R.id.label);
            binding = ItemCategoriesBinding.bind(itemView);
        }
    }
}
