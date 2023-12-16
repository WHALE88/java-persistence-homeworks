package com.bobocode.picturestealer.configuration;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * @author "Maksym Oliinyk"
 */

@Configuration
public class NasaConfig {

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                        .codecs(configurer -> configurer.defaultCodecs()
                                                        .maxInMemorySize(16 * 1024 * 1024))
                        .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                        .build();
    }

    private HttpClient getHttpClient() {
        return HttpClient.create()
                         .followRedirect(true)
                         .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                                 5000)
                         .responseTimeout(Duration.ofMillis(5000));
    }

}
