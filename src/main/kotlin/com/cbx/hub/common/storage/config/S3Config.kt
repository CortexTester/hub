package com.cbx.hub.common.storage.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3Config {
    @Value("\${amazon.s3.region}")
    private lateinit var region: String
    @Value("\${amazon.s3.accessKey}")
    private lateinit var accessKey: String
    @Value("\${amazon.s3.secretKey}")
    private lateinit var secretKey: String

    @Bean(destroyMethod = "close")
    fun initS3Client(): S3Client {
        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
            .build();
    }
}
