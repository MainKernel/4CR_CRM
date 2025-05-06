package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Entity
@Table(name = "candidate_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="file_name")
    private String fileName;
    @Column(name="file_type")
    private String fileType;
    @Column(name="file_path")
    private String filePath;
    @Column(name="uploaded_date")
    private LocalDate uploadedDate;
    @Column(name="uploaded_by")
    private String uploadedBy;
    @Column(name = "type")
    private String type;
    @ManyToOne
    @JoinColumn(name="candidate_id")
    private Candidate candidate;
}
