package com.poll.config;

import com.poll.client.UserClient;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserClientConfiguration {

    @Value("${application.client.endpoint.consulta-cpf}")
    private String coreUrl;
    private Client client;

    @Autowired
    public UserClientConfiguration(Client client) {
        this.client = client;
    }

    @Bean
    UserClient getCoreClient(){
        return Feign.builder()
                .client(client)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(UserClient.class))
                .logLevel(Logger.Level.BASIC)
                .retryer(Retryer.NEVER_RETRY)
                .target(UserClient.class, coreUrl);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
