package com.image.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

@Configuration
public class ImageControllerConfig {

  @Value("${cloud.aws.region.static}")
  private String region;

  @Value("${cloud.aws.credentials.accessKey}")
  private String awsAccessKeyId;

  @Value("${cloud.aws.credentials.secretKey}")
  private String awsSecretAccessKey;

  @Bean
  public RekognitionClient rekognitionClient() {
    return RekognitionClient.builder()
        .region(Region.of(region))
        .httpClientBuilder(UrlConnectionHttpClient.builder())
        .build();
  }

  @Bean
  public S3Client s3Client() {
    return S3Client.builder()
        .region(Region.of(region))
        .httpClientBuilder(UrlConnectionHttpClient.builder())
        .build();
  }

  @Bean
  public DynamoDbClient dynamoDbClient() {
    return DynamoDbClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey)))
            .build();
  }

}
