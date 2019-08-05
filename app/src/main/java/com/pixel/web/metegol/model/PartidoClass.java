package com.pixel.web.metegol.model;

public class PartidoClass {
    private String hour_input, hour_output, state;
    private boolean state_now=false;
    private int photoState;
    private int id=0;

    public PartidoClass() {
    }

    public PartidoClass(int id,String hour_input, String hour_output, String state, int photoState) {
        this.hour_input = hour_input;
        this.hour_output = hour_output;
        this.state = state;
        this.photoState = photoState;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean get_State_now() {
        return state_now;
    }

    public void setState_now(boolean state_now) {
        this.state_now = state_now;
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
