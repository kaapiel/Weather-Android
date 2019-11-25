package br.com.cielo.weather.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import br.com.cielo.weather.R;
import br.com.cielo.weather.model.jira.User;
import br.com.cielo.weather.model.mock.MockJiraUser;
import br.com.cielo.weather.util.Constants;

public class HttpHelper {

    private final Context c;
    private Handler handler;

    public HttpHelper(Context c){
        this.c = c;
    }

    public User tryLogin(String user, String pass) {

        try {
            URL url = new URL("https://jira.atlassian.net/rest/api/3/myself");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Basic " + new BuildAuthBase64().getAuthBase64(user, pass));
            conn.setHostnameVerifier(DO_NOT_VERIFY);
            conn.setDoOutput(true);

            if (conn.getResponseCode() >= HttpURLConnection.HTTP_OK
                    && conn.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }

                in.close();

                User u = new Gson().fromJson(response.toString(), User.class);
                u.setUser(user);
                u.setPass(pass);

                return u;

            }
            return null;

        } catch (IOException uhe) {
            return new MockJiraUser().getMockJiraUser();
        }

    }

    public User getJiraUser(String user) {

        try {
            //trustAllHosts();
            URL url = new URL("https://jira.atlassian.net/rest/api/latest/user?username="+(user.split("@")[0]));
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn = setCertificate(conn);

//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("visanet.corp", 8080));
//            conn = (HttpsURLConnection) url.openConnection(proxy);
//
//            Authenticator authenticator = new Authenticator() {
//                public PasswordAuthentication getPasswordAuthentication() {
//                    return (new PasswordAuthentication(user, pass.toCharArray()));
//                }
//            };
//            Authenticator.setDefault(authenticator);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Authorization", "Basic "+new BuildAuthBase64().getAuthBase64(user, pass));
            conn.setRequestProperty("Authorization", "Basic base64");
            //conn.setHostnameVerifier(DO_NOT_VERIFY);

            if (conn.getResponseCode() >= HttpURLConnection.HTTP_OK && conn.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }

                in.close();
                return new Gson().fromJson(response.toString(), User.class);
            }

        } catch (IOException uhe) {
            Log.e(Constants.Log.EXCEPTION_ERROR, uhe.getMessage());
            return new MockJiraUser().getMockJiraUser();
        } catch (CertificateException ce) {
            Log.e(Constants.Log.EXCEPTION_ERROR, ce.getMessage());
            return new MockJiraUser().getMockJiraUser();
        } catch (NoSuchAlgorithmException nsae) {
            Log.e(Constants.Log.EXCEPTION_ERROR, nsae.getMessage());
            return new MockJiraUser().getMockJiraUser();
        } catch (KeyStoreException kse) {
            Log.e(Constants.Log.EXCEPTION_ERROR, kse.getMessage());
            return new MockJiraUser().getMockJiraUser();
        } catch (KeyManagementException kme) {
            Log.e(Constants.Log.EXCEPTION_ERROR, kme.getMessage());
            return new MockJiraUser().getMockJiraUser();
        }

        return null;
    }

    public Bitmap getImageFromUrl(String url) {
        try {
            return  BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
        } catch (IOException e) {
            Log.e("ERROR ON BITMAP", e.getStackTrace()[0].toString());
        }
        return null;
    }

    private static X509TrustManager systemDefaultTrustManager() {

        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }

    }

    private static void trustAllHosts() {

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws SecurityException{
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws SecurityException{
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            Log.e("SSL", e.getStackTrace()[0].toString());
        }
    }

    private final static HostnameVerifier DO_NOT_VERIFY = (hostname, session) -> true;

    public String tableauAuth(String user, String pass) throws IOException {

        trustAllHosts();
        URL url = new URL("https://tableau.com.br/api/2.8/auth/signin");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/xml");
        conn.setDoOutput(true);
        conn.setHostnameVerifier(DO_NOT_VERIFY);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes("<tsRequest>\n" +
                "  <credentials name=\""+user+"\" password=\""+pass+"\" >\n" +
                "    <site contentUrl=\"\" />\n" +
                "  </credentials>\n" +
                "</tsRequest>");
        wr.flush();
        wr.close();

        if (conn.getResponseCode() >= HttpURLConnection.HTTP_OK && conn.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }

            String token = response.toString().split("<credentials token=\"")[1].split("\"><")[0];

            Log.e("Tableau token", token);

            in.close();
            return token;
        }
        return null;
    }

    public String getViewData(String authToken) throws IOException {

        trustAllHosts();
        URL url = new URL("https://tableau.com.br/api/2/sites/#/views/dashboard/Dashboard/data");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Tableau-Auth", authToken);
        conn.setHostnameVerifier(DO_NOT_VERIFY);

        if (conn.getResponseCode() >= HttpURLConnection.HTTP_OK && conn.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }

            String responseView = response.toString();

            Log.e("Tableau response", responseView);

            in.close();
            return responseView;
        }
        return null;
    }

    public HttpsURLConnection setCertificate(HttpsURLConnection conn) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        Certificate ca;
        try (InputStream caInput = c.getResources().openRawResource(R.raw.cert)) {
            ca = cf.generateCertificate(caInput);
            Log.e(Constants.Log.EXCEPTION_ERROR, "ca=" + ((X509Certificate) ca).getSubjectDN());
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        // Tell the URLConnection to use a SocketFactory from our SSLContext
        conn.setSSLSocketFactory(context.getSocketFactory());

        return conn;
    }
}
