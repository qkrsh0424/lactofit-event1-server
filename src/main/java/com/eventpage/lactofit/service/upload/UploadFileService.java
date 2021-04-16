package com.eventpage.lactofit.service.upload;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UploadFileService {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region).build();
    }

    public Map<String, String> uploadToS3Service(MultipartFile file) throws IOException {
        String imageDir = "/upload/image";
        String bucketPath = bucket + imageDir;
                
        Map<String, String> result = new HashMap<>();

        // 풀 네임
        String originalFileName = file.getOriginalFilename();
        if (!isPermissionFileExt(originalFileName)) {
            result.put("message", "extension_error");
            return result;
        }

        String fileName = getFileName(originalFileName);
        String fileUrl = "http://"+bucketPath+"/"+fileName;

        if(postImage2S3(bucketPath, fileName, file)){
            result.put("message", "success");
            result.put("imageUrl", fileUrl);
            result.put("imageName", fileName);
        }else{
            result.put("message", "failure");
        }
        return result;
    }

    private String getFileName(String fileFullName){
        String fileRootName = "Lactofit_";
        int pos = fileFullName.lastIndexOf("."); // 파일의 마지막 . 인덱스를 기준으로 자른다.
        String ext = fileFullName.substring(pos + 1); // 마지막 . 인덱스를 기준으로 뒷쪽 텍스트를 가져온다.
        String fileName = String.valueOf(fileRootName + new Date().getTime()) + "-"
                + ((int) (Math.random() * 99999) + 10000) + "." + ext; // 최종 파일 네임 : {현재 시간}-{랜덤값}.{확장자명}
        return fileName;
    }

    private Boolean postImage2S3(String bucketPath, String fileName, MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata(); // 에러코드 피하기 위함 : No content length specified for stream data.
                                                        // Stream contents will be buffered in memory and could result
                                                        // in out of memory errors.
        metadata.setContentLength(file.getSize()); // 위의 에러코드를 피하기 위해서는 contentLength를 지정해줘야 한다.

        try {
            s3Client.putObject(new PutObjectRequest(bucketPath, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.Private));
        } catch (IOException e) {
            log.error("==ERROR UploadFileService : postImage2S3 => {}.==","maybe cause by file.getInputStream, S3 upload failed");
            return false;
        }
        return true;
    }

    private boolean isPermissionFileExt(String fileName) {

        final String[] PERMISSION_FILE_EXT_ARR = { "GIF", "JPEG", "JPG", "PNG", "APNG", "AVIF", "SVG", "WebP" };

        if (!StringUtils.hasText(fileName)) {
            return false;
        }

        String[] fileNameArr = fileName.split("\\.");

        if (fileNameArr.length == 0) {
            return false;
        }

        String ext = fileNameArr[fileNameArr.length - 1].toUpperCase();

        boolean isPermissionFileExt = false;

        for (int i = 0; i < PERMISSION_FILE_EXT_ARR.length; i++) {
            if (PERMISSION_FILE_EXT_ARR[i].equals(ext)) {
                isPermissionFileExt = true;
                break;
            }
        }

        return isPermissionFileExt;

    }
}
