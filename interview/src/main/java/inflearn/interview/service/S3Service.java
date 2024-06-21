package inflearn.interview.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import inflearn.interview.domain.dto.UrlReturnDTO;
import inflearn.interview.domain.dto.VideoNameDTO;
import inflearn.interview.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public UrlReturnDTO getPresignedUrl(VideoNameDTO dto) {
        try {
            GeneratePresignedUrlRequest videoRequest = generatePresignedUrlRequest(dto.getVideoName());
            GeneratePresignedUrlRequest thumbnailRequest = generatePresignedUrlRequest(dto.getThumbnailName());

            String videoUrl = amazonS3.generatePresignedUrl(videoRequest).toString();
            String thumbnailUrl = amazonS3.generatePresignedUrl(thumbnailRequest).toString();

            return new UrlReturnDTO(videoUrl, thumbnailUrl);
        } catch (SdkClientException e) {
            throw new S3Exception();
        }
    }

    private Date getExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    private GeneratePresignedUrlRequest generatePresignedUrlRequest(String name) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, name)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getExpiration());

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        return generatePresignedUrlRequest;
    }

    public void deleteVideo(String videoLink, String thumbnailLink) {
        try {
            String[] videoSplit = videoLink.split("amazonaws[.]com/");
            String[] thumbnailSplit = thumbnailLink.split("amazonaws[.]com/");
            DeleteObjectsRequest multiRequest = new DeleteObjectsRequest(bucket).withKeys(videoSplit[1], thumbnailSplit[1]);
            amazonS3.deleteObjects(multiRequest);
        } catch (SdkClientException e) {
            throw new S3Exception();
        }
    }
}
