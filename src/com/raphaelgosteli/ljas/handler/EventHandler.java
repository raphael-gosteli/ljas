package com.raphaelgosteli.ljas.handler;

import com.raphaelgosteli.ljas.render.Render;
import com.raphaelgosteli.ljas.view.View;

public interface EventHandler {
    void handle();

    void execute(Render render, View view);
}

