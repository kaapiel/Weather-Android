
package br.com.aguido.weather.model.tableau;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableauAuth {

    @SerializedName("credentials")
    @Expose
    private Credentials credentials;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

}
