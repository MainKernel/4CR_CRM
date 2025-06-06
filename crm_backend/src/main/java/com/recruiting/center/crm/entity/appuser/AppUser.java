package com.recruiting.center.crm.entity.appuser;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="app_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_status_id_seq")
    @SequenceGenerator(
            name = "candidate_status_id_seq",
            sequenceName = "candidate_status_id_seq",
            allocationSize = 1
    )
    private long id;
    @Column(name = "first_name")
    @NotBlank
    private String firstName;
    @Column(name = "second_name")
    @NotBlank
    private String secondName;
    @ManyToOne
    @JoinColumn(name = "work_unit_id", nullable = false)
    private WorkUnit workUnit;
    @ManyToOne
    @JoinColumn(name = "user_position_id", nullable = false)
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
