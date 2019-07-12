package com.example.usuario.metegol;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MatchListAdapter extends ArrayAdapter<PartidoModelo> {
    public MatchListAdapter(@NonNull Context context, List<PartidoModelo> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
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
        PartidoModelo miclass = getItem(position);

        // Setup.
        //  Glide.with(getContext()).load(miclass.getImagenRoute()).into(avatar);

        hour_input.setText(miclass.getHour_input());
        hour_output.setText(miclass.getHour_output());
        state.setText(miclass.getState());
        imgState.setImageResource(miclass.getPhotoState());

        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
