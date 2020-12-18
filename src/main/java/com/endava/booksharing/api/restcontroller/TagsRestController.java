package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.service.TagsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagsRestController {
    private final TagsService tagsService;

    @GetMapping("/type/{type}")
    ResponseEntity<Object> getTagsByType(@PathVariable String type) {
        return ResponseEntity.ok(tagsService.getTagsByType(type));
    }

    @GetMapping("/name/{name}")
    ResponseEntity<Object> getTagsByName(@PathVariable String name) {
        return ResponseEntity.ok(tagsService.getTagsByName(name));
    }
}
