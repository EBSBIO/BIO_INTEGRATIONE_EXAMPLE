package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import ru.rtlabs.ebs.reference.receiver.config.ReferenceReceiverConfig;
import ru.rtlabs.ebs.reference.receiver.handlers.base.CheckAuthHeaderHandler;
import ru.rtlabs.ebs.reference.receiver.handlers.base.CookieSessionHandler;
import ru.rtlabs.ebs.reference.receiver.handlers.base.FailureHandler;
import ru.rtlabs.ebs.reference.receiver.handlers.base.MultipartBodyHandler;
import ru.rtlabs.ebs.reference.receiver.handlers.base.SuccessHandler;
import ru.rtlabs.ebs.reference.receiver.marshalling.MatcherRequest;
import ru.rtlabs.ebs.reference.receiver.marshalling.NotifyRequest;
import ru.rtlabs.ebs.reference.receiver.service.ServicePathPattern;
import ru.rtlabs.ebs.reference.receiver.service.jwt.ValidatorDeactivation;
import ru.rtlabs.ebs.reference.receiver.service.jwt.ValidatorUpload;

/**
 * Класс для инициализации роутов для различных запросов.
 */
public class ApiRouter {

  private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
  private static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";

  private ApiRouter() {
  }

  public static Router createApiRouter(Vertx vertx,
                                       ReferenceReceiverConfig referenceReceiverConfig) {
    Router apiRouter = Router.router(vertx);
    apiRouter.post().handler(LoggerHandler.create());
    idpNotifierHandlers(apiRouter, referenceReceiverConfig);
    matcherNotifierHandlers(apiRouter, referenceReceiverConfig);
    bioskudRouterDeactivationHandlers(apiRouter, referenceReceiverConfig);
    bioskudRouterUploadHandlers(apiRouter, referenceReceiverConfig);
    return apiRouter;
  }

  /**
   * Обработка уведомлений о создании УЗ.
   */
  private static void idpNotifierHandlers(Router router,
                                          ReferenceReceiverConfig referenceReceiverConfig) {
    router.post(ServicePathPattern.IDP_NOTIFIER.value)
          .consumes(CONTENT_TYPE_APPLICATION_JSON)
          .handler(new CheckAuthHeaderHandler(referenceReceiverConfig.authBearer()))
          .handler(BodyHandler.create())
          .handler(new BodyParserHandler<>(NotifyRequest.class))
          .handler(new SuccessHandler())
          .failureHandler(new FailureHandler());
  }

  /**
   * Обработка уведомлений о мэтчинге УЗ.
   */
  private static void matcherNotifierHandlers(Router router,
                                              ReferenceReceiverConfig referenceReceiverConfig) {
    router.post(ServicePathPattern.MATCHER_NOTIFIER.value)
          .consumes(CONTENT_TYPE_APPLICATION_JSON)
          .handler(new CheckAuthHeaderHandler(referenceReceiverConfig.authBearer()))
          .handler(BodyHandler.create())
          .handler(new BodyParserHandler<>(MatcherRequest.class))
          .handler(new SuccessHandler())
          .failureHandler(new FailureHandler());

  }

  /**
   * Обработка запроса деактивации шаблонов.
   */
  private static void bioskudRouterDeactivationHandlers(Router router,
                                                        ReferenceReceiverConfig referenceReceiverConfig) {
    router.post(ServicePathPattern.BIOSKUD_ROUTER_DEACTIVATION.value)
          .consumes(CONTENT_TYPE_TEXT_PLAIN)
          .handler(new CookieSessionHandler())
          .handler(new CheckAuthHeaderHandler(referenceReceiverConfig.authBearer()))
          .handler(BodyHandler.create())
          .handler(new SaveJWTHandler())
          .handler(new BioskudRouterJwtParserHandler<>(new ValidatorDeactivation()))
          .handler(new SuccessHandler())
          .failureHandler(new FailureHandler());

  }

  /**
   * Обработка запроса выгрузки шаблонов.
   */
  private static void bioskudRouterUploadHandlers(Router router,
                                                  ReferenceReceiverConfig referenceReceiverConfig) {
    router.post(ServicePathPattern.BIOSKUD_ROUTER_UPLOAD.value)
          .handler(new CheckAuthHeaderHandler(referenceReceiverConfig.authBearer()))
          .handler(new CookieSessionHandler())
          .handler(BodyHandler.create())
          .handler(new MultipartBodyHandler())
          .handler(new BioskudRouterJwtParserHandler<>(new ValidatorUpload()))
          .handler(new BioskudRouterValidateVectorsHandler())
          .handler(new SuccessHandler())
          .failureHandler(new FailureHandler());

  }

}
