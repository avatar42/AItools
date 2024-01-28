package com.dea42.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@NoArgsConstructor
public class DeepStackConfiguration {

    @Configuration
    @EnableFeignClients(clients = DeepStackClient.class)
    public static class DeepStackFeignConfiguration {
    }
}
