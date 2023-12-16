package com.bobocode.picturestealer.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author "Maksym Oliinyk"
 */

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@Configuration
public class NasaConfig {
}
