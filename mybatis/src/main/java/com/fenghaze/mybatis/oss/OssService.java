package com.fenghaze.mybatis.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.OSSAsyncClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class OssService implements AutoCloseable {

    @Value("${oss.region}")
    private String region;

    @Value("${oss.bucket-name}")
    private String bucketName;

    @Value("${oss.endpoint:}")
    private String endpoint;

    private OSSAsyncClient client;

    public OssService() {
    }

    @PostConstruct
    public void init() {
        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncClientBuilder builder = OSSAsyncClient.newBuilder()
                .region(region)
                .credentialsProvider(provider);
        if (endpoint != null && !endpoint.isEmpty()) {
            builder.endpoint(endpoint);
        }
        this.client = builder.build();
        log.info("OSS client initialized, region={}, bucket={}", region, bucketName);
    }

    /**
     * 上传本地文件到 OSS
     *
     * @param key      对象键
     * @param filePath 本地文件路径
     * @return PutObjectResult
     */
    public PutObjectResult uploadFile(String key, String filePath) throws Exception {
        Path path = Paths.get(filePath);
        byte[] data = Files.readAllBytes(path);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(key)
                .body(BinaryData.fromBytes(data))
                .build();
        return client.putObjectAsync(request).get();
    }

    /**
     * 从 OSS 下载文件到本地
     *
     * @param key          对象键
     * @param downloadPath 本地保存路径
     */
    public void downloadFile(String key, String downloadPath) throws Exception {
        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(key)
                .build();
        GetObjectResult result = client.getObjectAsync(request).get();
        Path target = Paths.get(downloadPath);
        Files.createDirectories(target.getParent());
        try (InputStream in = result.body();
             FileOutputStream out = new FileOutputStream(target.toFile())) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
    }

    /**
     * 从 OSS 下载文件内容为字符串
     *
     * @param key 对象键
     * @return 文件内容字符串
     */
    public String downloadString(String key) throws Exception {
        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(key)
                .build();
        GetObjectResult result = client.getObjectAsync(request).get();
        try (InputStream in = result.body();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toString();
        }
    }

    /**
     * 获取 OSS 对象的公开访问 URL
     *
     * @param objectKey 对象键
     * @return 可公开访问的图片 URL
     */
    public String getObjectUrl(String objectKey) {
        return "https://" + bucketName + ".oss-" + region + ".aliyuncs.com/" + objectKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    @Override
    @PreDestroy
    public void close() {
        if (client != null) {
            try {
                client.close();
                log.info("OSS client closed");
            } catch (Exception e) {
                log.error("Failed to close OSS client", e);
            }
        }
    }
}
