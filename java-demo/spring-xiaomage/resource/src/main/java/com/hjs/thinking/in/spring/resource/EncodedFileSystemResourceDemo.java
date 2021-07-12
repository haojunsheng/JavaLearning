package com.hjs.thinking.in.spring.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * @author 俊语
 * @date 2021/7/11 下午11:32
 */
public class EncodedFileSystemResourceDemo {
    public static void main(String[] args) {
        String currentJavaFilePath = System.getProperty("user.dir") + "/resource/src/main/java/com/hjs/thinking/in/spring/resource/EncodedFileSystemResourceDemo.java";
        // FileSystemResource => WritableResource => Resource
        FileSystemResource fileSystemResource = new FileSystemResource(currentJavaFilePath);
        EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");
        // 字符输入流
        // 字符输入流
        try (Reader reader = encodedResource.getReader()) {
            System.out.println(IOUtils.toString(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
