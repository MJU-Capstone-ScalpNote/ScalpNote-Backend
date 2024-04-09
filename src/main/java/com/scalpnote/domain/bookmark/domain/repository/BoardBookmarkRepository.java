package com.scalpnote.domain.bookmark.domain.repository;

import com.scalpnote.domain.bookmark.domain.BoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardBookmarkRepository extends JpaRepository<BoardBookmark, Long> {
}
