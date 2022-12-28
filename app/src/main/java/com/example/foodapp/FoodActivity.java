package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.MyDrinkAdapter;
import com.example.foodapp.eventbus.MyUpdateCartEvent;
import com.example.foodapp.listener.ICartLoadListener;
import com.example.foodapp.listener.IDrinkLoadListener;
import com.example.foodapp.model.CartModel;
import com.example.foodapp.model.DrinkModel;
import com.example.foodapp.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodActivity extends AppCompatActivity implements IDrinkLoadListener, ICartLoadListener {
    @BindView(R.id.recycler_drink)
    RecyclerView recyclerDrink;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.badge)
    NotificationBadge badge;

    @BindView(R.id.btnCart)
    FrameLayout btnCart;

    IDrinkLoadListener drinkLoadListener;
    ICartLoadListener cartLoadListener;

    private MyDrinkAdapter adapter;

    ImageView dlte;

//    public FoodActivity(MyDrinkAdapter adapter) {
//        this.adapter = adapter;
//    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if(EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class))
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpdateCart(MyUpdateCartEvent event)
    {
        countCartItem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        init();
        loadDrinkFromFirebase();
        countCartItem();

//        SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
//        String type = sharedPreferences.getString("Type", null);
//
//        dlte = findViewById(R.id.btnDelete2);
//
//        if (type.equals("admin")) {
//            dlte.setVisibility(View.VISIBLE);
//
//        }
    }

    private void loadDrinkFromFirebase() {
        List<DrinkModel> drinkModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Drink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot drinkSnapshot:snapshot.getChildren())
                            {
                                DrinkModel drinkModel = drinkSnapshot.getValue(DrinkModel.class);
                                drinkModel.setKey(drinkSnapshot.getKey());
                                drinkModels.add(drinkModel);
                            }
                            drinkLoadListener.onDrinkLoadSuccess(drinkModels);
                        }
                        else
                            drinkLoadListener.onDrinkLoadFailed("Cant find Drink");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
drinkLoadListener.onDrinkLoadFailed(error.getMessage());
                    }
                });
    }

    private void init(){
        ButterKnife.bind(this);

        drinkLoadListener = this;
        cartLoadListener = this;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerDrink.setLayoutManager(gridLayoutManager);
        recyclerDrink.addItemDecoration(new SpaceItemDecoration());

        btnCart.setOnClickListener(v -> startActivity(new Intent(this,CartActivity.class)));
    }

    @Override
    public void onDrinkLoadSuccess(List<DrinkModel> drinkModelList) {
        MyDrinkAdapter adapter = new MyDrinkAdapter(this, drinkModelList, cartLoadListener);
        recyclerDrink.setAdapter(adapter);
    }

    @Override
    public void onDrinkLoadFailed(String message) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        int cartSum = 0;
        for(CartModel cartModel:cartModelList)
            cartSum += cartModel.getQuantity();
        badge.setNumber(cartSum);
    }

    @Override
    public void onCartLoadFailed(String message) {
            Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {
        List<CartModel> cartModels = new ArrayList<>();
        FirebaseDatabase
                .getInstance().getReference("Cart")
                .child("unique")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot cartSnapshot:snapshot.getChildren())
                        {
                            CartModel cartModel= cartSnapshot.getValue(CartModel.class);
                            cartModel.setKey(cartSnapshot.getKey());
                            cartModels.add(cartModel);
                        }
                        cartLoadListener.onCartLoadSuccess(cartModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}