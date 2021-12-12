package com.example.toyproject.repository;


import com.example.toyproject.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<Files,Long> {
}
