package com.mercado.mercado.activity.carrinho;

import com.mercado.mercado.activity.models.ModelListCar;

import java.util.List;

public interface ListaCar {

    List<ModelListCar> getListCar();

    void addListCar( ModelListCar modelListCar );
}
