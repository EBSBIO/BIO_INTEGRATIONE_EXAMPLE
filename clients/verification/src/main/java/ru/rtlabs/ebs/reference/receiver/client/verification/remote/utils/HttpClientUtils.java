package ru.rtlabs.ebs.reference.receiver.client.verification.remote.utils;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClientUtils {
  private HttpClientUtils() {
  }

  /**
   * Формирование HTTP клиента для выполнения запросов.
   *
   * @return HTTP клиент.
   */
  public static OkHttpClient buildClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.level(HttpLoggingInterceptor.Level.BASIC);

    return new OkHttpClient().newBuilder()
                             .readTimeout(100, TimeUnit.SECONDS)
                             .followRedirects(false)
                             .followSslRedirects(false)
                             .addInterceptor(logging)
                             .build();
  }
}
