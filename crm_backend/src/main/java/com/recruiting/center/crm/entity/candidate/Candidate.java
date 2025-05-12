package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Valid
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_id_seq")
    @SequenceGenerator(
            name = "candidate_id_seq",
            sequenceName = "candidate_id_seq",
            allocationSize = 1
    )
    private long id;
    @Column(name = "surname", nullable = false, length = 128)
    @NotBlank
    @NotNull
    private String surname;
    @Column(name = "name", nullable = false, length = 128)
    @NotBlank
    @NotNull
    private String name;
    @Column(name = "middle_name", length = 128, nullable = false)
    @NotBlank
    @NotNull
    private String middleName;
    @ManyToOne
    @JoinColumn(name = "candidate_status_id", nullable = false)
    private CandidateStatus status;
    @Column(name = "phone_number", nullable = false)
    @NotBlank
    @NotNull
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "duty_type_id", nullable = false)
    private DutyType dutyType;
    @ManyToOne
    @JoinColumn(name = "military_unit_id", nullable = false)
    private MilitaryUnit militaryUnit;
    @Column(name = "recommendation_letter")
    private LocalDate recommendationLetter;
    @Column(name = "recruiter")
    private String recruiter;
    @Column(name = "curator")
    private String curator;
    @Column(name = "escorted_by")
    private String escortedBy;
    @ManyToOne
    @JoinColumn(name = "psychological_test", nullable = false)
    private PsychologicalTest psychologicalTest;
    @Column(name = "enrolment_order")
    private LocalDate enrolmentOrder;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "territory_center_record")
    private String territoryCenterRecord;
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<CandidateComment> comments;
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<CandidateDocument> documents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return id != 0 && id == candidate.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
