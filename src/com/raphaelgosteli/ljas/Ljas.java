package com.raphaelgosteli.ljas;

import com.raphaelgosteli.ljas.middleware.Middleware;
import com.raphaelgosteli.ljas.router.Router;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * ljas stands for lightweight java "application" server and does exactly this and nothing more. The project is based
 * on the {@link HttpServer} which is built in JDK 11.
 */
public class Ljas {

    private static Map<HttpContext, Middleware> middlewareMap = new HashMap<>();
    private static HttpServer httpServer;

    /**
     * Empty constructor used to do nothing
     */
    public Ljas() {
    }

    /**
     * Constructor used to directly listen on a specified port.
     *
     * @param port the port where the server should listen for connections
     */
    public Ljas(int port) {
        listen(port);
    }

    /**
     * Constructor used to directly listen on a specified port and to set a custom
     * backlog for incoming connections.
     *
     * @param port    the port where the server should listen for connections
     * @param backlog the rate of how many new connections the server can accept. For the system default set the value to 0
     */
    public Ljas(int port, int backlog) {
        listen(port, backlog);
    }

    public static Map<HttpContext, Middleware> getMiddlewares() {
        return middlewareMap;
    }

    public static void registerMiddleware(String route, Middleware middleware) {
        middlewareMap.put(httpServer.createContext(route, new Router() {
        }), middleware);
    }

    /**
     * Creates a new instance of an {@link HttpServer} which listens on the specified port.
     *
     * @param port the port where the server should listen.
     */
    public void listen(int port) {
        listen(port, 0);
    }

    /**
     * Creates a new instance of an {@link HttpServer} which listens on the specified port and sets the backlog of
     * the {@link HttpServer} to the specified value.
     *
     * @param port    the port where the server should listen.
     * @param backlog the rate of how many new connections the server can accept. For the system default set the value to 0
     */
    public void listen(int port, int backlog) {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), backlog);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers a new {@link Router} on the given route. The method creates a new {@link com.sun.net.httpserver.HttpContext}
     * with the route and the router.
     *
     * @param route  the route for the {@link Router}.
     * @param router the {@link Router} which handles the request.
     */
    public HttpContext on(String route, Router router) {
        return httpServer.createContext(route, router);
    }

    /**
     * @param middleware
     */
    public void use(Middleware middleware) {
        use(on("/", new Router() {
        }), middleware);
    }

    /**
     * @param route
     * @param middleware
     */
    public void use(HttpContext route, Middleware middleware) {
        middlewareMap.put(route, middleware);
    }

}