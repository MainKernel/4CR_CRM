package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="military_unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilitaryUnit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "military_unit_id_seq")
    @SequenceGenerator(
            name = "military_unit_id_seq",
            sequenceName = "military_unit_id_seq",
            allocationSize = 1
    )
    private long id;
    @Column(name="unit_name", nullable = false, unique = true)
    @NotBlank
    private String unitName;
    @Column(name = "strict_unit_name", nullable = false, unique = true)
    @NotBlank
    private String strictUnitName;
    @Column(name = "comment")
    private String comment;
    @Column(name="is_disabled")
    private Boolean isDisabled;
}
