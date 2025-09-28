package com.example.hello.news.repository;

import com.example.hello.news.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// entity 정보가 여기 들어간다
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByName(String name);
}
