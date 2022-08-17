package com.mercado.mercado.activity.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mercado.mercado.activity.carrinho.Lista;
import com.mercado.mercado.activity.models.ModelListCar;
import com.mercado.mercado.activity.models.ModelProdutos;
import com.mercado.mercado.on.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.ViewHolder> {

    List<ModelProdutos> list;
    View view;
    Context context;
    static ModelListCar modelListCar;
    String urlImg;

    int unidade;
    int click = 1;

    public AdapterProdutos(List<ModelProdutos> list){
        this.list = list;
    }

    public AdapterProdutos(List<ModelProdutos> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterProdutos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.adapter_list, parent, false);

        return new AdapterProdutos.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterProdutos.ViewHolder holder, int position) {

        ModelProdutos produto = list.get(position);

        Glide.with(context).asDrawable()
                .load( produto.getUrlImg() ).into(holder.imgProduto);

        holder.detalhes.setText( produto.getDetalhes());
        holder.valor.setText( produto.getValor());


// add itens ao car
        holder.button.setOnClickListener( view -> {

            String nome = holder.detalhes.getText().toString();
            String valor = holder.valor.getText().toString();

            for (int i = 0; i < list.size(); i++){
                if ( list.get(i).getDetalhes().equals(nome) ){
                    urlImg = list.get(i).getUrlImg();
                }
            }
            unidade = Integer.parseInt( holder.und.getText().toString() );

            Lista list = new Lista(nome, context);
            modelListCar = new ModelListCar(nome, valor, unidade, urlImg);
            list.addListCar(modelListCar);

        });

        // - unidades
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unidade = Integer.parseInt( holder.und.getText().toString() );
                if (unidade > 1){
                    unidade = unidade-1;

                    String und = String.valueOf(unidade);
                    holder.und.setText( und );
                }
            }
        });

        // + unidades
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unidade = Integer.parseInt( holder.und.getText().toString() );
                if (unidade < 5){
                    unidade = unidade+1;

                    String und = String.valueOf(unidade);
                    holder.und.setText( und );
                }

                click++;
                if (click == 7){
                    Toast.makeText(context, "O máximo é 5 unidades para cada cliente!", Toast.LENGTH_SHORT).show();
                    click = 0;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //   inner classe ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView valor, detalhes, und;
        ImageView imgProduto;

        Button button;
        CircleImageView up, down;

        public ViewHolder(View itemView) {
            super(itemView);

            valor = itemView.findViewById(R.id.textPreco);
            detalhes = itemView.findViewById(R.id.textViewdetalhes);
            imgProduto = itemView.findViewById(R.id.imag_post);
            button = itemView.findViewById(R.id.buttonAddcarrinho);

            und = itemView.findViewById(R.id.produt_unds);

            up = itemView.findViewById(R.id.buttonMais);
            down = itemView.findViewById(R.id.buttonMenos);

        }
    }
}
