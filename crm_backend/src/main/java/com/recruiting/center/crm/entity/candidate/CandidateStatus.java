package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "candidate_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_status_id_seq")
    @SequenceGenerator(
            name = "candidate_status_id_seq",
            sequenceName = "candidate_status_id_seq",
            allocationSize = 1
    )
    private long id;
    @NotBlank
    @Column(name = "status", length = 128, nullable = false, unique = true)
    private String status;
    @NotBlank
    @Column(name = "color")
    private String color;
    @Column(name = "is_disabled")
    private Boolean isDisabled;
}
