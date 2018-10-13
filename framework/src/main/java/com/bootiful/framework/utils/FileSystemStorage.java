package com.bootiful.framework.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileSystemStorage {

    protected final Log LOG = LogFactory.getLog(this.getClass());

    public static final String REGEX_IMAGE = "(.jpg|.jpeg|.gif|.png)";

    public static final String REGEX_AUDIO = "(.3gp|.aac|.m4a|.mp3|.ogg|.wav|.wma|.webm)";

    private final MultipartFile file;

    private Path uploadPath;

    private String outputName;

    private Path outputFilePath;

    private Type type;

    private boolean removeIfIsInvalid;

    private FileSystemStorage(MultipartFile file){
        this.file = file;
        this.type = Type.NONE;
        this.removeIfIsInvalid = false;
    }

    public static FileSystemStorage with(MultipartFile file) {
        return new FileSystemStorage(file);
    }

    public FileSystemStorage to(String uploadPath) {
        this.uploadPath = Paths.get(uploadPath);
        return this;
    }

    public FileSystemStorage as(String outputName) {
        this.outputName = outputName;
        return this;
    }

    public FileSystemStorage type(Type type) {
        this.type = type;
        return this;
    }

    public FileSystemStorage canRemoveIfIsInvalid() {
        this.removeIfIsInvalid = true;
        return this;
    }

    public FileSystemStorage save() throws Exception {

        if(file == null || file.isEmpty()
                || (type == Type.AUDIO && !isAudio())
                || (type == Type.IMAGE && !isImage())){

            return this;

        }

        if (!uploadPath.toFile().exists()) {
            uploadPath.toFile().mkdirs();
        }

        String outputFileName = !StringUtils.hasText(outputName)
                ? file.getOriginalFilename()
                : (outputName + getExtension());

        outputFilePath = uploadPath.resolve(outputFileName);

        Files.copy(file.getInputStream(), outputFilePath, StandardCopyOption.REPLACE_EXISTING);

        LOG.info("Server File Location: " + outputFilePath.toAbsolutePath().toString());

        return this;
    }

    public String getExtension(){
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
    }

    public Path getOutputFilePath(){

        return outputFilePath;
    }

    public void checkValid(Validation validation) throws Exception {

        if(outputFilePath == null){

            throw new Exception("outputFilePath cannot be null");

        }

        if(type == Type.AUDIO
                && validation instanceof AudioValidation){

            checkValidAudio((AudioValidation) validation);

        }

    }

    private void checkValidAudio(AudioValidation audioValidation) throws Exception {

        boolean isValid = false;

        if(FfmpegUtil.isAudio(outputFilePath.toAbsolutePath().toString())){

            int duration = FfmpegUtil.getDuration(outputFilePath.toAbsolutePath().toString());

            isValid = (duration >= audioValidation.getMinDuration()
                    && duration <= audioValidation.getMaxDuration());
        }

        if(!isValid){

            if(removeIfIsInvalid) {

                Files.delete(outputFilePath);

                outputFilePath = null;
            }

            throw new Exception("Invalid audio file");

        }

    }

    public static enum Type {

        NONE,

        IMAGE,

        AUDIO

    }

    public static abstract class Validation {

    }

    public static class AudioValidation extends Validation {
        private int minDuration;
        private int maxDuration;

        public AudioValidation(int minDuration, int maxDuration){
            this.minDuration = minDuration;
            this.maxDuration = maxDuration;
        }

        public int getMinDuration() {
            return minDuration;
        }

        public void setMinDuration(int minDuration) {
            this.minDuration = minDuration;
        }

        public int getMaxDuration() {
            return maxDuration;
        }

        public void setMaxDuration(int maxDuration) {
            this.maxDuration = maxDuration;
        }
    }

    private boolean isAudio(){
        return getExtension().matches(REGEX_AUDIO);
    }

    private boolean isImage(){
        return getExtension().matches(REGEX_IMAGE);
    }

}
