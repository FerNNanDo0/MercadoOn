package com.mercado.mercado.activity.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mercado.mercado.activity.carrinho.calculos;
import com.mercado.mercado.activity.models.ModelListCar;
import com.mercado.mercado.on.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListCarAdapter extends RecyclerView.Adapter<ListCarAdapter.ViewHolder> {

    List<ModelListCar> listC;
    Context context;
    View view;
    ModelListCar modelListCar;
    static calculos calculos;

    public ListCarAdapter(List<ModelListCar> listC, Context context, calculos calc ){
        this.listC = listC;
        this.context = context;
        this.calculos = calc;
    }

    @NonNull
    @Override
    public ListCarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_car_adapter,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCarAdapter.ViewHolder holder, int position) {
        modelListCar = listC.get(position);

        holder.nomeProdut.setText(modelListCar.getNomeProdut());
        holder.preco.setText(modelListCar.getPreco());
        holder.textUnd.setText(String.valueOf( modelListCar.getUnd()) );

        Glide.with(context).asDrawable()
                .load(modelListCar.getUrlImg()).into(holder.imgProdut);

// delete itens da lista
        deleteItem(holder, position);
    }

    @Override
    public int getItemCount() {
        if (listC != null && !listC.isEmpty()){
            return listC.size();
        }else{
            return 0;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteItem(ViewHolder holder, int position){
        holder.deleteItem.setOnClickListener(v -> {
            //String nomeProdut = holder.nomeProdut.getText().toString();

            int und = Integer.parseInt( holder.textUnd.getText().toString() );
            double valorItem = Double.parseDouble( holder.preco.getText().toString().replace(",", ".") );

            calculos.subtrair(valorItem, und);

            listC.remove(position);
            notifyDataSetChanged();
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomeProdut, preco, textUnd;
        CircleImageView deleteItem;
        ImageView imgProdut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeProdut = itemView.findViewById(R.id.nomeProdut);
            preco = itemView.findViewById(R.id.produtListPrecoCar);
            imgProdut = itemView.findViewById(R.id.imageProdutCar);

            deleteItem = itemView.findViewById(R.id.btndelete);
            textUnd = itemView.findViewById(R.id.textUndCar);
        }
    }

}