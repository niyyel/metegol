package com.example.usuario.metegol;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Partido extends Fragment {

    private RecyclerView myrecyclerview;
    private List<PartidoModelo> lstPartido;

    public Partido(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ListView mLeadsList;
        final ArrayAdapter<PartidoModelo> mLeadsAdapter;
        View view=inflater.inflate(R.layout.fragment_partido_card, container,false);
        //myrecyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);

       /* RecyclerView.Adapter recyclerViewAdapter=new PartidoViewAdapter(getContext(),lstPartido);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        myrecyclerview.setAdapter(recyclerViewAdapter);*/



        mLeadsList = (ListView) view.findViewById(R.id.list_hour);
        mLeadsAdapter = new MatchListAdapter(getContext(),HourRepository.getArrayHour());
        mLeadsList.setAdapter(mLeadsAdapter);
        mLeadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PartidoModelo currentLead = mLeadsAdapter.getItem(position);
                Toast.makeText( getContext(), "nombre: "+currentLead.getState(), Toast.LENGTH_SHORT).show();

            }
        });
        return view;
        //Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_partido, container, false);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstPartido = new ArrayList<>();
        lstPartido.add(new PartidoModelo("8:00","08:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("8:30","09:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("9:00","09:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("9:30","10:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("10:00","10:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("10:30","11:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("11:00","11:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("11:30","12:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("12:00","12:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("12:30","13:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("13:00","13:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("13:30","14:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("14:00","14:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("14:30","15:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("15:00","15:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("15:30","16:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("16:00","16:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("16:30","17:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("17:00","17:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("17:30","18:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("18:00","18:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("18:30","19:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("19:00","19:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("19:30","20:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("20:00","20:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("20:30","21:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("21:00","21:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("21:30","22:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("22:00","22:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("22:30","23:00", "RESERVADO", R.drawable.reservado));

    }
}
