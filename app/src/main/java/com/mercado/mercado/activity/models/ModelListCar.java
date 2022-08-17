package com.mercado.mercado.activity.models;

public class ModelListCar {

    public String nomeProdut, preco, urlImg;
    int und;
    //public int und;

    public ModelListCar(String nomeProdut, String preco, int und, String urlImg){
        this.nomeProdut = nomeProdut;
        this.preco = preco;
        this.urlImg = urlImg;
        this.und = und;
    }

    public String getNomeProdut() {
        return nomeProdut;
    }

    public String getPreco() {
        return preco;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public int getUnd() {
        return und;
    }

}
