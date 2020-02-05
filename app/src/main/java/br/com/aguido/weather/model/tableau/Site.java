
package br.com.aguido.weather.model.tableau;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Site {

    @SerializedName("contentUrl")
    @Expose
    private String contentUrl;

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

}
