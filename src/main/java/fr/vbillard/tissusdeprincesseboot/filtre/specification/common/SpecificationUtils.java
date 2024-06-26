package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import fr.vbillard.tissusdeprincesseboot.utils.color.LabColor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecificationUtils {

	private static final char PERCENT_WILDCARD = '%';
	private static final String QUOTE = "\"";
	private static final char QUOTE_CHAR = '"';
	private static final String SPACE = " ";

	public static Predicate getCharacterSearchPredicate(CharacterSearch characterSearch, Path<String> path,
			CriteriaBuilder cb) {

		List<Predicate> predicateList = new ArrayList<>();

		if (characterSearch != null) {
			Expression<String> applyIgnoreCaseOnPath = characterSearch.getIgnoreCase() ? cb.lower(path) : path;

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
			if (StringUtils.isNotBlank(characterSearch.getContainsMultiple())) {

				if (characterSearch.getContainsMultiple().contains(QUOTE)) {
					List<Integer> quoteIndex = new ArrayList<>();

					for (int i = 0; i < characterSearch.getContainsMultiple().length(); i++) {
						if (characterSearch.getContainsMultiple().charAt(i) == QUOTE_CHAR) {
							quoteIndex.add(i);
						}
					}

					if (quoteIndex.size() % 2 != 0) {
						quoteIndex.remove(quoteIndex.size() - 1);
					}

					List<String> elements = new ArrayList<>();
					if (!quoteIndex.isEmpty()) {
						elements.addAll(Arrays.asList(
								characterSearch.getContainsMultiple().substring(0, quoteIndex.get(0)).split(SPACE)));

						for (int i = 0; i < quoteIndex.size();) {

							if (i % 2 == 0) {
								elements.add(characterSearch.getContainsMultiple().substring(quoteIndex.get(i) + 1,
										quoteIndex.get(++i)));
							} else if (i + 1 == quoteIndex.size()) {

								elements.addAll(Arrays.asList(characterSearch.getContainsMultiple()
										.substring(quoteIndex.get(i++) + 1).split(SPACE)));
							} else {
								elements.addAll(Arrays.asList(characterSearch.getContainsMultiple()
										.substring(quoteIndex.get(i) + 1, quoteIndex.get(++i)).split(SPACE)));
							}

						}

					} else {
						elements.addAll(Arrays.asList(characterSearch.getContainsMultiple().split(SPACE)));
					}

					for (String s : elements) {
						if (!s.isEmpty()) {
							predicateList.add(cb.like(applyIgnoreCaseOnPath, addWildcardsAround(s)));
						}

					}

				} else {
					for (String s : characterSearch.getContainsMultiple().split(SPACE)) {
						predicateList.add(cb.like(applyIgnoreCaseOnPath, addWildcardsAround(s)));

					}
				}

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

	public static Predicate getColorPredicate(LabColor color, Path<Double>[] path, CriteriaBuilder cb){
		Expression<Double> lDiff = cb.diff(path[0], color.getL());
		Expression<Double> aDiff = cb.diff(path[1], color.getA());
		Expression<Double> bDiff = cb.diff(path[2], color.getB());

		Expression<Double> distance = cb.sqrt(cb.sum(cb.sum(cb.prod(lDiff, lDiff), cb.prod(aDiff, aDiff)), cb.prod(bDiff, bDiff)));
		return cb.lessThanOrEqualTo(distance, 50.0);
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
