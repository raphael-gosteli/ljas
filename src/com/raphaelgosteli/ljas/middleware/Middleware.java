package com.raphaelgosteli.ljas.middleware;

import com.raphaelgosteli.ljas.request.Request;
import com.raphaelgosteli.ljas.response.Response;

public interface Middleware {
    void handle(Request request, Response response);
}
