package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "duty_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DutyType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "duty_type_id_seq")
    @SequenceGenerator(
            name = "duty_type_id_seq",
            sequenceName = "duty_type_id_seq",
            allocationSize = 1
    )
    private long id;
    @Column(name = "status", length = 128, nullable = false, unique = true)
    @NotBlank
    @NotNull
    private String status;
    @Column(name = "color")
    @NotBlank
    @NotNull
    private String color;
}
