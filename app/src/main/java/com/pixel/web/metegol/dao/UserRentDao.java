package com.pixel.web.metegol.dao;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pixel.web.metegol.adapter.ListHourAdapter;
import com.pixel.web.metegol.model.PartidoClass;
import com.pixel.web.metegol.model.DetailRentClass;
import com.pixel.web.metegol.model.UserRentClass;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static com.pixel.web.metegol.PartidoFragment.pos;

public class UserRentDao {
    FirebaseFirestore firestoreDB;
   static ArrayAdapter<PartidoClass> PartidoAdapter;
    public UserRentDao() {
        firestoreDB = FirebaseFirestore.getInstance();
    }

    public static void save(final String email, final Number price,final ListView mLeadsList,final ArrayAdapter<PartidoClass> PartidoAdapter){
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("email",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                addUserRent(doc.getId(),email,price,new Timestamp(System.currentTimeMillis()),
                                        new Timestamp(System.currentTimeMillis()),mLeadsList, PartidoAdapter);
                            }
                        } else {
                            //  Log.d("Data", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private static void addUserRent(final String id_user, String mail, Number price,
                                    Timestamp date_rent, Timestamp date_register, final ListView mLeadsList,
                                    final ArrayAdapter<PartidoClass> PartidoAdapter) {
        Map<String, Object> users_rent = new UserRentClass( id_user,  mail,  price,  date_rent,  date_register).toMap();

        FirebaseFirestore.getInstance().collection("user_rent")
                .add(users_rent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        PartidoClass listItem;
                        for (int i=0;i<mLeadsList.getCount();i++)
                        {
                            listItem =(PartidoClass) mLeadsList.getItemAtPosition(i);
                            if(listItem.get_State_now()){
                                addDetailsRent(listItem.getId(),documentReference.getId(),"RESERVADO",id_user);
                                ((PartidoClass) mLeadsList.getItemAtPosition(i)).setState("RESERVADO");
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Orror", "Error adding Note document", e);
                        // Toast.makeText(getApplicationContext(), "Note could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private static void addDetailsRent(Number id_hour, String id_user_rent, String state,String id_user) {

        Calendar fechax = new GregorianCalendar();
        int año = fechax.get(Calendar.YEAR);
        int mes = fechax.get(Calendar.MONTH)+1;
        int dia = fechax.get(Calendar.DAY_OF_MONTH);
        String fecha=""+año+"-"+mes+"-"+dia;
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> users_rent = new DetailRentClass( id_hour, id_user_rent, state,fecha,id_user,user.getEmail()).toMap();

        FirebaseFirestore.getInstance().collection("detail_rent")
                .add(users_rent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.e("Agregado", "DocumentSnapshot written with ID: " + documentReference.getId());
                        //Toast.makeText(getApplicationContext(), "Registrado!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Orror", "Error adding Note document", e);
                        // Toast.makeText(getApplicationContext(), "Note could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
