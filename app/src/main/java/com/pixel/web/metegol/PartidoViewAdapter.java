package com.pixel.web.metegol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixel.web.metegol.model.PartidoClass;

import java.util.List;


public class PartidoViewAdapter extends RecyclerView.Adapter<PartidoViewAdapter.MyViewHolder> {

    Context mContext;
    List<PartidoClass> mData;

    public PartidoViewAdapter(Context mContext, List<PartidoClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(mContext).inflate(R.layout.fragment_partido_card, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.txtHourInput.setText(mData.get(i).getHour_input());
        myViewHolder.txtHourOutput.setText(mData.get(i).getHour_output());
        myViewHolder.txtState.setText(mData.get(i).getState());
        myViewHolder.imgState.setImageResource(mData.get(i).getPhotoState());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txtHourInput;
        private TextView txtHourOutput;
        private TextView txtState;
        private ImageView imgState;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtHourInput = (TextView) itemView.findViewById(R.id.txtHourInput);
            txtHourOutput = (TextView) itemView.findViewById(R.id.txtHourOutput);
            txtState = (TextView) itemView.findViewById(R.id.txtState);

            imgState = (ImageView) itemView.findViewById(R.id.imgState);

        }
    }
}
