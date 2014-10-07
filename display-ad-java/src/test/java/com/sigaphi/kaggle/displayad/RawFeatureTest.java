package com.sigaphi.kaggle.displayad;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.junit.Test;

import com.google.common.base.Splitter;

public class RawFeatureTest {

	@Test
	public void test() {
		String str = ",,2,";
		for (String s : Splitter.on(",").split(str)) {
			System.out.print("x:");
			System.out.println(s);
		}
		for (String s : str.split(",")) {
			System.out.print("y:");
			System.out.println(s);
		}
		List<Integer> a = Arrays.asList(0,1,2,3,4);
		System.out.println(a.subList(1, 4));
		
		String line = "55840616,0,0,4,13,13,2815,20,4,21,41,0,4,,14,05db9164,a0e12995,622d2ce8,51c64c6d,25c83c98,,3086a9e9,0b153874,a73ee510,3b08e48b,ebd30041,e9521d94,c7a109eb,1adce6ef,78c64a1d,ab8b968d,d4bb7bd8,1616f155,21ddcdc9,5840adea,ee4fa92e,,32c7478e,d61a7d0a,9b3e8820,b29c74dc";
		String actual = RawFeature.Builder.of(line).toString();
		System.out.println(RawFeature.Builder.of(line).getHeader());
		System.out.println(line);
		System.out.println(actual);
		System.out.println(RawFeature.Builder.of(line).getCatFields(null));
		System.out.println(RawFeature.Builder.of(line).getNumFields(null));
		assertEquals(line, actual);
		line = "66042132,,1,6,5,283,26,81,5,42,,6,,5,05db9164,,43a795a8,be13fbd1,4cf72387,6f6d9be8,f00bddf8,6c97ac79,a73ee510,ca1bb880,55795b33,277cb5a2,39795005,b28479f6,93625cba,b06f79e3,e5ba7672,3987fb8a,21ddcdc9,5840adea,45fdf300,,32c7478e,a6e7d8d3,001f3601,2fede552";
		actual = RawFeature.Builder.of(line).toString();
		System.out.println(RawFeature.Builder.of(line).getHeader());
		System.out.println(line);
		System.out.println(actual);
		assertEquals(line, actual);
		Map<String, Double> numMap = RawFeature.Builder.of(line).getNumFields(null);
		System.out.println(numMap);
//		System.out.println(FeaturesToVw.sos2("numN",  numMap.size()));
		Map<String, String> catMap = RawFeature.Builder.of(line).getCatFields(null);
		System.out.println(catMap);
		catMap = RawFeature.Builder.of(line).getCatFields(RawFeature.catColsExSet);
		System.out.println(catMap);
//		System.out.println(FeaturesToVw.numMapToLogString(numMap));
		
		
//		Map<String, String> catMap = RawFeature.Builder.of(line).getCatFields(FeaturesToVw.placeFeatures);
//		System.out.println(FeaturesToVw.catMapToString(catMap, Options.CAT_POP_1));
//		catMap = RawFeature.Builder.of(line).getCatFields(FeaturesToVw.otherFeatures);
//		System.out.println(FeaturesToVw.catMapToString(catMap, Options.CAT_POP_1));
//		System.out.println(FeaturesToVw.catMapToString(catMap, Options.CAT_BASIC));
	}

}
