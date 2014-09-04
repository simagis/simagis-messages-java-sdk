package com.simagis.live.messages.samples;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by alexei.vylegzhanin@gmail.com on 9/4/2014.
 */
public class SendMessage {

    private URL serverUrl;
    private JSONObject message;

    public static void main(String[] args) throws IOException, JSONException {
        final SendMessage instance = new SendMessage();
        if (args.length >= 1) {
            instance.setServerUrl(new URL(args[0]));
        } else {
            instance.setServerUrl(new URL("http://messages:pass@localhost:8080/live/messages"));
        }
        if (args.length >= 2) {
            instance.setMessage(readJsonObject(Files.newInputStream(Paths.get(args[1]))));
        } else {
            instance.setMessage(readJsonObject(SendMessage.class.getResourceAsStream("SimpleMessage.json")));
        }
        instance.run();
    }


    private void run() throws IOException, JSONException {
        final JSONObject response = postMessage(getServerUrl(), getMessage());
        System.out.println(response.toString(4));
    }

    public void setServerUrl(URL serverUrl) {
        this.serverUrl = serverUrl;
    }

    public URL getServerUrl() {
        return serverUrl;
    }

    public void setMessage(JSONObject message) {
        this.message = message;
    }

    public JSONObject getMessage() {
        return message;
    }


    private static JSONObject postMessage(URL serverUrl, JSONObject message) throws IOException, JSONException {
        final byte[] bytes = message.toString().getBytes("UTF-8");
        final HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
        setAuthorization(connection, serverUrl);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Content-Length", Integer.toString(bytes.length));
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.getOutputStream().write(bytes);
        final int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Invalid response code: " + responseCode
                    + " (" + HttpURLConnection.HTTP_OK + " expected)");
        }

        final JSONObject response = readJsonObject(connection.getInputStream());
        final JSONObject error = response.optJSONObject("error");
        if (error != null) {
            throw new IOException("Error: " + error.toString(4));
        }

        final JSONObject result = response.optJSONObject("result");
        if (result == null) {
            throw new IOException("Invalid response (result expected): " + response.toString(4));
        }

        return response;
    }

    private static void setAuthorization(HttpURLConnection connection, URL serverUrl) throws UnsupportedEncodingException {
        final String userInfo = serverUrl.getUserInfo();
        if (userInfo != null) {
            connection.setRequestProperty("Authorization",
                    "Basic " + new BASE64Encoder().encode(userInfo.getBytes("UTF-8")));
        }
    }

    private static JSONObject readJsonObject(InputStream inputStream) throws IOException, JSONException {
        final JSONObject result;
        try (final InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8")) {
            result = new JSONObject(new JSONTokener(reader));
        }
        return result;
    }
}

