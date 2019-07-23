package com.pixel.web.metegol.model;
import java.util.HashMap;
import java.util.Map;
public class UserClass {
    private String name="";
    private String email="";
    private String clave="";
    private String cellphone="";

    public UserClass(String name, String email, String clave, String cellphone) {
        this.name = name;
        this.email = email;
        this.clave = clave;
        this.cellphone = cellphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.name);
        result.put("email", this.email);
        result.put("clave", this.clave);
        result.put("cellphone", this.cellphone);

        return result;
    }
}
