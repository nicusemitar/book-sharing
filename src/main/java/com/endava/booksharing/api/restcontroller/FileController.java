package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.dto.FileInfoDto;
import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.StorageService;
import com.endava.booksharing.utils.exceptions.InvalidFormatFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class FileController {
    private final StorageService storageService;


    @GetMapping("/files")
    public ResponseEntity<List<FileInfoDto>> getListFiles() {
        List<FileInfoDto> fileInfoDtos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
            return new FileInfoDto(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfoDtos);
    }

    @PostMapping("/{bookID}/update-photo")
    public ResponseEntity handleFileUpdate(@RequestParam("file") MultipartFile file, @PathVariable Long bookID) throws InvalidFormatFile, IOException {
        return ResponseEntity.ok().body(Response.build(storageService.update(file, bookID)));
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
