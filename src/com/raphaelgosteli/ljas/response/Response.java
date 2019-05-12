package com.raphaelgosteli.ljas.response;

import com.raphaelgosteli.ljas.render.Render;
import com.raphaelgosteli.ljas.view.View;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpCookie;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Response} represent the HTTP response.
 */
public class Response {

    private static final int STATUS_CODE = 200; // default status code

    private HttpExchange exchange;
    private OutputStream outputStream;
    private StringBuilder body;
    private Headers headers;
    private int statusCode;
    private Set<ResponseListener> responseListeners;

    /**
     * Constructor used to set the instance variables.
     *
     * @param exchange
     */
    public Response(HttpExchange exchange) {
        this.outputStream = exchange.getResponseBody();
        this.exchange = exchange;
        this.statusCode = STATUS_CODE;
        this.headers = exchange.getResponseHeaders();
        this.body = new StringBuilder();
        this.responseListeners = new HashSet<>();
    }

    /**
     * Sends a response with the given content. Calls the event methods on the response listeners.
     *
     * @param content the content which will be sent to client.
     */
    public void send(String content) {
        try {
            for (ResponseListener responseListener : responseListeners) {
                responseListener.beforeSend();
                content = responseListener.beforeSend(content);
            }
            this.exchange.sendResponseHeaders(this.statusCode, content.length());
            this.outputStream.write(content.getBytes());
            this.outputStream.close();
            for (ResponseListener responseListener : responseListeners) {
                responseListener.afterSend();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a response with the given content and the provided statusCode.
     *
     * @param content    the content
     * @param statusCode the HTTP status code
     */
    public void send(String content, int statusCode) {
        this.statusCode = statusCode;
        send(content);
    }

    /**
     * Sends the content of a file to the client.
     *
     * @param path the path to the file.
     */
    public void sendFile(String path) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = Files.newBufferedReader(Paths.get(path));
            StringBuilder content = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) content.append(line);

            send(content.toString());
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(File file) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) content.append(line);

            send(content.toString());
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Render render, View view) {
        send(render.render(view));
    }

    /**
     * Sends the String built in the {@link StringBuilder}
     */
    public void sendBody() {
        send(this.body.toString());
    }

    /**
     * Sends the String built in the {@link StringBuilder} with the given status code
     *
     * @param statusCode the http status code
     */
    public void sendBody(int statusCode) {
        send(this.body.toString(), statusCode);
    }

    /**
     * Default redirection method used to redirect the client. Uses the temporarily redirection status code 302.
     *
     * @param url the url where the client should get redirected to.
     */
    public void redirect(String url) {
        redirect(url, 302);
    }

    /**
     * Method to redirect the client to an desired url.
     *
     * @param url        the url where the client should get redirected to.
     * @param statusCode the redirection status code (3xx)
     */
    public void redirect(String url, int statusCode) {
        this.exchange.getResponseHeaders().add("location", url);
        send("", statusCode);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Setter for a {@link HttpCookie}
     *
     * @param httpCookie the {@link HttpCookie} to set
     */
    public void setCookie(HttpCookie httpCookie) {
        this.headers.add("Set-Cookie", httpCookie.toString().replace("\"", "")); // fix for the to string method adding double quotes
    }

    public StringBuilder getBody() {
        return body;
    }

    public void setBody(StringBuilder body) {
        this.body = body;
    }

    public Set<ResponseListener> getResponseListeners() {
        return responseListeners;
    }

    public void setResponseListeners(Set<ResponseListener> responseListeners) {
        this.responseListeners = responseListeners;
    }
}
