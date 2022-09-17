package com.game.portal.specifications.sentences;

import com.game.portal.models.Sentence;
import com.game.portal.specifications.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class GetByUserSpecification implements Specification<Sentence> {

    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Sentence> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase("=")) {
            return builder.equal(root.get(criteria.getKey()).get("id"), criteria.getValue());
        }
        return null;
    }
}