package com.example.apidemo1.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements IStorageService{

    private final Path storageFolder = Paths.get("uploads");

    public  ImageStorageService() {
        //inject only one then project run
        try{
            Files.createDirectories(storageFolder);
        }catch (Exception e){
            throw new RuntimeException("Cannot initilize storage", e);
        }
    }

    private boolean isImageFile(MultipartFile file){

        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png","jpg","jpeg","bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile file) {
        try{
            if (file.isEmpty()){
                throw new RuntimeException("Failed to store empty file");
            }
            if(!isImageFile(file)){
                throw new RuntimeException("You can only upload image file");
            }
            float fileSizeImage = file.getSize()/1000000.0f;
            if(fileSizeImage > 7.0f){
                throw new RuntimeException("File must be <= 7Mb");
            }
            //phai doi ten file truoc khi day len server
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-","");
            generatedFileName = generatedFileName+"."+fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(
                    Paths.get(generatedFileName)).normalize().toAbsolutePath();

            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw new RuntimeException("Cannot store file outside current directory");
            }
            //if file name already exits then override
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        }catch (IOException exception){
            throw new RuntimeException("Failed to store file",exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        //load file from folder jave use stream
        try{
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        }catch (IOException e){
            throw new RuntimeException("Failed to load stored file",e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }else {
                throw new RuntimeException("Could not read file: "+fileName);
            }
        }catch (IOException exception){
            throw new RuntimeException("Could not read file: "+fileName,exception);
        }
    }

    @Override
    public void deleteAllFile() {

    }
}
