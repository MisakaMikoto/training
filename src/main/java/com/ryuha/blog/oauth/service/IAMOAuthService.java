package com.ryuha.blog.oauth.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Misaka on 2016-04-22.
 */
@Service(value = "iamOAuthService")
public class IAMOAuthService {
    public static final String GET = "GET";
    public static final String POST = "POST";

    public String getToken(String code, String clientId, String clientSecret) {
        String responseString = null;

        try {
            URL url = new URL("http://52.79.164.208:8080/oauth/access_token");
            String urlParameters = "client_id=" + clientId + "&grant_type=authorization_code&client_secret=" + clientSecret +"&code=" + code;
            responseString = this.httpURLConnectionHandler(POST, url, urlParameters);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public String getTokenResource(String clientId, String clientSecret, String decodedUsername, String decodedPassword, String scope) {
        String responseString = null;

        try {
            URL url = new URL("http://52.79.164.208:8080/oauth/access_token");
            String urlParameters = "client_id=" + clientId + "&grant_type=password&client_secret=" + clientSecret + "&username=" + decodedUsername +
                    "&password=" + decodedPassword + "&scope=" + scope;
            responseString = this.httpURLConnectionHandler(POST, url, urlParameters);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public String getTokenClient(String clientId, String clientSecret, String scope) {
        String responseString = null;

        try {
            URL url = new URL("http://52.79.164.208:8080/oauth/access_token");
            String urlParameters = "client_id=" + clientId + "&grant_type=client_credentials&client_secret=" + clientSecret + "&scope=" + scope;
            responseString = this.httpURLConnectionHandler(POST, url, urlParameters);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public String getRefreshToken(String clientId, String clientSecret, String refreshToken) {
        String responseString = null;

        try {
            URL url = new URL("http://52.79.164.208:8080/oauth/access_token");
            String urlParameters = "client_id=" + clientId + "&grant_type=refresh_token&client_secret=" + clientSecret +"&refresh_token=" + refreshToken;
            responseString = this.httpURLConnectionHandler(POST, url, urlParameters);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public String getTokenInfo(String accessToken) {
        String responseString = null;

        try {
            URL url = new URL("http://52.79.164.208:8080/oauth/token_info?access_token=" + accessToken);
            responseString = this.httpURLConnectionHandler(GET, url, null);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public String httpURLConnectionHandler(String method, URL url, String urlParameters) {
        StringBuffer response = null;
        BufferedReader in = null;
        HttpURLConnection connection = null;

        try {
            if(POST.equals(method) && urlParameters != null) {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

            // GET
            } else {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
            }

            int responseCode = connection.getResponseCode();

            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return response.toString();
    }

    public String decodeBase64(String encodedString) {
        String decodedString = new String(Base64.decodeBase64(encodedString.getBytes()));
        return decodedString;
    }
}
