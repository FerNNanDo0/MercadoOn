package com.mercado.mercado.activity.models;

public class ModelProdutos implements Produtos{

    //private Bitmap bitmap;
    String valor, detalhes, UrlImg;

    public ModelProdutos(String UrlImg, String detalhes, String valor) {
        //this.bitmap = bitmap;
        this.UrlImg = UrlImg;
        this.detalhes = detalhes;
        this.valor = valor;
    }
// getters

    @Override
    public String getUrlImg() {
        return UrlImg;
    }

    @Override
    public String getValor() {
        return valor;
    }

    @Override
    public String getDetalhes() {
        return detalhes;
    }

}

