package com.pixel.web.metegol.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pixel.web.metegol.model.DetailRentClass;
import com.pixel.web.metegol.model.PartidoClass;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class DetailRentDao {
    public static void loadList(final List<PartidoClass> mishoras) {
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
                            List<DetailRentClass> listDetails = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                //DetailRentClass note = doc.toObject(DetailRentClass.class);
                                //note.setId(doc.getId());
                                DetailRentClass miclassx;
                                for ( int j=0;j<mishoras.size();j++ ){
                                    if(mishoras.get(j).getId()== Integer.parseInt(""+doc.getData().get("id_hour"))){
                                        mishoras.get(j).setState("RESERVADO");
                                       // Log.e("holitas", "mi_datax1: "+doc.getData());
                                        Log.e("ttt", "mi_datax1: "+mishoras.get(j).getId());
                                    }
                                }
                               // miclassx =(DetailRentClass) doc.getData();


                               // Log.d("listx", "mi_datax2: "+doc.getData().get("id_hour"));
                                //Log.d("dat", "datexx: "+doc.getData().get("date_rent"));

                                /*DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
                                df.setTimeZone(TimeZone.getTimeZone("GMT"));
                                Date today=new Date();
                                try {
                                    // Convert string into Date
                                    today = df.parse(""+doc.getData().get("date_rent"));
                                    //today = df.parse("Mon, 16 Apr 2018 00:00:00 GMT+08:00");
                                    System.out.println("Today = " + df.format(today));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Log.d("en_javaa", "datexx: "+today);*/

                                //notesList.add(note);
                            }

                            /*mAdapter = new NoteRecyclerViewAdapter(notesList, getApplicationContext(), firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);*/
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
