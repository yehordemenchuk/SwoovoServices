package org.swoovo.support.util;

import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.swoovo.support.exception.FileStorageException;
import org.swoovo.support.util.helpers.MinioAction;
import org.swoovo.support.util.helpers.MinioNonResultAction;

import java.io.InputStream;

@RequiredArgsConstructor
public class MinioUtil {
    private final String bucketName;

    private final int minioExpiration;

    private final MinioClient minioClient;

    public Iterable<Result<Item>> listFiles(String bucketName) throws FileStorageException {
        return executeMinioAction(minioClient::listObjects,
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .build());
    }

    public String downloadFile(String fileName) throws FileStorageException {
        return executeMinioAction(minioClient::getPresignedObjectUrl,
                GetPresignedObjectUrlArgs.builder()
                        .method(Http.Method.GET)
                        .bucket(bucketName)
                        .expiry(minioExpiration)
                        .object(fileName)
                        .build()
        );
    }

    public void uploadFile(String objectName, InputStream stream, long size, String contentType)
            throws FileStorageException {
        createBucketIfNotExists();

        executeMinioAction(minioClient::putObject, PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(stream, size, -1L)
                .contentType(contentType)
                .build());
    }

    private void createBucketIfNotExists() throws FileStorageException {
        boolean found = executeMinioAction(minioClient::bucketExists, BucketExistsArgs
                .builder()
                .bucket(bucketName)
                .build()
        );

        if (!found)
            executeMinioNonResultAction(minioClient::makeBucket, MakeBucketArgs
                    .builder().bucket(bucketName).build());
    }

    private <T, R> R executeMinioAction(MinioAction<T, R> minioAction, T arg) throws FileStorageException {
        try {
            return minioAction.execute(arg);
        } catch (Exception e) {
            throw new FileStorageException(e.getMessage());
        }
    }

    private <T> void executeMinioNonResultAction(MinioNonResultAction<T> minioNonResultAction,
                                                 T arg) throws FileStorageException {
        try {
            minioNonResultAction.execute(arg);
        } catch (Exception e) {
            throw new FileStorageException(e.getMessage());
        }
    }
}
