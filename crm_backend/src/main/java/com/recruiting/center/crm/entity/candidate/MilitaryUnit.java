package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="military_unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilitaryUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="unit_name", nullable = false, unique = true)
    private String unitName;
    @Column(name = "strict_unit_name", nullable = false, unique = true)
    private String strictUnitName;
    @Column(name = "comment")
    private String comment;
}
