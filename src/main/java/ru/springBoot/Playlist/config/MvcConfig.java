package ru.springBoot.Playlist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MvcConfig {
    @Value("${upload.path}")
    private String uploadPath;
    private Path path;

    @PostConstruct
    private void postConstruct() {
        path = Paths.get(uploadPath);
    }

    @GetMapping("/uploadMusic/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable(name = "fileName") String fileName) throws IOException {
        Path filePath = path.resolve(fileName);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
        return ResponseEntity.ok()
                .contentLength(Files.size(filePath))
                .contentType(MediaType.asMediaType(MimeType.valueOf(Files.probeContentType(filePath))))
                .body(resource);
    }
}
