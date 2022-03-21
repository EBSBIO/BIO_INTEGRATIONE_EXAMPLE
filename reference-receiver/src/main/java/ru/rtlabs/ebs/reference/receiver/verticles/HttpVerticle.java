package ru.rtlabs.ebs.reference.receiver.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.config.ReferenceReceiverConfig;
import ru.rtlabs.ebs.reference.receiver.handlers.ApiRouter;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.service.ServicePathPattern;
import ru.rtlabs.ebs.reference.receiver.service.utils.Status;


public class HttpVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpVerticle.class);
  private final ReferenceReceiverConfig configuration;
  private HttpServer server = null;

  public HttpVerticle(ReferenceReceiverConfig config) {
    this.configuration = config;
  }

  @Override
  public void start() {
    LOGGER.info(Messages.START_SERVER.message);
    int port = configuration.serverPort();

    server = vertx.createHttpServer(new HttpServerOptions().setPort(port));

    Router router = Router.router(vertx);

    router.get(ServicePathPattern.HEALTH.value)
          .handler(event -> event.response().setStatusCode(Status.OK.getCode()).end());

    router.mountSubRouter("/", ApiRouter.createApiRouter(vertx, configuration));

    server.requestHandler(router);
    server.exceptionHandler(
        throwable -> LOGGER.error(throwable.getMessage(), throwable));
    server.listen();

    LOGGER.info(Messages.INIT_SERVER.message, port);
  }

  @Override
  public void stop() {
    try {
      if (server != null) {
        server.close();
      }
    } catch (Exception e) {
      LOGGER.error(e.toString(), e);
    }
  }
}
