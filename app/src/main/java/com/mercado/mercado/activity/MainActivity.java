package com.mercado.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mercado.mercado.activity.carrinho.CarShopActivity;
import com.mercado.mercado.on.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mercado.mercado.activity.pages_fragment.PageFragmentAP;
import com.mercado.mercado.activity.pages_fragment.PageFragmentBeb;
import com.mercado.mercado.activity.pages_fragment.PageFragmentH;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    public Intent intentCarShop;
    FragmentPagerItemAdapter adapterPager;
    ViewPager viewPage;
    SmartTabLayout viewPagerTab;

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapterPager.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle("");
        }

        actionBarDrawerToggle = new
                ActionBarDrawerToggle(this, drawer, R.string.nav_open, R.string.nav_close);

        drawer.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle.syncState();

        adapterPager = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(getApplicationContext())
                        .add(R.string.tabAlimentosP, PageFragmentAP.class)
                        .add(R.string.tabBebidas, PageFragmentBeb.class)
                        .add(R.string.tabHigieneL, PageFragmentH.class)
                        .create()
        );
        adapterPager.notifyDataSetChanged();


        viewPage = findViewById(R.id.viewpager);
        viewPage.setAdapter(adapterPager);

        viewPagerTab = findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPage);

        //View view = getCurrentFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        getMenuInflater().inflate(R.menu.menu_car_shop, menu);
        return true;
    }

    public void alterarImg(View view) {
        Toast.makeText(this, "Alterar Imagem do user", Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
    }

    public void infoUser(View v) {
        Toast.makeText(this, "Informações do usuario", Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
    }

    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.carrinho) {
            intentCarShop = new Intent(this, CarShopActivity.class);
            startActivity(intentCarShop);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "mercado", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_marmits:
                Toast.makeText(this, "marmita", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_shareApp:
                Toast.makeText(this, "compartilhar", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_info:
                Toast.makeText(this, "Informações", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}