package com.dea42.client;

//import com.dea42.client.model.FaceDetectionResponse;
//import com.dea42.client.model.FaceRecognizeResponse;
//import com.dea42.client.model.FaceRegisterResponse;
import com.dea42.client.model.ObjectDetectionResponse;
//import com.dea42.client.model.SceneDetectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@FeignClient(name = "deepstack", url = "${deepstack.url}", path = "/v1/vision/")
public interface DeepStackClient {

//    @PostMapping(path = "/face/", consumes = MULTIPART_FORM_DATA_VALUE)
//    FaceDetectionResponse faceDetection(@RequestPart("image") MultipartFile image);
//
//    @PostMapping(path = "/face/", consumes = MULTIPART_FORM_DATA_VALUE)
//    FaceDetectionResponse faceDetection(@RequestPart("image") MultipartFile image, @RequestPart("min_confidence") double minConfidence);
//
//    @PostMapping(path = "/face/register", consumes = MULTIPART_FORM_DATA_VALUE)
//    FaceRegisterResponse faceRegister(@RequestPart("image") MultipartFile image, @RequestPart("userid") String name);
//
//    @PostMapping(path = "/face/recognize", consumes = MULTIPART_FORM_DATA_VALUE)
//    FaceRecognizeResponse faceRecognize(@RequestPart("image") MultipartFile image);

    @PostMapping(path = "/detection", consumes = MULTIPART_FORM_DATA_VALUE)
    ObjectDetectionResponse objectDetection(@RequestPart("image") MultipartFile image);

//    @PostMapping(path = "/scene", consumes = MULTIPART_FORM_DATA_VALUE)
//    SceneDetectionResponse sceneDetection(@RequestPart("image") MultipartFile image);

}
