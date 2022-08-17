package com.mercado.mercado.activity.carrinho;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.mercado.mercado.activity.models.ModelListCar;
import java.text.DecimalFormat;
import java.util.List;

public class calculos implements calc{

    List<ModelListCar> listC;
    String str;
    double total = 0;
    TextView textViewTotal;

    public calculos() {
    }

    public calculos(@NonNull List<ModelListCar> listC, TextView textView){
        this.listC = listC;
        this.textViewTotal = textView;
    }

    @Override
    public void total(){
        for ( int i = 0; i <= listC.size(); i++ ){

            if (i < listC.size() ){
                double preco = Double.parseDouble( listC.get(i).getPreco().replace(",", ".") );
                int und = listC.get(i).getUnd();

                total += preco*und;
            }else {
                str = new DecimalFormat("0.00").format(total) ;
                textViewTotal.setText( str.replace(".",",") );
            }
        }
    }

    @Override
    public void subtrair( double valorItem, int und){
        double total = Double.parseDouble( textViewTotal.getText().toString().replace(",", ".") );

        total = total - valorItem*und;
        String strTotal = new DecimalFormat("0.00").format(total) ;

        textViewTotal.setText( strTotal.replace(".",",") );

    }


}
