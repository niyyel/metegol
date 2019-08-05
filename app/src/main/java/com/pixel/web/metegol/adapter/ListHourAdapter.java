package com.pixel.web.metegol.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixel.web.metegol.model.PartidoClass;
import com.pixel.web.metegol.R;

import java.util.List;

import static com.pixel.web.metegol.PartidoFragment.pos;

public class ListHourAdapter extends ArrayAdapter<PartidoClass> {

    Context myContext;
    List<PartidoClass> DataList;
    private SparseBooleanArray mSelectedItemsIds;
    // Constructor for get Context and  list

    public  ListHourAdapter(Context context, int resourceId,  List<PartidoClass> lists) {

        super(context,  resourceId, lists);
        //super(context,0, objects);
        mSelectedItemsIds = new  SparseBooleanArray();
        myContext = context;
        DataList = lists;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // Â¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.fragment_partido_hour,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView imgState = (ImageView) convertView.findViewById(R.id.imgState);
        TextView hour_input = (TextView) convertView.findViewById(R.id.txtHourInput);
        TextView hour_output = (TextView) convertView.findViewById(R.id.txtHourOutput);
        TextView state = (TextView) convertView.findViewById(R.id.txtState);
        // Lead actual.
        PartidoClass miclass = getItem(position);
        hour_input.setText(miclass.getHour_input());
        hour_output.setText(miclass.getHour_output());
        state.setText(miclass.getState());
        imgState.setImageResource(miclass.getPhotoState());



        state.setTextColor(Color.BLACK);
        hour_input.setTextColor(Color.BLACK);
        hour_output.setTextColor(Color.BLACK);


        convertView.setBackgroundColor(Color.WHITE); //or whatever is your default color
        if(miclass.getState().equals("RESERVADO")){
            convertView.setBackgroundColor(Color.GRAY);
        }
        //if the position exists in that list the you must set the background to BLUE
        if(pos!=null){
            if (pos.contains(position)) {
                convertView.setBackgroundColor(Color.BLUE);
                state.setTextColor(Color.WHITE);
                hour_input.setTextColor(Color.WHITE);
                hour_output.setTextColor(Color.WHITE);
            }
        }
        return convertView;
    }

    @Override
    public void remove(PartidoClass object) {
        DataList.remove(object);
        notifyDataSetChanged();
    }

    // get List after update or delete

    public  List<PartidoClass> getMyList() {
        return DataList;
    }

    public void  toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }
    // Remove selection after unchecked

    public void  removeSelection() {
        mSelectedItemsIds = new  SparseBooleanArray();
        notifyDataSetChanged();
    }

    // Item checked on selection

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position,  value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();

    }

    public int  getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public  SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
