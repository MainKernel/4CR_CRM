package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "psychological_test")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Valid
public class PsychologicalTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "psychological_test_id_seq")
    @SequenceGenerator(
            name = "psychological_test_id_seq",
            sequenceName = "psychological_test_id_seq",
            allocationSize = 1
    )
    private long id;
    @NotBlank
    @NotNull
    @Column(name = "status", unique = true, nullable = false)
    private String status;
    @NotBlank
    @NotNull
    @Column(name = "color", nullable = false)
    private String color;
    @Column(name = "is_disabled")
    private Boolean isDisabled;
}
