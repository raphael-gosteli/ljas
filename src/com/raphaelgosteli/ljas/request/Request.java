package com.raphaelgosteli.ljas.request;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link Request} represent the HTTP request.
 */
public class Request {

    private URI uri;
    private String body;
    private Map<String, String> parameters;
    private Headers headers;
    private Map<String, String> cookies;

    /**
     * Constructor used to set the instance variables.
     *
     * @param exchange the {@link HttpExchange} which contains information about the request.
     */
    public Request(HttpExchange exchange) {
        this.body = readRequestBody(exchange.getRequestBody());
        this.uri = exchange.getRequestURI();
        this.parameters = parseRequestParameters(this.uri);
        this.headers = exchange.getRequestHeaders();
        this.cookies = getCookies();
    }


    private String readRequestBody(InputStream requestBodyInputStream) {
        try {
            return new String(requestBodyInputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> parseRequestParameters(URI requestUri) {
        Map<String, String> parameterMap = new HashMap<>();
        String uri = requestUri.toString();

        /* check if the uri has a question mark */
        if (uri.contains("?")) {
            String[] uriParams = uri.split("\\?");
            String[] keyValuePairs = uriParams[1].split("&");
            for (String keyValuePair : keyValuePairs) {
                String[] keyValue = keyValuePair.split("=");

                /* check if the length matches the required pattern */
                if (keyValue.length == 2) {
                    parameterMap.put(keyValue[0], keyValue[1]);
                }
            }
        }

        return parameterMap;
    }

    private Map<String, String> getCookies() {
        Map<String, String> cookieMap = new HashMap<>();
        List<String> cookies = headers.get("Cookie");
        for (String cookie : cookies) {
            String[] nameValue = cookie.split("=");
            if (nameValue.length == 2) {
                cookieMap.put(nameValue[0], nameValue[1]);
            }
        }
        return cookieMap;
    }

    /**
     * Getter for a {@link HttpCookie}
     *
     * @param name the name of the cookie
     * @return the {@link HttpCookie} with the name
     */
    public HttpCookie getCookie(String name) {
        String value = this.cookies.get(name);
        if (value != null) {
            return new HttpCookie(name, value);
        }
        return null;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String key) {
        return this.parameters.get(key);
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }
}
