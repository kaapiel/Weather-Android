package br.com.aguido.weather.model.jira;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AvatarUrls implements Serializable{

    @SerializedName("48x48")
    @Expose
    private String avatar48x48;
    @SerializedName("24x24")
    @Expose
    private String avatar24x24;
    @SerializedName("16x16")
    @Expose
    private String avatar16x16;
    @SerializedName("32x32")
    @Expose
    private String avatar32x32;

    public String getAvatar48x48() {
        return avatar48x48;
    }

    public void setAvatar48x48(String avatar48x48) {
        this.avatar48x48 = avatar48x48;
    }

    public String getAvatar24x24() {
        return avatar24x24;
    }

    public void setAvatar24x24(String avatar24x24) {
        this.avatar24x24 = avatar24x24;
    }

    public String getAvatar16x16() {
        return avatar16x16;
    }

    public void setAvatar16x16(String avatar16x16) {
        this.avatar16x16 = avatar16x16;
    }

    public String getAvatar32x32() {
        return avatar32x32;
    }

    public void setAvatar32x32(String avatar32x32) {
        this.avatar32x32 = avatar32x32;
    }
}