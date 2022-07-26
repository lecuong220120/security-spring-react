package com.example.demo.repository;

import com.example.demo.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Long> {
    Optional<Poll> findById(Long id);
    Page<Poll> findByCreatedBy(Long id, Pageable pageable);
    long countByCreateBy(Long id);
    List<Poll> findByIdIn(List<Long> id);
    List<Poll> findByIdIn(List<Long> id, Sort sort);
}
