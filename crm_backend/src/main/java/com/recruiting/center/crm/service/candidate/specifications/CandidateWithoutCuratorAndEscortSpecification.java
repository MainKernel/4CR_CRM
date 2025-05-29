package com.recruiting.center.crm.service.candidate.specifications;

import com.recruiting.center.crm.entity.candidate.Candidate;
import com.recruiting.center.crm.entity.candidate.enums.Completed;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CandidateWithoutCuratorAndEscortSpecification implements Specification<Candidate> {

    @Override
    public Predicate toPredicate(Root<Candidate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.isNull(root.get("curator")));
        predicates.add(criteriaBuilder.isNull(root.get("escortedBy")));
        predicates.add(criteriaBuilder.notEqual(root.get("completed"), Completed.COMPLETED));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
