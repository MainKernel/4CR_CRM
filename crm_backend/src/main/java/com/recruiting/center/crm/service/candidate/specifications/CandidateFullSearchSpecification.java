package com.recruiting.center.crm.service.candidate.specifications;

import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.entity.candidate.enums.Completed;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CandidateFullSearchSpecification implements Specification<Candidate> {
    private final String search;

    @Override
    public Predicate toPredicate(Root<Candidate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> mandatoryPredicates = new ArrayList<>();

        // Обов'язкові умови (AND)
        mandatoryPredicates.add(cb.isNull(root.get("curator")));
        mandatoryPredicates.add(cb.isNull(root.get("escortedBy")));
        mandatoryPredicates.add(cb.notEqual(root.get("completed"), Completed.COMPLETED));

        if (search == null || search.trim().isEmpty()) {
            return cb.conjunction();
        }
        String likeSearch = "%" + search.toLowerCase() + "%";
        List<Predicate> searchPredicates = new ArrayList<>();

        searchPredicates.add(cb.like(cb.lower(root.get("name")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.get("surname")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.get("middleName")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.get("phoneNumber")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.get("recruiter")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.get("curator")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.get("escortedBy")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.get("orderNumber")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.get("territoryCenterRecord")), likeSearch));


        searchPredicates.add(cb.like(cb.lower(root.join("status").get("status")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.join("dutyType").get("status")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.join("militaryUnit").get("unitName")), likeSearch));
        searchPredicates.add(cb.like(cb.lower(root.join("psychologicalTest").get("status")), likeSearch));

        Predicate searchPredicate = cb.or(searchPredicates.toArray(new Predicate[0]));
        mandatoryPredicates.add(searchPredicate);

        return cb.and(mandatoryPredicates.toArray(new Predicate[0]));
    }
}
