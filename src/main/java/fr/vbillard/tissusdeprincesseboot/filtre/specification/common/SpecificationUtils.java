package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;

public class SpecificationUtils {

	private static final char PERCENT_WILDCARD = '%';

	public static Predicate getCharacterSearchPredicate(CharacterSearch characterSearch, Path<String> path,
			CriteriaBuilder cb) {

		List<Predicate> predicateList = new ArrayList<>();

		if (characterSearch != null) {
			Path<String> applyIgnoreCaseOnPath = characterSearch.getIgnoreCase() ? (Path<String>) cb.lower(path) : path;

			if (StringUtils.isNotBlank(characterSearch.getEquals())) {
				predicateList.add(cb.equal(applyIgnoreCaseOnPath, characterSearch.getEquals()));
			}
			if (StringUtils.isNotBlank(characterSearch.getNotEquals())) {
				predicateList.add(cb.notEqual(applyIgnoreCaseOnPath, characterSearch.getNotEquals()));
			}
			if (StringUtils.isNotBlank(characterSearch.getStartsWith())) {
				predicateList.add(cb.like(applyIgnoreCaseOnPath, addWildcardLast(characterSearch.getStartsWith())));
			}
			if (StringUtils.isNotBlank(characterSearch.getEndsWith())) {
				predicateList.add(cb.like(applyIgnoreCaseOnPath, addWildcardFirst(characterSearch.getEndsWith())));
			}
			if (StringUtils.isNotBlank(characterSearch.getContains())) {
				predicateList.add(cb.like(applyIgnoreCaseOnPath, addWildcardsAround(characterSearch.getContains())));
			}
			if (StringUtils.isNotBlank(characterSearch.getNotContains())) {
				predicateList
						.add(cb.notLike(applyIgnoreCaseOnPath, addWildcardsAround(characterSearch.getNotContains())));
			}
			if (Boolean.TRUE.equals(characterSearch.getIsNull())) {
				predicateList.add(cb.isNull(path));
			}
			if (Boolean.FALSE.equals(characterSearch.getIsNull())) {
				predicateList.add(cb.isNotNull(path));
			}
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

	public static Predicate getDateSearchPredicate(DateSearch dateSearch, Path<LocalDate> path, CriteriaBuilder cb) {
		return getCommonSearchPredicate(dateSearch, path, cb);
	}

	public static Predicate getDateTimeSearchPredicate(DateTimeSearch dateTimeSearch, Path<LocalDateTime> path,
			CriteriaBuilder cb) {
		return getCommonSearchPredicate(dateTimeSearch, path, cb);
	}

	public static <T extends Number & Comparable<? super T>> Predicate getNumericSearchPredicate(
			NumericSearch<T> numericSearch, Path<T> path, CriteriaBuilder cb) {
		return getCommonSearchPredicate(numericSearch, path, cb);
	}

	private static <X extends CommonSearch<Y>, Y extends Comparable<? super Y>> Predicate getCommonSearchPredicate(
			X commonSearch, Path<Y> path, CriteriaBuilder cb) {

		List<Predicate> predicateList = new ArrayList<>();

		if (commonSearch != null) {
			if (commonSearch.getEquals() != null) {
				predicateList.add(cb.equal(path, commonSearch.getEquals()));
			}
			if (commonSearch.getNotEquals() != null) {
				predicateList.add(cb.notEqual(path, commonSearch.getNotEquals()));
			}
			if (commonSearch.getLessThan() != null) {
				predicateList.add(cb.lessThan(path, commonSearch.getLessThan()));
			}
			if (commonSearch.getLessThanEqual() != null) {
				predicateList.add(cb.lessThanOrEqualTo(path, commonSearch.getLessThanEqual()));
			}
			if (commonSearch.getGreaterThan() != null) {
				predicateList.add(cb.greaterThan(path, commonSearch.getGreaterThan()));
			}
			if (commonSearch.getGreaterThanEqual() != null) {
				predicateList.add(cb.greaterThanOrEqualTo(path, commonSearch.getGreaterThanEqual()));
			}
			if (Boolean.TRUE.equals(commonSearch.getIsNull())) {
				predicateList.add(cb.isNull(path));
			}
			if (Boolean.FALSE.equals(commonSearch.getIsNull())) {
				predicateList.add(cb.isNotNull(path));
			}
		}

		return cb.and(predicateList.toArray(new Predicate[] {}));
	}

	private static String addWildcardLast(String str) {
		return str + PERCENT_WILDCARD;
	}

	private static String addWildcardFirst(String str) {
		return PERCENT_WILDCARD + str;
	}

	private static String addWildcardsAround(String str) {
		return PERCENT_WILDCARD + str + PERCENT_WILDCARD;
	}
}
