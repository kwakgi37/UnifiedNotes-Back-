package com.kbg.Memo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pages")
public class PagesController {

    private final PagesService pagesService;

    public PagesController(PagesService pagesService) {
        this.pagesService = pagesService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPage(@RequestBody Pages page) {
        if (page.getTitle() == null || page.getTitle().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title cannot be empty");
        }
        Pages newPage = pagesService.addPage(page);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPage);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Pages>> getPagesByUsername(@PathVariable String username) {
        List<Pages> pages = pagesService.getPagesByUsername(username);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<Optional<Pages>> findByIdPages(@PathVariable String username, @PathVariable Long id) {
        Optional<Pages> page = pagesService.findByIdPages(username, id);
        if (page.isPresent()) {
            return ResponseEntity.ok(page);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{username}/{id}")
    public ResponseEntity<Pages> savePage(@PathVariable String username, @PathVariable Long id, @RequestBody Pages page) {
        // URL에서 받은 username과 id를 페이지 객체에 설정
        page.setUsername(username);
        page.setId(id);

        // 페이지 저장
        Pages savedPage = pagesService.addPage(page);

        return ResponseEntity.ok(savedPage);
    }

    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<String> deletePage(@PathVariable String username, @PathVariable Long id) {
        try {
            pagesService.deletePage(username, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 성공 시 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 실패 시 404
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        try {
            // 파일 저장 및 URL 생성
            String fileUrl = pagesService.uploadFile(file); // 서비스 메서드 호출
            return ResponseEntity.ok("{ \"uploaded\": true, \"url\": \"" + fileUrl + "\" }");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/videos/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            String videoUrl = pagesService.uploadVideo(file); // 비디오 파일 처리
            return ResponseEntity.ok("{ \"uploaded\": true, \"url\": \"" + videoUrl + "\" }");
        } catch (IOException e) {
            e.printStackTrace(); // 에러 스택을 콘솔에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Video upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // 다른 예외도 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }


    // 오디오 업로드
    @PostMapping("/audios/upload")
    public ResponseEntity<?> uploadAudio(@RequestParam("file") MultipartFile file) {
        try {
            String audioUrl = pagesService.uploadAudio(file); // 오디오 파일 처리
            return ResponseEntity.ok("{ \"uploaded\": true, \"url\": \"" + audioUrl + "\" }");
        } catch (IOException e) {
            e.printStackTrace(); // 에러 스택을 콘솔에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Audio upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // 다른 예외도 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

}
