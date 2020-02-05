package br.com.aguido.weather.model.mock;


import br.com.aguido.weather.model.jira.AvatarUrls;
import br.com.aguido.weather.model.jira.User;

/**
 * Created by gagafra on 18/02/18.
 */

public class MockJiraUser {

    public User getMockJiraUser(){

        User u = new User();
        u.setDisplayName("Usuário Cielo");
        u.setEmailAddress("usuario.cielo@cielo.com.br");
        u.setAvatarUrls(null);
        u.setUser("mock");
        u.setUser("C!elo_1234");

        AvatarUrls a = new AvatarUrls();
        a.setAvatar48x48("");

        u.setAvatarUrls(a);

        return u;
    }

}