package com.recruiting.center.crm.entity.appuser;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_position_id_seq")
    @SequenceGenerator(
            name = "user_position_id_seq",
            sequenceName = "user_position_id_seq",
            allocationSize = 1
    )
    private long id;
    @Column(name = "unit_position")
    @NotBlank
    private String unitPosition;
}
