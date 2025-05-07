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
    @SequenceGenerator(
            name = "candidate_comments_seq",
            sequenceName = "candidate_comments_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "candidate_comments_seq")
    private long id;
    private LocalDate commentDate;
    private String commentedBy;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
