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

public class ViewModelFragmentAP extends ViewModel implements Conexao {

    MutableLiveData<List<ModelProdutos>> listModel;
    static ArrayList<ModelProdutos> listA;

    static ModelProdutos alimentosEpereciveis;
    static JSONObject jsonObject;
    static String valor, detalhes, urlImg;
    FirebaseFirestore db;
    static ListenerRegistration collectionRef;
    int maxDoc;

    public ViewModelFragmentAP() {
        firestore();

        listModel = new MutableLiveData<>();
        listA = new ArrayList<>();

        //this.listModel.setValue(listA);
        //this.listModel.postValue(listA);
    }

    public LiveData<List<ModelProdutos>> getList() {
        // this.listModel.setValue(listA);
        this.listModel.postValue(listA);

        return listModel;
    }

    @Override
    public void firestore() {

        // firestore
        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("AlimentosEpereciveis").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            jsonObject = new JSONObject(Objects.requireNonNull(item.getData()));

                            detalhes = jsonObject.getString("Detalhes");
                            valor = jsonObject.getString("Valor");
                            //disponivel = jsonObject.getString("Disponibilidade");
                            urlImg = jsonObject.getString("UrlImg");

                            alimentosEpereciveis = new ModelProdutos(urlImg, detalhes, valor);
                            listA.add(alimentosEpereciveis);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (listA.size() > maxDoc) {
                            listA.clear();
                            sleep(100);

                            //alimentosEpereciveis = new AlimentosEpereciveis(urlImg, detalhes, valor, disponivel);
                            listA.add(alimentosEpereciveis);

                        }

                    }
                }
            }
        });
    }
    //fim class
}
