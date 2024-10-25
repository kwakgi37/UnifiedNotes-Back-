package com.kbg.Memo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagesRepository extends JpaRepository<Pages, Long> {

    List<Pages> findByUsername(String username);// 사용자의 username으로 페이지 검색

    Optional<Pages> findByUsernameAndId(String username, Long id);
}
