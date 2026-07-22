package com.vikash.Ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    private final S3Client s3Client;


//     Upload file to S3 and return the public URL.
    public String uploadFile(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename()
                .replaceAll("\\s+", "-");
        String key = "products/" + UUID.randomUUID() + "-" + originalFileName;

        PutObjectRequest request = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())
                        .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(file.getBytes())
        );

        return String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                region,
                key
        );
    }


//     Delete file from S3 using its URL.

    public void deleteFile(String imageUrl) {

        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }
        String key = extractKey(imageUrl);
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

        s3Client.deleteObject(request);
    }

//    Extract object key from S3 URL.
    private String extractKey(String imageUrl) {
        URI uri = URI.create(imageUrl);
        return uri.getPath().substring(1);
    }

}
