package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
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
    private String status;
    @Column(name = "color")
    private String color;
}
