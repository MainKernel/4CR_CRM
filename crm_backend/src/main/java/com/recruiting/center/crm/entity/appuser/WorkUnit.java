package com.recruiting.center.crm.entity.appuser;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_unit_id_seq")
    @SequenceGenerator(
            name = "work_unit_id_seq",
            sequenceName = "work_unit_id_seq",
            allocationSize = 1
    )
    private long id;
    @Column(name = "unit_name")
    @NotBlank
    private String unitName;
}
