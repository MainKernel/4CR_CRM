package com.recruiting.center.crm.entity.appuser;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="app_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    @NotBlank
    private String firstName;
    @Column(name = "second_name")
    @NotBlank
    private String secondName;
    @ManyToOne
    @JoinColumn(name = "work_unit_id", nullable = false)
    @NotBlank
    private WorkUnit workUnit;
    @ManyToOne
    @JoinColumn(name = "user_position_id", nullable = false)
    @NotBlank
    private UserPosition userPosition;
    @Column(name = "username")
    @NotBlank
    private String username;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
    @Column(name = "password")
    @NotBlank
    private String password;
    @Builder.Default
    @Column(name = "is_account_non_expired")
    boolean isAccountNonExpired = true;
    @Builder.Default
    @Column(name = "is_account_non_locked")
    boolean isAccountNonLocked = true;
    @Builder.Default
    @Column(name = "is_credentials_non_expired")
    boolean isCredentialsNonExpired = true;
}
