package com.example.usuario.metegol;

public class PartidoModelo {
    private String hour_input, hour_output, state;
    private int photoState;

    public PartidoModelo() {
    }

    public PartidoModelo(String hour_input, String hour_output, String state,int photoState) {
        this.hour_input = hour_input;
        this.hour_output = hour_output;
        this.state = state;
        this.photoState = photoState;
    }

    public String getHour_input() {
        return hour_input;
    }

    public void setHour_input(String hour_input) {
        this.hour_input = hour_input;
    }

    public String getHour_output() {
        return hour_output;
    }

    public void setHour_output(String hour_output) {
        this.hour_output = hour_output;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPhotoState() {
        return photoState;
    }

    public void setPhotoState(int photoState) {
        this.photoState = photoState;
    }

}
