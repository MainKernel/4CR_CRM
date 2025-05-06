package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "candidate_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Column(name = "status", length = 128, nullable = false, unique = true)
    private String status;
    @NotBlank
    @Column(name = "color")
    private String color;
}
