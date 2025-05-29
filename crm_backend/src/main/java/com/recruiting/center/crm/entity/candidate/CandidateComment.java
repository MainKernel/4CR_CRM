package com.recruiting.center.crm.entity.candidate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "candidate_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateComment implements Serializable {
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
    @JsonBackReference
    private Candidate candidate;


}
