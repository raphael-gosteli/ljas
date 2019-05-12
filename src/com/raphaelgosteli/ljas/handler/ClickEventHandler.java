package com.raphaelgosteli.ljas.handler;

import com.raphaelgosteli.ljas.Ljas;
import com.raphaelgosteli.ljas.render.Render;
import com.raphaelgosteli.ljas.view.HtmlView;
import com.raphaelgosteli.ljas.view.View;
import org.jsoup.nodes.Element;

abstract public class ClickEventHandler implements EventHandler {

    private Element element;

    public ClickEventHandler(Element element) {
        this.element = element;
    }

    @Override
    public void execute(Render render, View view) {

        HtmlView htmlView = (HtmlView) view;
        htmlView.getDocument().getElementsByAttributeValue("data-ljas", this.element.id()).remove();

        String route = String.format("/ljas/handler/%s/click", this.getElement().id());

        Element scriptElement = new Element("script");
        scriptElement.attr("data-ljas", this.getElement().id());

        String handlerFunction = String.format("function %sClickHandler(){" +
                "$.get('%s', function(data){" +
                "$('body').html(data);" +
                "})}", this.getElement().id(), route);

        scriptElement.append(handlerFunction);

        String clickEventListener = String.format("$('#%s').on('click', function(){%sClickHandler();})", this.getElement().id(), this.getElement().id());

        scriptElement.append(clickEventListener);

        htmlView.getDocument().body().appendChild(scriptElement);

        Ljas.registerMiddleware(route, (request, response) -> {
            this.handle();
            response.send(htmlView.getDocument().body().outerHtml());
        });
    }

    /**
     * Gets element.
     *
     * @return Value of element.
     */
    public Element getElement() {
        return element;
    }

    /**
     * Sets new element.
     *
     * @param element New value of element.
     */
    public void setElement(Element element) {
        this.element = element;
    }
}
