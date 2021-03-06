package io.javac.minoss.minossservice.base;

import io.javac.minoss.minossservice.vertx.VertxRequest;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.Optional;

/**
 * invok Controller method return handler
 *
 * @author pencilso
 * @date 2020/1/23 11:06 下午
 */
public interface VertxControllerHandler extends Handler<RoutingContext> {

    @Override
    default void handle(RoutingContext event) {
        VertxRequest vertxRequest = event.get("vertx_request");
        handle(Optional.ofNullable(vertxRequest).orElseGet(() -> VertxRequest.build(event)));
    }

    void handle(VertxRequest vertxRequest);
}
