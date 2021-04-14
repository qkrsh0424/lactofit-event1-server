package com.eventpage.lactofit.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventpage.lactofit.model.message.Message;
import com.eventpage.lactofit.model.message.StatusEnum;
import com.eventpage.lactofit.service.upload.UploadFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/upload")
@Slf4j
public class UploadFileApiController {
    @Autowired
    UploadFileService uploadFileService;

    // /api/upload/s3/image
    @PostMapping("/s3/image")
    public ResponseEntity<?> FileUploadImageToS3Api(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file") MultipartFile[] files) {
        Message message = new Message();

        try {
            Map<String, String> uploadResult = uploadFileService.uploadToS3Service(files);
            if (uploadResult.get("message").equals("success")) {
                Map<String, String> data = new HashMap<>();
                data.put("message", "success");
                data.put("imageUrl", uploadResult.get("imageUrl"));

                message.setStatus(StatusEnum.OK);
                message.setMessage("success");
                message.setMemo("image upload success");
                message.setData(data);
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else if (uploadResult.get("message").equals("failure")) {
                message.setStatus(StatusEnum.OK);
                message.setMessage("failure");
                message.setMemo("image upload failure");
                return new ResponseEntity<>(message, HttpStatus.OK);

            } else if (uploadResult.get("message").equals("extension_error")) {
                log.error("UploadFileService => upload file extension error");
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("extension_error");
                message.setMemo("UploadFileService => upload file extension error");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else {
                log.error("UploadFileService => upload file to s3 failed");
                message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
                message.setMessage("error");
                message.setMemo("UploadFileService => upload file to s3 failed");
                return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            log.error("UploadFileService => upload file to s3 failed");
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("error");
            message.setMemo("UploadFileService => upload file to s3 failed");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // /api/fileupload/image
    // @PostMapping("/image")
    // public String FileUpload(HttpServletRequest request, HttpServletResponse
    // response,
    // @RequestParam("file") MultipartFile[] files) throws IOException {
    // String url = fileUploadService.upload(files);
    // if (url.equals("FAILURE")) {
    // return "{\"message\":\"failure\"}";
    // }
    // return "{\"message\":\"success\",\"imageUrl\":\"" + url + "\"}";
    // }
}
