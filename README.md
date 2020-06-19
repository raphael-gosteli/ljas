> :warning: **An experimental project, which might inspire you (or not)**

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

### HtmlView, HtmlRender and EventHandler
The best way to server dynamic html files is to use the `HtmlView` Class which parses the Html file and gives you access to DOM. On the HtmlView you can register server-side EventHandler and within this Handler you can manipulte the DOM on the client side. The `HtmlRender` is the render engine for HtmlView's and is used when rendering the response using the `render` method.
```java
ljas.on("/", new Router() {
    @Override
    public void get(Request request, Response response) {
        HtmlView htmlView = new HtmlView("static\\index.html");

        Element title = htmlView.getDocument().getElementById("title"); // make sure to have an element exists
        title.text("New title"); // change the text of the element with the id title

        /* register click handler on the title element */
        htmlView.addEventHandler(new ClickEventHandler(title) {
            @Override
            public void handle() {
                System.out.println("Button clicked!");
                getElement().text("Again new title");
                getElement().parent().appendChild(new Element("p").text("Changed on the server!"));
            }
        });

        response.render(new HtmlRender(), htmlView);
    }
});
```
