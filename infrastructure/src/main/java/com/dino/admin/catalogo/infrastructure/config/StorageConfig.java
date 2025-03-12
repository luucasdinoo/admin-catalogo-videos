package com.dino.admin.catalogo.infrastructure.config;

import com.dino.admin.catalogo.infrastructure.config.properties.google.GoogleStorageProperties;
import com.dino.admin.catalogo.infrastructure.config.properties.storage.StorageProperties;
import com.dino.admin.catalogo.infrastructure.services.StorageService;
import com.dino.admin.catalogo.infrastructure.services.impl.GoogleCloudStorageService;
import com.google.cloud.storage.Storage;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StorageConfig {

    @Bean
    @ConfigurationProperties("storage.catalogo-videos")
    public StorageProperties storageProperties(){
        return new StorageProperties();
    }

    @Bean(name = "storageService")
    @Profile({"development, production"})
    public StorageService googleCloudStorageService(
            final GoogleStorageProperties props,
            final Storage storage
            ) {
        return new GoogleCloudStorageService(props.getBucket(), storage);
    }
}
