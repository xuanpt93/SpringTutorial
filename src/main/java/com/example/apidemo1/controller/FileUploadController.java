package com.example.apidemo1.controller;

import com.example.apidemo1.models.ResponseObject;
import com.example.apidemo1.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/FileUpload")
public class FileUploadController {
    //inject
    @Autowired
    private  ImageStorageService imageStorageService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        try {
        String generatedFileNamme = imageStorageService.storeFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK,"Upload file Successfully",generatedFileNamme)
        );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(HttpStatus.NOT_IMPLEMENTED,e.getMessage(),"")
            );
        }
//        c13d4d2bbfcb447aa634e619c7193fff
    }
    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<byte[]> readImageFile(@PathVariable String fileName){

        try {
            byte[] bytes = imageStorageService.readFileContent(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception e){
            return ResponseEntity.noContent().build();

        }
    }
    @GetMapping("")
    public ResponseEntity<ResponseObject> getUploaderFile(){
        try{
            List<String> urls = imageStorageService.loadAll()
                    .map(path -> {
                        //convert fileName to url(send request readImageFile)
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readImageFile",path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("List file successfully",urls));
        }catch (Exception e){
            return ResponseEntity.ok(
                    new ResponseObject("List file failed",new String[]{})
            );
        }
    }

}
