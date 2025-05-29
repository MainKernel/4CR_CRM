package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Valid
@Table(name="candidate_position")
public class CandidatePosition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_position_id_seq")
    @SequenceGenerator(
            name = "candidate_position_id_seq",
            sequenceName = "candidate_position_id_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "position_name")
    @NotNull
    @NotBlank
    private String positionName;
    @Column(name="description")
    @NotNull
    @NotBlank
    private String description;
    @Column(name = "is_disabled")
    private Boolean isDisabled;
}
