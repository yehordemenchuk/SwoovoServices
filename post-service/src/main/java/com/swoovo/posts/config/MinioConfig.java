package com.swoovo.posts.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.swoovo.support.util.MinioUtil;

@Configuration
public class MinioConfig {
    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.secret-key}")
    private String minioSecretKey;

    @Value("${minio.access-key}")
    private String minioAccessKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.expiration}")
    private int minioExpiration;

    @Bean
    public MinioUtil getMinioUtil() {
        return new MinioUtil(bucketName, minioExpiration, getMinioClient());
    }

    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
    }
}
