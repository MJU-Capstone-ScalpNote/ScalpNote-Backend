package com.scalpnote.domain.user.domain.repository;

import com.scalpnote.domain.user.domain.Model6Result;
import com.scalpnote.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Model6ResultRepository extends JpaRepository<Model6Result, Long> {
    List<Model6Result> findByUser(User user);
}
