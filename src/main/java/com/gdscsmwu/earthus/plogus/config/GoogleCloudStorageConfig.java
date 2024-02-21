package com.gdscsmwu.earthus.plogus.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class GoogleCloudStorageConfig {

    @Bean
    public Storage storage() throws IOException {

//        ClassPathResource resource = new ClassPathResource("oceanic-craft-412007-8dee390f63d1.json");
        ClassPathResource resource = new ClassPathResource("elegant-wavelet-414706-86dd47be39a7.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
//        String projectId = "oceanic-craft-412007";
        String projectId = "elegant-wavelet-414706";

        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();

    }

}