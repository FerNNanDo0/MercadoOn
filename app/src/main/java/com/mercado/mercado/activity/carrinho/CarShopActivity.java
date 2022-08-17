package com.mercado.mercado.activity.carrinho;

import static android.os.SystemClock.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.service.carrier.CarrierMessagingService;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.mercado.mercado.activity.adapters.ListCarAdapter;
import com.mercado.mercado.activity.models.ModelListCar;
import com.mercado.mercado.on.R;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CarShopActivity extends AppCompatActivity{

    Button btnComprar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    static TextView textViewCar;
    TextView texttt;
    TextView totalText;
    ListCarAdapter listCarAdapter;
    Context context;

    List<ModelListCar> list = new ArrayList<ModelListCar>();
    String preferencesList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    static calculos calc;

    private static final String CHAVE_NOME = "nome";
    final int PRIVATE = 0;
    private static final String ARQ_N = "preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_shop);

        // config da actionBar
        ActionBar actionBar = getSupportActionBar();
        context = this;

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.home_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
            actionBar.setTitle("Carrinho");
        }

        btnComprar = findViewById(R.id.btnComprar);
        recyclerView = findViewById(R.id.recyclerListCar);
        textViewCar = findViewById(R.id.textCarEmpty);
        totalText = findViewById(R.id.textTotal);
        texttt = findViewById(R.id.texttT);

 // config do recycler
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        Lista lista = new Lista();
        list = lista.getListCar();

  // preferences da lista
        preferences = getSharedPreferences(ARQ_N,PRIVATE);
        editor = preferences.edit();

        editor.putString(CHAVE_NOME, list.toString() );
        editor.commit();
        preferencesList = preferences.getString(CHAVE_NOME,"");



        if ( !list.isEmpty()) {
            textViewCar.setVisibility(View.INVISIBLE);

            calc = new calculos(list, totalText);

            listCarAdapter = new ListCarAdapter(list, context, calc);
            recyclerView.setAdapter(listCarAdapter);

            calc.total();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if (list.isEmpty()){
                        invisible();
                        break;
                    }
                }
            }
        }).start();

// btnComprar
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        " finalizando sua compra... ",
                        Toast.LENGTH_SHORT).show();

                String listStr = list.toString();
                String number = "51984601729";
                String number1 = "51984512853";
                sendWhatsApp(listStr, number1);


            }
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void sendWhatsApp(String msg, String number){

        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            packageManager.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_ACTIVITIES);

            Intent sendIntent = new Intent( Intent.ACTION_SEND);

            String url = "https://api.whatsapp.com/send?phone="+number
                    +"&text="+ URLEncoder.encode(msg, "UTF-8");

            sendIntent.setData( Uri.parse( url) );
            sendBroadcast(sendIntent);
            startActivity(sendIntent);

        }catch (Exception e){

        }

    }

    public void invisible(){
        btnComprar.setVisibility(View.INVISIBLE);
        totalText.setVisibility(View.INVISIBLE);
        texttt.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}