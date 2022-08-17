package com.mercado.mercado.activity.pages_fragment;

import static android.os.SystemClock.sleep;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.mercado.mercado.activity.models.ModelProdutos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewModelFragmentBeb extends ViewModel implements Conexao{

    MutableLiveData<List<ModelProdutos>> listModel;
    ArrayList<ModelProdutos> listBeb;

    ModelProdutos bebidas;
    JSONObject jsonObject;
    String valor, detalhes, urlImg;
    FirebaseFirestore db;
    ListenerRegistration collectionRef;
    int maxDoc;

    public ViewModelFragmentBeb(){

        firestore();

        listModel = new MutableLiveData<>();
        listBeb = new ArrayList<>();

    }

    public LiveData<List<ModelProdutos>> getListModel() {

        // this.listModel.setValue(listBeb);
        this.listModel.postValue(listBeb);
        return listModel;
    }

    @Override
    public void firestore() {
        // firestore
        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("Bebidas").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    //Toast.makeText(requireContext(), "Erro de Conexão !", Toast.LENGTH_SHORT).show();
                    Log.w("Tag=Error", "listen; error", error);
                    return;
                }
                if (value != null){
                    maxDoc = value.getDocuments().size();

                    for ( DocumentSnapshot item : value.getDocuments()) {

                        try {
                            jsonObject = new JSONObject(Objects.requireNonNull(item.getData()));
                            detalhes = jsonObject.getString("Detalhes");
                            valor = jsonObject.getString("Valor");
                           // disponivel = jsonObject.getString("Disponibilidade");
                            urlImg = jsonObject.getString("UrlImg");

                            bebidas = new ModelProdutos(urlImg, detalhes, valor);
                            listBeb.add(bebidas);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (listBeb.size() > maxDoc){
                            listBeb.clear();
                            sleep(100);

                            listBeb.add(bebidas);
                        }
                    }

                }
            }
        });
    }
}
