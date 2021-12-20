package com.example.manga;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.manga.adapter.MyComicAdapter;
import com.example.manga.common.Common;
import com.example.manga.interfaces.IBannerLoadDone;
import com.example.manga.interfaces.IComicLoadDone;
import com.example.manga.model.Comic;
import com.example.manga.service.PicassoLoadingService;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class HomeActivity extends AppCompatActivity implements IBannerLoadDone, IComicLoadDone {

    private Slider slider;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerComic;
    private TextView textComic;

    // Database
    private DatabaseReference banners;
    private DatabaseReference comics;

    // Listener
    private IBannerLoadDone bannerListener;
    private IComicLoadDone comicListener;

    // Dialog
    private android.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Database
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        comics = FirebaseDatabase.getInstance().getReference("Comic");

        // Init Listener
        bannerListener = this;
        comicListener = this;


       /* slider = findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());*/

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
                loadComic();
            }
        });

        recyclerComic = findViewById(R.id.recycler_comic);
        recyclerComic.setHasFixedSize(true);
        recyclerComic.setLayoutManager(new GridLayoutManager(this, 2));

        textComic = findViewById(R.id.text_comic);
    }

   /* @Override
    public void onBannerLoadDoneListener(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));
    }*/

    @Override
    public void onComicLoadDoneListener(List<Comic> comicList) {
        Common.comicList = comicList;

        recyclerComic.setAdapter(new MyComicAdapter(getBaseContext(), comicList));

        textComic.setText(String.format("NEW MANGA (%d)", comicList.size()));

        if (!swipeRefreshLayout.isRefreshing()) {
            alertDialog.dismiss();
        }
    }

    private void loadComic() {
        // show dialog
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Please wait...")
                .build();

        if (swipeRefreshLayout.isRefreshing()) {
            alertDialog.show();
        }

        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Comic> comicList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot comicSnapshot : dataSnapshot.getChildren()) {
                    Comic comic = comicSnapshot.getValue(Comic.class);
                    comicList.add(comic);
                }

                comicListener.onComicLoadDoneListener(comicList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBanner() {
        banners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList = new ArrayList<>();
                for (DataSnapshot bannerSnapshot : dataSnapshot.getChildren()) {
                    String image = bannerSnapshot.getValue(String.class);
                    bannerList.add(image);
                }

                // Call listener
                bannerListener.onBannerLoadDoneListener(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBannerLoadDoneListener(List<String> banners) {

    }
}
