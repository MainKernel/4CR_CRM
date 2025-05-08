package com.recruiting.center.crm.entity.candidate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private String surname;
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    @Column(name = "middle_name", length = 128, nullable = false)
    private String middleName;
    @ManyToOne
    @JoinColumn(name = "candidate_status_id", nullable = false)
    private CandidateStatus status;
    @Column(name = "phone_number", nullable = false)
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
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CandidateComment> comments;
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL,orphanRemoval = true,  fetch = FetchType.EAGER)
    private List<CandidateDocument> documents;
}
