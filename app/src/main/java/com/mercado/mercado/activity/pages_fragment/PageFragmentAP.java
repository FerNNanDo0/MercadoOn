package com.mercado.mercado.activity.pages_fragment;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mercado.mercado.activity.adapters.AdapterProdutos;
import com.mercado.mercado.activity.models.ModelProdutos;
import com.mercado.mercado.on.R;

import android.os.Bundle;

import android.annotation.SuppressLint;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.List;


public class PageFragmentAP extends Fragment  implements SearchView.OnQueryTextListener  {

    View view;
    private static List<ModelProdutos> listA;
    private AdapterProdutos adapter;
    private RecyclerView recyclerView1;
    static RecyclerView.LayoutManager layoutManager1;
    private ProgressBar progressBarCircular;
    private Context contextFrag;
    public SearchView searchView;
    //MenuItem searchItm;
    static ViewModelFragmentAP viewModelFragmentA;

    public PageFragmentAP() {

        // firestore();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        try{

            MenuItem searchItm = menu.findItem(R.id.search);
            searchView = (SearchView) searchItm.getActionView();

            if (searchView != null){
                searchView.setOnQueryTextListener( this );
                searchView.setQueryHint("Buscar Produtos ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int searchList(List<ModelProdutos> list, String nome ){

        for (int i=0; i < list.size(); i++){
            if (list.get(i).getDetalhes().toLowerCase().contains(nome.toLowerCase()) ){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (listA != null && !listA.isEmpty()){
            int position = searchList(listA, s);

            recyclerView1.scrollToPosition(position);
            return true;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // --------
        viewModelFragmentA = new ViewModelProvider(this).get(ViewModelFragmentAP.class);
        //listA = viewModelFragmentAP.getList().getValue();

// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_page_a_p, container, false);
        contextFrag = view.getContext();

        //progressBar
        progressBarCircular = view.findViewById(R.id.progressBarCircular);
        recyclerView1 = view.findViewById(R.id.recyclerViewAP);

        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new GridLayoutManager(requireActivity(), 2);
        recyclerView1.setLayoutManager(layoutManager1);

        viewModelFragmentA.getList().observe(getViewLifecycleOwner(), new Observer<List<ModelProdutos>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<ModelProdutos> listAP_Model) {
                listA = listAP_Model;
                progressBarCircular.setVisibility(View.INVISIBLE);

                adapter = new AdapterProdutos(listAP_Model, contextFrag);
                recyclerView1.setAdapter( adapter );
                adapter.notifyDataSetChanged();

            }
        });

        return view;
    }

}