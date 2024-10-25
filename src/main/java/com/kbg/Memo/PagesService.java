package com.kbg.Memo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PagesService {

    private final PagesRepository pagesRepository;

    @Autowired
    public PagesService(PagesRepository pagesRepository) {
        this.pagesRepository = pagesRepository;
    }

    public Pages addPage(Pages page){
        return pagesRepository.save(page);
    }

    public List<Pages> getPagesByUsername(String username) {
        return pagesRepository.findByUsername(username);
    }

    public Optional<Pages> findByIdPages(String username, Long id) {
        return pagesRepository.findByUsernameAndId(username, id);
    }

    public void deletePage(String username, Long id) {
        Optional<Pages> page = pagesRepository.findByUsernameAndId(username, id);
        if (page.isPresent()) {
            pagesRepository.delete(page.get()); // 엔티티가 존재하면 삭제
        } else {
            throw new RuntimeException("페이지가 존재하지 않습니다."); // 존재하지 않으면 예외 발생
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("파일이 비어 있습니다.");
        }

        // 파일 저장 디렉토리 생성 (없으면)
        String uploadDir = "C:/Users/asbck/OneDrive/바탕 화면/Memo/Memo/src/main/resources/static/uploads/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // 파일 이름 생성 (UUID 사용)
        String uniqueFilename = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));

        // 파일 저장
        File destinationFile = new File(uploadDir + uniqueFilename);
        file.transferTo(destinationFile);

        // 저장된 파일의 URL 반환
        return "http://localhost:8080/uploads/" + uniqueFilename;
    }

    // 비디오 업로드 전용 메서드
    public String uploadVideo(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("비디오 파일이 비어 있습니다.");
        }

        // 비디오 파일 저장 디렉토리 확인 및 생성 (절대 경로 사용)
        String uploadDir = "C:/Users/asbck/OneDrive/바탕 화면/Memo/Memo/src/main/resources/static/videos/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs(); // 디렉토리가 없으면 생성
        }

        // 파일 이름 생성 (UUID 사용)
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // 파일 저장
        File destinationFile = new File(uploadDir + uniqueFilename);
        file.transferTo(destinationFile);

        // 저장된 파일의 절대 URL을 데이터베이스에 저장
        return "http://localhost:8080/videos/" + uniqueFilename; // 절대 경로로 변경
    }

    // 비디오 업로드 전용 메서드
    public String uploadAudio(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("오디오 파일이 비어 있습니다.");
        }

        // 비디오 파일 저장 디렉토리 확인 및 생성 (절대 경로 사용)
        String uploadDir = "C:/Users/asbck/OneDrive/바탕 화면/Memo/Memo/src/main/resources/static/audios/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs(); // 디렉토리가 없으면 생성
        }

        // 파일 이름 생성 (UUID 사용)
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // 파일 저장
        File destinationFile = new File(uploadDir + uniqueFilename);
        file.transferTo(destinationFile);

        // 저장된 파일의 절대 URL을 데이터베이스에 저장
        return "http://localhost:8080/audios/" + uniqueFilename; // 절대 경로로 변경
    }
}
