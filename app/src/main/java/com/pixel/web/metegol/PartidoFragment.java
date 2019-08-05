package com.pixel.web.metegol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.pixel.web.metegol.adapter.ListHourAdapter;
import com.pixel.web.metegol.dao.DetailRentDao;
import com.pixel.web.metegol.dao.UserRentDao;
import com.pixel.web.metegol.model.DetailRentClass;
import com.pixel.web.metegol.model.PartidoClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class PartidoFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private List<PartidoClass> lstPartido;
    public static ArrayList<Integer> pos = new ArrayList<Integer>();
    public ArrayAdapter<PartidoClass> PartidoAdapter;
    List<PartidoClass> mishoras;
     boolean  bloqueado=false;
     ListView mLeadsList;
    Button reservar ;


    private ListenerRegistration firestoreListener;

    public PartidoFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_partido_card, container,false);
        mLeadsList = (ListView) view.findViewById(R.id.list_hour);
        //cargar la lista
        mishoras=HourRepository.getArrayHour();

        firestoreListener = FirebaseFirestore.getInstance().collection("detail_rent").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    //Log.e("holaa", "Listen failed!", e);
                    return;
                }
                //Log.e("exitoo", "bienxx", e);
                loadList();

            }
        });

        //loadList();
        reservar = (Button) view.findViewById(R.id.buttonReserva);
        reservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert= new AlertDialog.Builder(getContext());
                    alert.setMessage("¿Esta seguro de reservar?");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // guardar datos
                            testRent();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          //  Toast.makeText(getActivity().getApplicationContext(),"No realizó ninguna acción",Toast.LENGTH_SHORT).show();

                        }
                    });
                    alert.show();
                }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //comprobar si ya te ganaron en elegir
    public void testRent() {
        bloqueado = false;
        Calendar fechax = new GregorianCalendar();
        int mes = fechax.get(Calendar.MONTH)+1;
        String fecha=""+fechax.get(Calendar.YEAR)+"-"+mes+"-"+fechax.get(Calendar.DAY_OF_MONTH);

        FirebaseFirestore.getInstance().collection("detail_rent").whereEqualTo("date_rent",fecha)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DetailRentClass> listDetails = new ArrayList<>();
                            if(task.getResult().size()>0){
                                //obtenemos las canchas ocupadas
                                    //DetailRentClass note = doc.toObject(DetailRentClass.class);
                                    //note.setId(doc.getId());
                                    PartidoClass listItem;
                                    for (int i=0;i<mLeadsList.getCount();i++)
                                    {
                                        listItem =(PartidoClass) mLeadsList.getItemAtPosition(i);
                                        if(listItem.get_State_now()){
                                            for (DocumentSnapshot docx : task.getResult()) {
                                                if(listItem.getId()==Integer.parseInt(""+docx.getData().get("id_hour")))
                                                {
                                                    //Log.e("Pos:","ocupado");
                                                    ((PartidoClass) mLeadsList.getItemAtPosition(i)).setState("RESERVADO");
                                                    ((PartidoClass) mLeadsList.getItemAtPosition(i)).setState_now(false);
                                                    for (int j=0;j<pos.size();j++ )
                                                    {
                                                        if(pos.get(j).equals(i)){
                                                            pos.remove(j);
                                                        }
                                                    }
                                                    PartidoAdapter.notifyDataSetChanged();
                                                    bloqueado =true;
                                                    break;
                                                }
                                            }
                                            //Log.d("seleccionó: ",""+listItem.getHour_input()+"-"+listItem.getHour_output());
                                        }
                                        if(bloqueado){
                                            //Log.e("Pos:","bloqueadox");
                                             break;}
                                    }

                                    if(bloqueado){
                                          Toast.makeText(getActivity().getApplicationContext(),"Ya te ganaron :(",Toast.LENGTH_LONG).show();

                                    }else{
                                        //bloqueado =false;
                                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                        UserRentDao.save(user.getEmail(),100.48, mLeadsList,PartidoAdapter);
                                        Toast.makeText(getActivity().getApplicationContext(),"Registrado con exito",Toast.LENGTH_LONG).show();


                                    }

                            }else{
                                //Log.e("Pos:","libre");
                                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                UserRentDao.save(user.getEmail(),100.48, mLeadsList,PartidoAdapter);
                               //bloqueado =false;
                            }

                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void loadList() {
        bloqueado =false;
        Calendar fechax = new GregorianCalendar();
        int año = fechax.get(Calendar.YEAR);
        int mes = fechax.get(Calendar.MONTH)+1;
        int dia = fechax.get(Calendar.DAY_OF_MONTH);
        String fecha=""+año+"-"+mes+"-"+dia;

        FirebaseFirestore.getInstance().collection("detail_rent").whereEqualTo("date_rent",fecha)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Log.e("Pos:","entree");
                            List<DetailRentClass> listDetails = new ArrayList<>();
                            if(task.getResult().size()>0){
                                //si hay datos
                            for (DocumentSnapshot doc : task.getResult()) {
                                //DetailRentClass note = doc.toObject(DetailRentClass.class);
                                //note.setId(doc.getId());
                                DetailRentClass miclassx;
                                for ( int j=0;j<mishoras.size();j++ ){
                                    if(mishoras.get(j).getId()== Integer.parseInt(""+doc.getData().get("id_hour"))){
                                        mishoras.get(j).setState("RESERVADO");
                                       // Log.e("ttt", "mi_datax1: "+mishoras.get(j).getId());
                                    }
                                }
                                for (int i=0;i<mLeadsList.getCount();i++)
                                {
                                    PartidoClass listItem;
                                    listItem =(PartidoClass) mLeadsList.getItemAtPosition(i);
                                    if(listItem.get_State_now()){
                                        //((PartidoClass) mLeadsList.getItemAtPosition(i)).setState("RESERVADO");
                                        ((PartidoClass) mLeadsList.getItemAtPosition(i)).setState_now(false);
                                        for (int j=0;j<pos.size();j++ )
                                        {
                                            if(pos.get(j).equals(i)){
                                                pos.remove(j);
                                            }
                                        }
                                        PartidoAdapter.notifyDataSetChanged();
                                    }
                                }
                                //agregando el adaptador
                                PartidoAdapter = new ListHourAdapter(getContext(),0,mishoras);
                                mLeadsList.setAdapter(PartidoAdapter);
                                mLeadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                                        //Log.e("Pos:","hay datos");
                                        PartidoClass listItem =(PartidoClass) mLeadsList.getItemAtPosition(position);
                                        if(listItem.getState().equals("RESERVADO")){
                                            //no hacer nada
                                        }else{
                                            if(listItem.get_State_now()){
                                                ((PartidoClass) mLeadsList.getItemAtPosition(position)).setState_now(false);
                                               // ((PartidoClass) mLeadsList.getItemAtPosition(position)).setState("dsdsd");
                                            }else{
                                                ((PartidoClass) mLeadsList.getItemAtPosition(position)).setState_now(true);
                                               // ((PartidoClass) mLeadsList.getItemAtPosition(position)).setState("xxxx");
                                            }
                                            if (!pos.contains(position)) {
                                                pos.add(position); //add the position of the clicked row
                                                // Log.e("Pos:",""+position);
                                            }else{
                                               // pos.remove(position);
                                                for (int i=0;i<pos.size();i++ )
                                                {
                                                    if(pos.get(i).equals(position)){
                                                        pos.remove(i);
                                                    }
                                                }
                                            }
                                            PartidoAdapter.notifyDataSetChanged(); //notify the adapter of the change
                                        }

                                    }
                                });
                                //fin de agregar el adptador

                            }
                        }else{
                            //si no hay ningun dato

                                PartidoAdapter = new ListHourAdapter(getContext(),0,mishoras);
                                mLeadsList.setAdapter(PartidoAdapter);
                                mLeadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                                        PartidoClass listItem =(PartidoClass) mLeadsList.getItemAtPosition(position);
                                       // Log.e("Pos:","no hay datos");
                                        if(listItem.getState().equals("RESERVADO")){

                                            //no hacer nada
                                        }else{
                                            if(listItem.get_State_now()){
                                                ((PartidoClass) mLeadsList.getItemAtPosition(position)).setState_now(false);
                                                // ((PartidoClass) mLeadsList.getItemAtPosition(position)).setState("dsdsd");
                                            }else{
                                                ((PartidoClass) mLeadsList.getItemAtPosition(position)).setState_now(true);
                                                // ((PartidoClass) mLeadsList.getItemAtPosition(position)).setState("xxxx");
                                            }
                                            if (!pos.contains(position)) {
                                                pos.add(position); //add the position of the clicked row
                                                // Log.e("Pos:",""+position);
                                            }else{
                                                for (int i=0;i<pos.size();i++ )
                                                {
                                                    if(pos.get(i).equals(position)){
                                                        pos.remove(i);
                                                    }
                                                }
                                            }
                                            PartidoAdapter.notifyDataSetChanged(); //notify the adapter of the change
                                        }

                                    }
                                });
                        }

                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
