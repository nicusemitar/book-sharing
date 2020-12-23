package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.FileInfoDto;

import java.util.ArrayList;
import java.util.List;


public class FileInfoUtils {
    public static List<FileInfoDto> EXPECTED_FILE_INFO() {
        List<FileInfoDto> fileJson = new ArrayList<>();

        fileJson.add(FileInfoDto.builder().name("first.txt").url("http://localhost/book/files/first.txt").build());
        fileJson.add(FileInfoDto.builder().name("second.txt").url("http://localhost/book/files/second.txt").build());
        return fileJson;
    }

    public static final FileInfoDto EXPECTED_FILE_INFO_1() {
        return FileInfoDto.builder().name("first.txt").url("http://localhost/book/files/first.txt").build();
    }
    public static final FileInfoDto EXPECTED_FILE_INFO_2() {
        return FileInfoDto.builder().name("test.jpg").url("http://localhost/book/files/test.jpg").build();
    }

}

