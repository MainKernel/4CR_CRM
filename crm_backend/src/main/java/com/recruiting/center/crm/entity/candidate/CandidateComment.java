package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "candidate_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate commentDate;
    private String commentedBy;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
