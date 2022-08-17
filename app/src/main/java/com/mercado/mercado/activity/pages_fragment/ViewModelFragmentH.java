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

public class ViewModelFragmentH extends ViewModel implements Conexao{

    MutableLiveData<List<ModelProdutos>> listModel;
    ArrayList<ModelProdutos> listaDeHigiene;

    JSONObject jsonObject;
    String valor, detalhes, urlImg;
    ModelProdutos higiene;
    int maxDoc;
    ListenerRegistration collectionRef;
    FirebaseFirestore db;

    public ViewModelFragmentH(){
        firestore();

        listModel = new MutableLiveData<>();
        listaDeHigiene = new ArrayList<>();
    }

    public LiveData<List<ModelProdutos>>getList() {
        // this.listModel.setValue(listaDeHigiene);
        this.listModel.postValue(listaDeHigiene);

        return listModel;
    }

    @Override
    public void firestore() {

        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("LimpezaHigiene")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            //Toast.makeText(requireContext(), "Erro de Conexão !", Toast.LENGTH_SHORT).show();
                            Log.w("Tag=Error", "listen; error", error);
                            return;
                        }
                        if (value != null) {
                            maxDoc = value.getDocuments().size();

                            for (DocumentSnapshot item : value.getDocuments()) {
                                try {
                                    jsonObject = new JSONObject(Objects.requireNonNull( item.getData()) );

                                    detalhes = jsonObject.getString("Detalhes");
                                    valor = jsonObject.getString("Valor");
                                    //disponivel = jsonObject.getString("Disponibilidade");
                                    urlImg = jsonObject.getString("UrlImg");

                                    higiene = new ModelProdutos(urlImg, detalhes, valor);
                                    listaDeHigiene.add(higiene);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (listaDeHigiene.size() > maxDoc) {
                                    listaDeHigiene.clear();
                                    sleep(100);

                                    listaDeHigiene.add(higiene);
                                }

                            }
                        }
                    }
                });
    }
    // fim class
}
