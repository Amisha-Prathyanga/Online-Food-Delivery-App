package com.example.foodapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.eventbus.MyUpdateCartEvent;
import com.example.foodapp.listener.ICartLoadListener;
import com.example.foodapp.listener.IRecyclerViewClickListener;
import com.example.foodapp.model.CartModel;
import com.example.foodapp.model.DrinkModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyDrinkAdapter extends RecyclerView.Adapter<MyDrinkAdapter.MyDrinkViewHolder> implements Filterable {

    private Context context;
    private List<DrinkModel> drinkModelList;
    private ICartLoadListener iCartLoadListener;
    private List<DrinkModel> drinkModelListFull;

    ImageView dlte;


    public MyDrinkAdapter(Context context, List<DrinkModel> drinkModelList, ICartLoadListener iCartLoadListener){
        this.context = context;
        this.drinkModelList = drinkModelList;
        this.iCartLoadListener = iCartLoadListener;
        drinkModelListFull = new ArrayList<>(drinkModelList);

    }

    @NonNull
    @Override
    public MyDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyDrinkViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_drink_item,parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull MyDrinkViewHolder holder, int position) {
        Glide.with(context)
                .load(drinkModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("$").append(drinkModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(drinkModelList.get(position).getName()));



        holder.setListener((view, adapterPosition) -> {
           addToCart(drinkModelList.get(position));
        });

        holder.btnDelete2.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Delete item")
                    .setMessage("Do you really want to delete item")
                    .setNegativeButton("CANCEL",(dialog1, which) -> dialog1.dismiss())
                    .setPositiveButton("OK",(dialog12,which) -> {

                        //Temp remove
                        notifyItemRemoved(position);

                        deleteFromFirebase(drinkModelList.get(position));
                        dialog12.dismiss();
                    }).create();
            dialog.show();
        });

    }

    private void deleteFromFirebase(DrinkModel drinkModel){
        FirebaseDatabase.getInstance()
                .getReference("Drink")
                .child(drinkModel.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid -> EventBus.getDefault().postSticky(new MyUpdateCartEvent()));

    }

    private void addToCart(DrinkModel drinkModel) {
        DatabaseReference userCart = FirebaseDatabase
                .getInstance()
                .getReference("Cart")
                .child("Unique");

        userCart.child(drinkModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            CartModel cartModel = snapshot.getValue(CartModel.class);
                            cartModel.setQuantity(cartModel.getQuantity()+1);
                            Map<String, Object> updateData = new HashMap<>();
                            updateData.put("quantity", cartModel.getQuantity());
                            updateData.put("totalPrice",cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                            userCart.child(drinkModel.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(aVoid -> {
                                        iCartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));
                        }
                        else
                        {
                            CartModel cartModel = new CartModel();
                            cartModel.setName(drinkModel.getName());
                            cartModel.setImage(drinkModel.getImage());
                            cartModel.setKey(drinkModel.getKey());
                            cartModel.setPrice(drinkModel.getPrice());
                            cartModel.setQuantity(1);
                            cartModel.setTotalPrice(Float.parseFloat(drinkModel.getPrice()));

                            userCart.child(drinkModel.getKey())
                                    .setValue(cartModel)
                                    .addOnSuccessListener(aVoid -> {
                                        iCartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));
                        }
                        EventBus.getDefault().postSticky(new MyUpdateCartEvent());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iCartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return drinkModelList.size();
    }

    public class MyDrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.btnDelete2)
        ImageView btnDelete2;



        IRecyclerViewClickListener listener;

        public void setListener(IRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        private Unbinder unbinder;
        public MyDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecyclerClick(v,getAdapterPosition());
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DrinkModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(drinkModelListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(DrinkModel item : drinkModelListFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            drinkModelList.clear();
            drinkModelList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
