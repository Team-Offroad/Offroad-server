package sites.offload.external.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sites.offload.common.config.AwsConfig;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;

@Component
@Slf4j
public class S3Service {

    private final S3Presigner presigner;
    private final String bucketName;

    public S3Service(@Value("${aws-property.s3-bucket-name}") final String bucketName, AwsConfig awsConfig) {
        this.bucketName = bucketName;
        this.presigner = awsConfig.getPresigner();
    }

    public String getPresignUrl(final String filename) {
        if (filename == null || filename.equals("")) {
            return null;
        }

        final GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();

        final GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5)) // 5분간 접근 허용
                .getObjectRequest(getObjectRequest)
                .build();

        final PresignedGetObjectRequest presignedGetObjectRequest = presigner
                .presignGetObject(getObjectPresignRequest);

        //presigned url 반환
        final String url = presignedGetObjectRequest.url().toString();

        presigner.close();
        log.info(url);
        return url;
    }

}
