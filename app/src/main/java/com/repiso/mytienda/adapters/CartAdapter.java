package com.repiso.mytienda.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.repiso.mytienda.R;
import com.repiso.mytienda.databinding.ItemCartBinding;
import com.repiso.mytienda.databinding.QuantityDialogBinding;
import com.repiso.mytienda.models.Cart;
import com.repiso.mytienda.models.Product;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    Context context;
    ArrayList<Product> products;

    public CartAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
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
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent,false));
    }

    /**
     * Asigna valores a cada elemento de la lista
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.image);

        holder.binding.name.setText(product.getName());
        holder.binding.price.setText(product.getPrice()+" €");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            QuantityDialogBinding quantityDialogBinding;
            @Override
            public void onClick(View v) {
                quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));
                quantityDialogBinding.productName.setText(product.getName());
                //quantityDialogBinding.productStock.setText(product.getStock());

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(quantityDialogBinding.getRoot())
                        .create();

                dialog.show();
            }
        });

    }

    /**
     * Devuelve el número de elementos
     * @return nº elementos
     */
    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Obtiene las referencias de los componentes visuales (views) de cada elemento de la lista
     */
    public class CartViewHolder extends RecyclerView.ViewHolder {

        ItemCartBinding binding;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
