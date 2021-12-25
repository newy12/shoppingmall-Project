package com.example.toyproject.service;

import com.example.toyproject.entity.Files;
import com.example.toyproject.repository.FilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilesService {

    private final FilesRepository filesRepository;

    public Files save(Files file) {
        return filesRepository.save(file);
    }
}
