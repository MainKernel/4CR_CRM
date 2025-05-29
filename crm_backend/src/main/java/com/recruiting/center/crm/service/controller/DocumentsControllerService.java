package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.entity.DocumentDto;
import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.entity.candidate.CandidateDocument;
import com.recruiting.center.crm.service.candidate.CandidateDocumentService;
import com.recruiting.center.crm.service.candidate.CandidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentsControllerService {
    private final CandidateDocumentService documentService;
    private final CandidateService candidateService;

    @Value("${app.upload.dir}")
    private String baseFilePath;

    public void addDocuments(Long id, List<DocumentDto> documents, List<MultipartFile> files, String username) {
        Candidate candidateById = candidateService.findCandidateById(id);

        // Використовуємо абсолютний шлях і нормалізуємо його
        Path baseDir = Paths.get(baseFilePath).toAbsolutePath().normalize();
        Path candidateDocDir = baseDir.resolve("documents").resolve(id.toString()).normalize();

        try {
            // Перевіряємо чи базова директорія існує і доступна для запису
            if (!Files.exists(baseDir)) {
                Files.createDirectories(baseDir);
            }

            if (!Files.isWritable(baseDir)) {
                throw new IllegalStateException("Base upload directory is not writable: " + baseDir);
            }

            // Створюємо директорію для кандидата
            Files.createDirectories(candidateDocDir);
            log.info("Created candidate directory: {}", candidateDocDir);

            for (int i = 0; i < documents.size(); i++) {
                DocumentDto document = documents.get(i);
                MultipartFile file = files.get(i);

                String documentDescription = document.fileName();
                String documentFileType = file.getContentType();

                String originalFilename = file.getOriginalFilename();
                if (originalFilename == null || originalFilename.trim().isEmpty()) {
                    originalFilename = document.originalFileName();
                }

                if (originalFilename == null || originalFilename.trim().isEmpty()) {
                    throw new IllegalArgumentException("File must have a valid filename");
                }

                String cleanedFilename = StringUtils.cleanPath(originalFilename);
                String documentName = System.currentTimeMillis() + "_" + cleanedFilename;

                Path targetFilePath = candidateDocDir.resolve(documentName);

                // Перевіряємо що файл буде створений в межах дозволеної директорії
                if (!targetFilePath.startsWith(candidateDocDir)) {
                    throw new SecurityException("Invalid file path: " + documentName);
                }

                Files.copy(file.getInputStream(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                log.info("Saved file: {}", targetFilePath);

                String relativePath = "documents/" + id + "/" + documentName;

                documentService.addDocument(CandidateDocument.builder()
                        .uploadedBy(username)
                        .type(documentDescription)
                        .candidate(candidateById)
                        .filePath(relativePath)
                        .fileType(documentFileType)
                        .uploadedDate(LocalDate.now())
                        .fileName(documentName)
                        .build());
            }
        } catch (IOException e) {
            log.error("Failed to store documents for candidate ID: {}. Error: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to store documents for candidate ID: " + id + ". " + e.getMessage(), e);
        }
    }

    public ResponseEntity<Resource> getDocumentFile(Long documentId) {

        CandidateDocument document = documentService.findByDocumentId(documentId);


        try {
            // Отримуємо шлях до файлу
            Path filePath = getSecureFilePath(document.getFilePath());

            // Перевіряємо чи існує файл
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("File not found: " + document.getFileName());
            }

            // Перевіряємо чи це файл (не директорія)
            if (!Files.isRegularFile(filePath)) {
                throw new IllegalStateException("Path is not a regular file: " + document.getFileName());
            }

            // Створюємо Resource
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(document.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + document.getFileName() + "\"")
                        .body(resource);
            } else {
                throw new FileNotFoundException("File not readable: " + document.getFileName());
            }

        } catch (MalformedURLException | FileNotFoundException e) {
            throw new RuntimeException("Error creating file resource for document ID: " + documentId, e);
        }
    }


    /**
     * Безпечне отримання шляху до файлу з перевіркою на Path Traversal атаки
     */
    private Path getSecureFilePath(String relativePath) {
        if (relativePath == null || relativePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        // Базовий шлях
        Path basePath = Paths.get(baseFilePath).toAbsolutePath().normalize();

        // Повний шлях до файлу
        Path fullPath = basePath.resolve(relativePath).normalize();

        // Перевіряємо чи файл знаходиться в межах базової директорії (захист від Path Traversal)
        if (!fullPath.startsWith(basePath)) {
            throw new SecurityException("Access denied: file path outside of allowed directory");
        }

        return fullPath;
    }

}
