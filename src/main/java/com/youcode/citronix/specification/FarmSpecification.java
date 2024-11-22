package com.youcode.citronix.specification;

import com.youcode.citronix.domain.Farm;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FarmSpecification {
    public static Specification<Farm> filterFarm(String name, String location, Double area) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(name)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        likePattern(name.toLowerCase())
                ));
            }

            if (StringUtils.isNotBlank(location)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location")),
                        likePattern(location.toLowerCase())
                ));
            }

            if (area != null) {
                predicates.add(criteriaBuilder.equal(root.get("area"), area));
            }

            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            // Combine all predicates with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static String likePattern(String value) {
        return "%" + value + "%";
    }
}
