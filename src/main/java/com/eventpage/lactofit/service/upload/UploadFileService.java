package com.eventpage.lactofit.service.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

@Service
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

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public Map<String,String> uploadToS3Service(MultipartFile[] file) throws IOException {
        String imageDir = "/upload/image";
        String bucketPath = bucket + imageDir;
        
        Map<String, String> result = new HashMap<>();

        if(file.length<=0){
            result.put("message", "failure");
            return result;
        }
        List<String> fileUrl = new ArrayList<>();
        for(int i = 0 ; i < file.length; i++){
            // String fileName = new Date().getTime() + file[i].getOriginalFilename();  // 파일 풀 네임
            String originalFileName = file[i].getOriginalFilename();
            if(!isPermissionFileExt(originalFileName)){
                result.put("message", "extension_error");
                return result;
            }

            int pos = file[i].getOriginalFilename().lastIndexOf(".");   // 파일의 마지막 . 인덱스를 기준으로 자른다.
            String ext = file[i].getOriginalFilename().substring( pos + 1 );    // 마지막 . 인덱스를 기준으로 뒷쪽 텍스트를 가져온다.
            String fileName = String.valueOf("Lactofit_"+new Date().getTime())+"-"+((int)(Math.random()*99999)+10000)+"."+ext;  // 최종 파일 네임 : {현재 시간}-{랜덤값}.{확장자명} 

            ObjectMetadata metadata = new ObjectMetadata(); // 에러코드 피하기 위함 : No content length specified for stream data. Stream contents will be buffered in memory and could result in out of memory errors.
            metadata.setContentLength(file[i].getSize());   // 위의 에러코드를 피하기 위해서는 contentLength를 지정해줘야 한다.

            s3Client.putObject(new PutObjectRequest(bucketPath, fileName, file[i].getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.Private))
                    ;

            // System.out.println(s3Client.getUrl(bucketPath, fileName).toString());
            fileUrl.add(s3Client.getUrl(bucketPath, fileName).toString());
        }
        
        result.put("message", "success");
        result.put("imageUrl", fileUrl.get(0));
        // return String.valueOf(file.length);
        return result;
    }

    private boolean isPermissionFileExt( String fileName ){
		
		final String[] PERMISSION_FILE_EXT_ARR = {"GIF", "JPEG", "JPG", "PNG", "APNG", "AVIF", "SVG", "WebP"};
		
		if( !StringUtils.hasText(fileName) ) {
			return false;
		}
		
		String[] fileNameArr = fileName.split("\\.");
		
		if( fileNameArr.length == 0 ) {
			return false;
		}
		
		String ext = fileNameArr[fileNameArr.length - 1].toUpperCase();
		 
		boolean isPermissionFileExt = false;
		
		for( int i = 0; i < PERMISSION_FILE_EXT_ARR.length; i++ ) {
			if( PERMISSION_FILE_EXT_ARR[i].equals(ext) ) {
				isPermissionFileExt = true;
				break;
			}
		}
		
		return isPermissionFileExt;
		
}
}
