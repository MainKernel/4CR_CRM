package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.entity.DocumentDto;
import com.recruiting.center.crm.service.controller.DocumentsControllerService;
import com.recruiting.center.crm.utils.Base64MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контролер для роботи з документами кандидатів
 */
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentsController {

    private final DocumentsControllerService documentsControllerService;

    /**
     * Додавання документів до кандидата
     *
     * @param id ID кандидата
     * @param documents список документів у форматі DTO
     * @return повідомлення про успішне завантаження
     */
    @PostMapping(value = "/{id}/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addDocument(
            @PathVariable Long id,
            @RequestBody List<DocumentDto> documents
    ) {
        log.info("Received request to add {} documents for candidate ID: {}", documents.size(), id);

        try {
            // Отримуємо ім'я користувача з контексту безпеки
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();

            // Конвертуємо DocumentDto в MultipartFile
            List<MultipartFile> multipartFiles = documents.stream().map(doc -> {
                // Декодуємо Base64 в байти
                byte[] fileBytes = Base64.getDecoder().decode(doc.fileContent());

                // Створюємо власну реалізацію MultipartFile
                return new Base64MultipartFile(
                        fileBytes,
                        "file", // field name
                        doc.originalFileName(),
                        doc.contentType()
                );
            }).collect(Collectors.toList());

            // Викликаємо сервіс для збереження документів
            documentsControllerService.addDocuments(id, documents, multipartFiles, userName);

            return ResponseEntity.ok("Документи успішно завантажено");

        } catch (Exception e) {
            log.error("Error adding documents for candidate ID: {}", id, e);
            return ResponseEntity.badRequest().body("Помилка завантаження: " + e.getMessage());
        }
    }

    /**
     * Отримання документа за ID
     *
     * @param documentId ID документа
     * @return файл документа як ресурс
     */
    @GetMapping("/{documentId}")
    public ResponseEntity<Resource> getDocument(@PathVariable Long documentId) {
        log.info("Received request to get document with ID: {}", documentId);
        try {
            return documentsControllerService.getDocumentFile(documentId);
        } catch (Exception e) {
            log.error("Error getting document with ID: {}", documentId, e);
            return ResponseEntity.notFound().build();
        }
    }
}