package com.raphaelgosteli.ljas.router;

import com.raphaelgosteli.ljas.request.Request;
import com.raphaelgosteli.ljas.response.Response;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * A {@link Router} is an implementation of the {@link HttpHandler} which is required for ljas. Within a router there are
 * different methods to handle HTTP requests by overriding the methods in the overloading class.
 */
public abstract class Router implements HttpHandler {

    /**
     * The handle method is used to process an incoming request on the route where the {@link Router} is
     * listening on. This implementation delegates the request to separate methods based on the HTTP request method.
     *
     * @param exchange the {@link com.sun.net.httpserver.HttpExchange} which contains everything about the request.
     */
    @Override
    public void handle(HttpExchange exchange) {
        Request request = new Request(exchange);
        Response response = new Response(exchange);

        /* switching through the HTTP request method */
        switch (exchange.getRequestMethod()) {
            case "GET":
                get(request, response);
                break;
            case "POST":
                post(request, response);
                break;
            case "PUT":
                put(request, response);
                break;
            case "DELETE":
                delete(request, response);
                break;
        }
    }

    /**
     * The post method handles the GET request method.
     *
     * @param request  the request which contains information about the request
     * @param response the response which is used to respond to the request
     */
    public void get(Request request, Response response) {
    }


    /**
     * The post method handles the POST request method.
     *
     * @param request  the request which contains information about the request
     * @param response the response which is used to respond to the request
     */
    public void post(Request request, Response response) {
    }

    /**
     * The post method handles the PUT request method.
     *
     * @param request  the request which contains information about the request
     * @param response the response which is used to respond to the request
     */
    public void put(Request request, Response response) {
    }

    /**
     * The post method handles the DELETE request method.
     *
     * @param request  the request which contains information about the request
     * @param response the response which is used to respond to the request
     */
    public void delete(Request request, Response response) {
    }
}
