package com.sigaphi.kaggle.displayad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Read csv file to RawFeature objects
 * @author Guocong Song
 */
public class RawFeature {
	private int id; 
	private int label; 

	public static final int INT_NULL = -100;
	public static final String NA = "NA";
	
	public static final List<String> numCols = IntStream.range(1, 14)
			.mapToObj(i -> "I" + Integer.toString(i)).collect(Collectors.toList());
	public static final List<String> catCols = IntStream.range(1, 27)
			.mapToObj(i -> "C" + Integer.toString(i)).collect(Collectors.toList());
	public static final List<String> catColsEx = new ArrayList<>();
	static {
		catColsEx.addAll(catCols);
		catColsEx.add("reqstA");
		catColsEx.add("reqstB");
	}
	public static final Set<String> catColsSet = new HashSet<String>(RawFeature.catCols);
	public static final Set<String> catColsExSet = new HashSet<String>(RawFeature.catColsEx);

	private static final List<String> reqstA = Arrays.asList("C3", "C4", "C12", "C16", "C21", "C24");
	private static final List<String> reqstB = Arrays.asList("C19", "C20", "C25", "C26");
			
	private final Map<String, String> map = new HashMap<String, String>();
	
	public RawFeature(int id, int label, List<String> nums, List<String> cats) {
		this.id = id;
		this.label = label;
		for (int i = 0; i < numCols.size(); i++) {
			map.put(numCols.get(i), nums.get(i));
		}
		for (int i = 0; i < catCols.size(); i++) {
			map.put(catCols.get(i), cats.get(i));
		}
		String a = reqstA.stream().map(col -> this.getField(col, "")).collect(Collectors.joining("-"));
		String b = reqstB.stream().map(col -> this.getField(col, "")).collect(Collectors.joining("-"));
		map.put("reqstA", a);
		map.put("reqstB", b);
	}
	
	public int getId() {
		return id;
	}

	public int getLabel() {
		return label;
	}

	public String getField(String name, String empty) {
		String val = map.get(name);
		if (StringUtils.isEmpty(val)) {
			return empty;
		}
		return val;
	}
	
	public Double getNumField(String name) {
		String val = map.get(name);
		if (StringUtils.isEmpty(val)) {
			return INT_NULL * 1.0;
		}
		return Double.parseDouble(val);
	}
	
	public Map<String, String> getCatFields(Set<String> colSet) {
		Set<String> set;
		if (colSet == null) {
			set = catColsSet;
		} else {
			set = colSet;
		}
		return catColsEx.stream()
				.filter(col -> set.contains(col))
				.collect(Collectors.toMap(Function.identity(),
										  col -> this.getField(col, NA),
										  (x, y) -> y,
										  LinkedHashMap<String, String>::new));
	}
	
	public Map<String, Double> getNumFields(Set<String> colSet) {
		Set<String> set;
		if (colSet == null) {
			set = new HashSet<String>(numCols);
		} else {
			set = colSet;
		}
		return numCols.stream()
				.filter(col -> set.contains(col))
				.collect(Collectors.toMap(Function.identity(),
										  col -> this.getNumField(col),
										  (x, y) -> y,
										  LinkedHashMap<String, Double>::new));
	}
	
	public String getSync() {
		return Joiner.on(",").join(id, label);
	}
	
	public String getHeader() {
		List<String> vals = new ArrayList<String>();
		vals.add("Id");
		if (label != INT_NULL) {
			vals.add("Label");
		}
		numCols.stream().forEach(col -> vals.add(col));
		catCols.stream().forEach(col -> vals.add(col));
		return Joiner.on(",").join(vals);
	}
	
	public boolean hasLabel() {
		return label != INT_NULL;
	}
	
	@Override
	public String toString() {
		List<String> vals = new ArrayList<String>();
		vals.add(Integer.toString(id));
		if (label != INT_NULL) {
			vals.add(Integer.toString(label));
		}
		numCols.stream().forEach(col -> vals.add(this.getField(col, "")));
		catCols.stream().forEach(col -> vals.add(this.getField(col, "")));
		return Joiner.on(",").join(vals);
	}
	
	public static class Builder {
		public static RawFeature of(String line) {
			List<String> els = Splitter.on(",").splitToList(line);
			List<String> nums, cats;
			int label;
			int id = Integer.parseInt(els.get(0));
			switch (els.size()) {
			case 41:
				label = Integer.parseInt(els.get(1));
				nums = els.subList(2, 15);
				cats = els.subList(15, 41);
				break;
			case 40:
				label = RawFeature.INT_NULL;
				nums = els.subList(1, 14);
				cats = els.subList(14, 40);
				break;
			default:
				throw new ArrayStoreException();
			}
			return new RawFeature(id, label, nums, cats);
		}
	}
}
