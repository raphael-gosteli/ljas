package com.raphaelgosteli.ljas.middleware;

import com.raphaelgosteli.ljas.request.Request;
import com.raphaelgosteli.ljas.response.Response;

import java.io.File;
import java.nio.file.Path;

public class StaticMiddleware implements Middleware {

    private String path;

    public StaticMiddleware(String baseDir) {
        this.path = baseDir;
    }

    @Override
    public void handle(Request request, Response response) {
        Path path = Path.of(this.path, request.getUri().toString().equals("/") ? "/index.html" : request.getUri().toString());
        File file = new File(path.toString());
        if (file.exists()) {
            response.sendFile(file);
        }
    }
}
