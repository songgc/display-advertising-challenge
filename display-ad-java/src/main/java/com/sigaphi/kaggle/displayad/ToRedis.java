package com.sigaphi.kaggle.displayad;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.base.Joiner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Input feature value pairs to Redis
 * @author Guocong Song
 */
public class ToRedis {
	public static final Joiner JOIN_KV = Joiner.on(":");
	private static final Jedis jedis = new Jedis("localhost", 6379);
	
	private static Consumer<RawFeature> toRedis = (RawFeature raw) -> {
		Pipeline pipe = jedis.pipelined();
		Map<String, String> catMap = raw.getCatFields(null);
		catMap.entrySet().stream()
			.forEach(e -> pipe.zincrby(JOIN_KV.join("imp", e.getKey()), 1, e.getValue()));
		pipe.sync();
	};

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		reader.lines()
			.skip(1)
			.map(line -> RawFeature.Builder.of(line))
			.forEach(toRedis);
		jedis.close();
	}

}
