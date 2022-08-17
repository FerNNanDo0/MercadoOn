package com.mercado.mercado.activity.pages_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ProgressBar;
import android.widget.SearchView;

import com.mercado.mercado.activity.adapters.AdapterProdutos;
import com.mercado.mercado.activity.models.ModelProdutos;
import com.mercado.mercado.on.R;

import java.util.List;

public class PageFragmentH extends Fragment  implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView1;
    RecyclerView.LayoutManager layoutManager1;
    View view;
    private static List<ModelProdutos> listaDeHigiene;
    private ProgressBar progressBarCircular;
    private AdapterProdutos adapter;
    private Context context;
    public SearchView searchView;
    private AlertDialog.Builder alert;
    ViewModelFragmentH viewModelFragmentH;

    public PageFragmentH() {
        // Required empty public constructor
        //firestore();
    }

    public int searchList(List<ModelProdutos> list, String nome ){

        for (int i=0; i < list.size(); i++){
            if (list.get(i).getDetalhes().toLowerCase().contains(nome.toLowerCase()) ){
                return i;
            }
        }
        return -1;
    }

    //@SuppressWarnings("deprecation")
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
        };
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (listaDeHigiene != null && !listaDeHigiene.isEmpty() ){
            int position = searchList(listaDeHigiene, s);

            recyclerView1.scrollToPosition(position);
            return true;
        }
        return false;
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
        viewModelFragmentH = new ViewModelProvider(this).get(ViewModelFragmentH.class);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_page_h, container, false);
        context = view.getContext();

        //  ProgressBar
        progressBarCircular = view.findViewById(R.id.progressBarCircular1);
        recyclerView1 = view.findViewById(R.id.recyclerViewhl);

        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new GridLayoutManager(requireActivity(), 2);
        recyclerView1.setLayoutManager(layoutManager1);

        viewModelFragmentH.getList().observe(getViewLifecycleOwner(), new Observer<List<ModelProdutos>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<ModelProdutos> listH) {
                listaDeHigiene = listH;
                progressBarCircular.setVisibility(View.INVISIBLE);

                adapter = new AdapterProdutos(listH, context);
                recyclerView1.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });

        return view;
    }

}