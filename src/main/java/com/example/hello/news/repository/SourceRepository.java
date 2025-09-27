package com.example.hello.news.repository;

import com.example.hello.news.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SourceRepository  extends JpaRepository<Source,Long> {

    Optional<Source> findByName(String name);
}
