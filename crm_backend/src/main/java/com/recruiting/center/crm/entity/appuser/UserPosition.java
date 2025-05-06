package com.recruiting.center.crm.entity.appuser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_position")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "unit_position")
    private String unitPosition;
}
