package br.com.aguido.weather.service;

import br.com.aguido.weather.model.jira.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JiraService {

    @Headers("Authorization: Basic dDIxMDZyc25AcHJlc3RhZG9yY2JtcC5jb20uYnI6R3FGMzZrNVI4ZDJWUzVna1VjSVpCMjE3")
    @GET("rest/api/latest/user")
    Call<User> getUser(@Query("username") String username);

}
