package com.mercado.mercado.activity.carrinho;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.mercado.mercado.activity.models.ModelListCar;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Lista implements ListaCar {

    private static final List<ModelListCar> listCar = new ArrayList<>();
    int index = 0;
    Context context;
    String nome;

    public Lista(){}

    public Lista( String nome, Context context ) {
        this.context = context;
        this.nome = nome;
    }

    @Override
    public List<ModelListCar> getListCar() {
        return listCar;
    }

    @Override
    public void addListCar(@NonNull ModelListCar modelListCar) {

        if ( !listCar.contains( modelListCar )  ){

            listCar.add(modelListCar);
            Toast.makeText(context.getApplicationContext(),
                    nome + " adicionado ao Carrinho. ", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(context.getApplicationContext(),
                 "Este produto ja adicionado ao Carrinho. ", Toast.LENGTH_SHORT).show();
            }

}


