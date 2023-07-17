package com.repiso.mytienda.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.repiso.mytienda.R;
import com.repiso.mytienda.activities.ProductDetailActivity;
import com.repiso.mytienda.databinding.ItemProductBinding;
import com.repiso.mytienda.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Product> productArrayList;
    Context context;

    public ProductAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    /**
     * Infla (renderiza) el layout (archivo xml) que representa a nuestros elementos, y devuelve una instancia de la clase ViewHolder
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAdapter.ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false));
    }

    /**
     *  Asigna valores a cada elemento de la lista
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product=productArrayList.get(position);

        //Glide es un marco que permite la carga de imágenes y administración de medios de forma ágil y fluida.
        //Permite la carga de imágenes de URL
        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.image);
        holder.binding.tvName.setText(product.getName());
        holder.binding.tvPrice.setText(String.valueOf(product.getPrice())+" €");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("image", product.getImage());
                intent.putExtra("id", product.getId());
                intent.putExtra("price", product.getPrice());
                context.startActivity(intent);
            }
        });

    }

    /**
     * Devuelve el número de elementos
     * @return nº elementos
     */
    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    /**
     * Clase interna que obtiene las referencias de los componentes visuales (views) de cada elemento de la lista
     */
    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ItemProductBinding binding;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemProductBinding.bind(itemView);
        }
    }
}
