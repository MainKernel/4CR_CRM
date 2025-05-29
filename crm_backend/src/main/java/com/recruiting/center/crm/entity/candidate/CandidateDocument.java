package com.recruiting.center.crm.entity.candidate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "candidate_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDocument implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_documents_id_seq")
    @SequenceGenerator(
            name = "candidate_documents_id_seq",
            sequenceName = "candidate_documents_id_seq",
            allocationSize = 1
    )
    private long id;
    @Column(name="file_name")
    @NotBlank
    private String fileName;
    @Column(name="file_type")
    @NotBlank
    private String fileType;
    @Column(name="file_path")
    @NotBlank
    private String filePath;
    @Column(name="uploaded_date")
    private LocalDate uploadedDate;
    @Column(name="uploaded_by")
    @NotBlank
    private String uploadedBy;
    @Column(name = "type")
    @NotBlank
    private String type;
    @ManyToOne
    @JoinColumn(name="candidate_id")
    @JsonBackReference
    private Candidate candidate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateDocument document = (CandidateDocument) o;
        return id != 0 && id == document.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
