package org.munoz_family.examples.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Supplier;

public class Solutions {

	public static void main(String[] args) {
		System.out.println("reverseNum(12345) : " + reverseNum(12345));
		System.out.println("reverseNum(-12345) : " + reverseNum(-12345));

		
		Map<String,Integer> unsortedMap = new HashMap<>();
		unsortedMap.put("A) Book Title 50", 50);
		unsortedMap.put("C) Book Title 20", 20);
		unsortedMap.put("B) Book Title 60", 60);
		unsortedMap.put("D) Book Title 10", 10);
		System.out.println("sortMap(unsortedMap)  : "  + sortMap(unsortedMap, 1));
		System.out.println("sortMap2(unsortedMap) : " + sortMap2(unsortedMap, -1));
		System.out.println("sortMap3(unsortedMap) : " + sortMap3(unsortedMap, Comparator.comparing(Entry::getValue), LinkedHashMap::new, null));
		Map<String, Integer> test = sortMap3(unsortedMap, Comparator.comparing(Entry::getKey), null, -1L);
		unsortedMap.put("E) Book Title 05", 5);
		System.out.println("sortMap3(unsortedMap) : " + test);
		System.out.println("sortMap3(unsortedMap) : " + sortMap3(unsortedMap, Comparator.comparing(Entry::getValue), LinkedHashMap::new, null));
		System.out.println("sortMap4(unsortedMap) : " + sortMap4(unsortedMap, null, 4L));
		
		System.out.println("isPalindrome(\"race car\") : " + isPalindrome("race car"));
		System.out.println("isPalindrome(\"Rats live on no Evil STAR\") : " + isPalindrome("Rats live on no Evil STAR"));
		System.out.println("isPalindrome(\"Rats live on no Evil STAR!\") : " + isPalindrome("Rats live on no Evil STAR!"));
		System.out.println("isPalindrome(\"\") : " + isPalindrome(""));
		System.out.println("isPalindrome(\"tattarrattat\") : " + isPalindrome("tattarrattat"));
		
	}

	static int reverseNum(int number) {
		int numReversed =0;
		for (int x = number; x != 0; x /= 10) {
			numReversed = numReversed * 10 + x % 10;
		}
		return numReversed;
	}
	
	static Map<String,Integer> sortMap(Map<String,Integer> pMap, long limit) {
		if (pMap == null || pMap.size() == 0) {
			return pMap;
		}

		limit = limit < 0 ? Long.MAX_VALUE : limit;
		Map<String,Integer> sortedMap;
//		sortedMap = pMap.entrySet().stream().sorted(Comparator.comparing(Entry::getValue)).limit(limit).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		sortedMap = pMap.entrySet().stream().sorted(Comparator.comparing(Entry::getValue)).limit(limit).collect(LinkedHashMap::new, (x, y) -> x.put(y.getKey(),y.getValue()), Map::putAll);
		return sortedMap;
	}
	
	static <K,V extends Comparable<V>> Map<K,V> sortMap2(Map<K,V> pMap, long limit) {
		if (pMap == null || pMap.size() == 0) {
			return pMap;
		}

		limit = limit < 0 ? Long.MAX_VALUE : limit;
		
		Map<K,V> sortedMap;
		sortedMap = pMap.entrySet().stream().sorted(Comparator.comparing(Entry::getValue)).limit(limit).collect(LinkedHashMap::new, (x, y) -> x.put(y.getKey(),y.getValue()), Map::putAll);
		return sortedMap;
	}
	
	/**
	 * Returns a new Map sorted by the specified comparator. The map's key & value
	 * elements MUST implement {@code Comparable}.
	 * <p>
	 * NOTE: In order for sorting to stick, the map MUST be an implementation that
	 * maintains order, such as {@code LinkedHashMap} vs. one that does NOT like
	 * {@code HashMap}.
	 * 
	 * @param pMap
	 *            a Map that implements {@code Comparable} for its keys and values
	 * @param comparator
	 *            if null, defaults to {@code Entry::getValue}
	 * @param supplier
	 *            if null, default to {@code LinkedHashMap::new}
	 * @param limit
	 *            if null or negative, defaults to unlimited
	 * @return A new Map sorted by the specified comparator
	 */
	static <K extends Comparable<K>, V extends Comparable<V>, S extends Map<K, V>> Map<K, V> sortMap3(Map<K, V> pMap, Comparator<? super Entry<K, V>> comparator, Supplier<S> supplier, Long limit) {
		Objects.requireNonNull(pMap);		
		if (pMap.size() <= 1)
			return pMap;

		// Set defaults for any null params
		comparator = (comparator == null) ? Comparator.comparing(Entry::getValue) : comparator;
		limit = (limit == null || limit < 0) ? Long.MAX_VALUE : limit;
		supplier = (supplier == null) ? new Supplier<S>() {
			@SuppressWarnings("unchecked")
			@Override
			public S get() {
				return (S) new LinkedHashMap<K, V>(pMap.size());
			}
		} : supplier;

		Map<K, V> sortedMap = pMap.entrySet().stream().sorted(comparator).limit(limit)
				.collect(supplier,(x, y) -> x.put(y.getKey(), y.getValue()), Map::putAll);
		return sortedMap;
	}
	
	static <K extends Comparable<K>, V extends Comparable<V>> Map<K,V> sortMap4(Map<K,V> pMap, Comparator<? super Entry<K, V>> comparator, Long limit) {
		if (pMap == null || pMap.size() == 0) {
			return pMap;
		}
		comparator = (comparator == null) ? Comparator.comparing(Entry::getKey) : comparator;
		limit = (limit == null || limit < 0) ? Long.MAX_VALUE : limit;
		List<Entry<K,V>> col =  new ArrayList<>(pMap.entrySet());
		Collections.sort(col, comparator);
		Map<K,V> sortedMap = new LinkedHashMap<>(col.size());
		for (Entry<K, V> entry : col) {
			sortedMap.put(entry.getKey(), entry.getValue());
			if (--limit <= 0) {
				break;
			}
		}
		return sortedMap;
	}
	
	private static boolean isPalindrome(String string) {
		Objects.requireNonNull(string, "MUST provide a string of ZERO or more characters");
		string = string.replaceAll(" ", "").toLowerCase();
		boolean isPalindrome = true;
		for (int offBeg = 0, offEnd = string.length()-1; offBeg  < offEnd; offBeg++, offEnd--) {
			if (string.charAt(offBeg) != string.charAt(offEnd)) {
				isPalindrome = false;
				break;
			}
		}
		return isPalindrome;
	}
}
