package com.pixel.web.metegol;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pixel.web.metegol.adapter.ListHourAdapter;
import com.pixel.web.metegol.model.DetailRentClass;
import com.pixel.web.metegol.model.PartidoClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MyReservationFragment extends Fragment {
    ListView misReservasList;
    public ArrayAdapter<PartidoClass> PartidoAdapter;
    List<PartidoClass> mishoras;
    public MyReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_reservation, container,false);
        misReservasList = (ListView) view.findViewById(R.id.list_reservation);
        mishoras=HourRepository.getArrayHourReserva();
        loadList();
        /*PartidoAdapter = new ListHourAdapter(getContext(),0,mishoras);
        misReservasList.setAdapter(PartidoAdapter);*/
        return view;
    }
    public void loadList() {

        Calendar fechax = new GregorianCalendar();
        int año = fechax.get(Calendar.YEAR);
        int mes = fechax.get(Calendar.MONTH)+1;
        int dia = fechax.get(Calendar.DAY_OF_MONTH);
        String fecha=""+año+"-"+mes+"-"+dia;
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("detail_rent").whereEqualTo("date_rent",fecha)
                .whereEqualTo("email",user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().size()>0){
                                //si hay datos
                                for (DocumentSnapshot doc : task.getResult()) {
                                    //DetailRentClass note = doc.toObject(DetailRentClass.class);
                                    //note.setId(doc.getId());
                                    DetailRentClass miclassx;

                                    for ( int j=0;j<mishoras.size();j++ ){
                                        if(mishoras.get(j).getId()== Integer.parseInt(""+doc.getData().get("id_hour"))){
                                            mishoras.get(j).setState("RESERVADO");
                                        }
                                    }

                                }

                                //agregando el adaptador
                                PartidoAdapter = new ListHourAdapter(getContext(),0,mishoras);
                                misReservasList.setAdapter(PartidoAdapter);
                                PartidoAdapter.notifyDataSetChanged(); //notify the adapter of the change
                                //fin de agregar el adptador
                            }

                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
