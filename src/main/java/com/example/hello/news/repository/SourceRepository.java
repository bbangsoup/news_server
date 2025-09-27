package com.example.hello.news.repository;

import com.example.hello.news.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository  extends JpaRepository<Source,Long> {
}
