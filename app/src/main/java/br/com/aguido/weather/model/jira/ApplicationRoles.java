package br.com.aguido.weather.model.jira;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ApplicationRoles implements Serializable{

    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("items")
    @Expose
    private transient List<Object> items = null;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

}