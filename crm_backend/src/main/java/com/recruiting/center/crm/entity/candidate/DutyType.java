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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "status", length = 128, nullable = false)
    private String status;
    @Column(name = "color")
    private String color;
}
