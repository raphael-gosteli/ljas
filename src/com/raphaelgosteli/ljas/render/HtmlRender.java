package com.raphaelgosteli.ljas.render;

import com.raphaelgosteli.ljas.handler.EventHandler;
import com.raphaelgosteli.ljas.view.View;

public class HtmlRender implements Render {

    @Override
    public String render(View view) {
        for (EventHandler eventHandler : view.getEventHandlers()) {
            eventHandler.execute(this, view);
        }
        return view.getContent();
    }
}
