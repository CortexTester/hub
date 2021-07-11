package com.cbx.hub.common.storage

import com.cbx.hub.common.utils.logger
import java.net.URL
import java.nio.ByteBuffer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.ResponseBytes
import software.amazon.awssdk.core.exception.SdkClientException
import software.amazon.awssdk.core.exception.SdkServiceException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.core.sync.ResponseTransformer
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse

@Service
class S3StorageService : IStorageService {
    @Autowired
    private lateinit var s3Client: S3Client

    @Value("\${amazon.s3.default-bucket-name}")
    private val bucket: String? = null

    override fun uploadFile(keyName: String, contentType: String, content: ByteArray): String {
        try {
            logger().info("uploading file $keyName to S3")
            val putObjectResult: PutObjectResponse = s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(keyName)
                    .contentType(contentType)
                    .contentLength(content.size.toLong())
                    .build(),
                RequestBody.fromByteBuffer(ByteBuffer.wrap(content))
            )
            val reportUrl: URL =
                s3Client.utilities().getUrl(GetUrlRequest.builder().bucket(bucket).key(keyName).build())
            logger().info("putObjectResult $putObjectResult");
            logger().info("reportUrl $reportUrl");
            return reportUrl.toString();
        } catch (ase: SdkServiceException) {
            logger().error("Caught an AmazonServiceException, for reason: $ase");
            logger().info("Error Message:  ${ase.message}");
            logger().info("Key:   $keyName");
            throw ase;
        } catch (ace: SdkClientException) {
            logger().error("Caught an AmazonClientException, for reason: $ace");
            logger().error("Error Message: $keyName, ${ace.message}");
            throw ace;
        }

    }

    override fun getFile(keyName: String): ByteArray {
        return try {
            logger().info("Retrieving file from S3 for key: $bucket/$keyName")
            val s3Object: ResponseBytes<GetObjectResponse> =
                s3Client.getObjectAsBytes(
                    GetObjectRequest.builder().bucket(bucket).key(keyName).build()
                )
            s3Object.asByteArray()
        } catch (ase: SdkClientException) {
            logger().error("Caught an AmazonServiceException when get $keyName, for reason: $ase")
            throw ase
        } catch (ace: SdkServiceException) {
            logger().error("Caught an AmazonClientException  when get $keyName, for reason: $ace")
            throw ace
        }
    }
}
