package kr.easw.lesson03.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import kr.easw.lesson03.model.dto.AWSKeyDto;
import kr.easw.lesson03.model.dto.ResultDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

@Service
public class AWSService {
    private static final String BUCKET_NAME = "easw-random-bucket-" + UUID.randomUUID();

    public ResultDto testS3(AWSKeyDto awsKey) {
        AmazonS3 client;
        try {
            client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsKey.getApiKey(), awsKey.getApiSecretKey())))
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build();
        } catch (Exception ex) {
            System.err.println("S3 Client Test - Key verification failed");
            return new ResultDto("failed");
        }

        try {
            client.listBuckets();
            System.err.println("S3 Client Test - List permission denial verification failed");
            return new ResultDto("failed");
        } catch (Exception ex) {
            // Ignored
        }
        try {
            client.createBucket(BUCKET_NAME);
            client.putObject(BUCKET_NAME, "test.txt", new File("test.txt"));
            client.deleteObject(BUCKET_NAME, "test.txt");
            client.deleteBucket(BUCKET_NAME);
            client.shutdown();
        } catch (Exception ex) {
            System.err.println("S3 Client Test - Create / Delete Operation Failed");
        }

        return new ResultDto("success");
    }

    public ResultDto testDynamo(AWSKeyDto awsKey) {
        AmazonS3 client;
        try {
            client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsKey.getApiKey(), awsKey.getApiSecretKey())))
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build();
        } catch (Exception ex) {
            System.err.println("Dyanmo Client Test - Key verification failed");
            return new ResultDto("failed");
        }
        try {
            client.listBuckets();
        } catch (Exception ex) {
            System.err.println("Dynamo Client Test - S3 ListBucket Failed");
            ex.printStackTrace();
            return new ResultDto("failed");
        }

        try {
            client.createBucket(BUCKET_NAME);
            client.putObject(BUCKET_NAME, "test.txt", new File("test.txt"));
            client.deleteObject(BUCKET_NAME, "test.txt");
            client.deleteBucket(BUCKET_NAME);
            client.shutdown();
        } catch (Exception ex) {
            System.err.println("Dynamo Client Test - Create / Delete Operation Failed");
            ex.printStackTrace();
            return new ResultDto("failed");
        }

        AmazonDynamoDB dynamoClient;
        DynamoDB db;
        try {
            dynamoClient = AmazonDynamoDBClient.builder()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsKey.getApiKey(), awsKey.getApiSecretKey())))
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build();
            db = new DynamoDB(dynamoClient);
        } catch (Exception ex) {
            System.err.println("Dynamo Client Test - DynamoDB permission check failed");
            return new ResultDto("failed");
        }

        try {
            db.createTable(new CreateTableRequest("test", Arrays.asList(new KeySchemaElement().withAttributeName("test").withKeyType(KeyType.HASH))).withAttributeDefinitions(
                    new AttributeDefinition().withAttributeName("test").withAttributeType(ScalarAttributeType.S)
            ).withProvisionedThroughput(new ProvisionedThroughput()
                    .withReadCapacityUnits(5L)
                    .withWriteCapacityUnits(6L))).waitForActive();
        } catch (Exception ex) {
            System.err.println("Dynamo Client Test - Table creation failed");
            ex.printStackTrace();
            return new ResultDto("failed");
        }
        try {
            db.getTable("test").putItem(new Item().withString("test", "test"));
        } catch (Exception ex) {
            System.err.println("Dynamo Client Test - Table insert failed");
            ex.printStackTrace();
            return new ResultDto("failed");
        }
        dynamoClient.deleteTable("test");
        return new ResultDto("success");
    }


}
