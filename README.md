# ljas
ljas is a simple web server for Java based on the built-in HttpServer.
The goal of this project is to create a simplified alternative for java web development with application servers while maintaining some of the capabilities.

# Getting started
## Get the library
You can download a jar file from the current releases or clone the project and build it yourself.
## Create a new server

```java
Ljas ljas = new Ljas();
ljas.listen(3000);

/* or */

Ljas ljas = new Ljas(3000);
```

## Register a route

You can do this by calling the (express.js like) `on` method. Which links a route to an instance of the `Router` class. You can either create the instance inline or create a new class which extends the `Router` class.

### Inline
```java
Ljas ljas = new Ljas(3000);
ljas.on("/", new Router() {
    @Override
    public void get(Request request, Response response) {
        // handle the get request
    }
});
```
### Class
```java
// IndexRouter.java

class IndexRouter extends Router {

    @Override
    public void get(Request request, Response response) {
        // handle the get request
    }
}

// Main.java
...
ljas.on("/", new IndexRouter());
...
```
## Handle a request
```java
...
ljas.on("/", new Router() {
    @Override
    public void get(Request request, Response response) {
        String name = request.getParameter("name");
        response.send("Your name is: " + name);
    }
});
```
Now you can start the program and visit the page [http://localhost:3000/?name=Foo](http://localhost:3000/?name=Foo) in your browser.

### Sending a file
The response also allows you to send files as the response e.g. an HTML website. You can simply do that by using the method `sendFile`.
```java
...
ljas.on("/", new Router() {
    @Override
    public void get(Request request, Response response) {
        response.sendFile("src\\com\\example\\myproject\\index.html"); // depending on the IDE the relative paths can change
    }
});
```

### Serve static files
To serve static files like stylesheets or JavaScript Files you can use a Middleware called StaticMiddleware which does excatly this.
The Middleware looks for the requested file in a specified folder. If the file exists the Middlware will send the content of the file to the client otherwise the Router will look if there is and alternative handler for the request. Thanks to this feature you can serve static and dynamic files on the same base route.

```java
Ljas ljas = new Ljas(3000);
ljas.use(ljas.on("/", new Router() {}), new StaticMiddleware("path\\to\\static\\directory"));
```


