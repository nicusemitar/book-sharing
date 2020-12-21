package com.endava.booksharing.service;


import com.endava.booksharing.api.dto.FileInfoDto;
import com.endava.booksharing.api.restcontroller.FileController;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.repository.BookRepository;
import com.endava.booksharing.utils.exceptions.InvalidFormatFile;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import com.endava.booksharing.utils.exceptions.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {
    private final Path root = Paths.get("uploads");
    private final BookRepository bookRepository;

    public void init() {
        try {
            Files.getFileStore(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public FileInfoDto save(MultipartFile file, Long bookID) throws InvalidFormatFile, IOException {
        String format = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);
        if (!format.equals(".png"))
            if (!format.equals(".jpg"))
                throw new InvalidFormatFile("invalid file format format: " + format);

        Book book = bookRepository.findById(bookID).orElseThrow(() -> {
            log.warn("Book with id [{}] is not present in database", bookID);
            return new NotFoundException("Book with id" + bookID + " was not found in database");
        });

        String fileName = "book_" + bookID + format;
        try {
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
        } catch (Exception e) {
            throw new StorageException("Could not store the file. Error: " + e.getMessage());
        }
        Path path = root.resolve(fileName);
        String url = MvcUriComponentsBuilder
                .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
        book.setBookImageUrl(url);
        bookRepository.save(book);
        return new FileInfoDto(path.getFileName().toString(), url);
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file! or file not found");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public String update(MultipartFile file, Long bookID) throws IOException, InvalidFormatFile {
        deleteImageByBookId(bookID);
        this.save(file, bookID);
        return "Photo updated for book with id: " + bookID;
    }

    public void deleteImageByBookId(Long bookID) {
        Path imagesPng = Paths.get("uploads", "book_" + bookID + ".png");
        Path imagesJpg = Paths.get("uploads", "book_" + bookID + ".jpg");
        FileSystemUtils.deleteRecursively(imagesPng.toFile());
        FileSystemUtils.deleteRecursively(imagesJpg.toFile());
    }
}
