/**
 * jCRK – Cracking cryptographic hash functions implemented in Java.
 * Copyright © 2016 Filip van Laenen <f.a.vanlaenen@ieee.org>
 * 
 * This file is part of jCRK.
 *
 * jCRK is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *  
 * jCRK is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You can find a copy of the GNU General Public License in /doc/gpl.txt
 * 
 */
package net.filipvanlaenen.jcrk;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An in-memory implementation of the segment repository.
 */
public class InMemorySegmentRepository implements SegmentRepository {
	private final HashFunction hashFunction;
	private final Map<Point, Segment> startPointMap = new HashMap<Point, Segment>();
	private final Map<Point, Set<Segment>> endPointMap = new HashMap<Point, Set<Segment>>();
	private int order;

	/**
	 * Constructor creating an empty in-memory repository for a hash function.
	 * 
	 * @param hashFunction
	 *            The hash function for the in-memory segment repository.
	 */
	public InMemorySegmentRepository(HashFunction hashFunction) {
		this.hashFunction = hashFunction;
	}

	@Override
	public boolean add(Segment segment) throws IllegalArgumentException {
		if (!segment.isComplete()) {
			throw new IllegalArgumentException("The segment isn't complete.");
		}
		if (segment.getOrder() != order) {
			throw new IllegalArgumentException(
					String.format("The order of the segment (%d) isn't the same as the order of the repository (%d).",
							segment.getOrder(), order));
		}
		if (segment.getHashFunction() != hashFunction) {
			throw new IllegalArgumentException(String.format(
					"The hash function of the segment (%s) isn't the same as the hash function of the repository (%s).",
					segment.getHashFunction(), hashFunction));
		}
		if (contains(segment)) {
			return false;
		} else {
			startPointMap.put(segment.getStartPoint(), segment);
			if (endPointMap.containsKey(segment.getEndPoint())) {
				endPointMap.get(segment.getEndPoint()).add(segment);
			} else {
				Set<Segment> segments = new HashSet<Segment>();
				segments.add(segment);
				endPointMap.put(segment.getEndPoint(), segments);
			}
			return true;
		}
	}

	@Override
	public boolean contains(Segment segment) {
		return startPointMap.containsValue(segment);
	}

	@Override
	public boolean containsSegmentWithStartPoint(Point point) {
		return startPointMap.containsKey(point);
	}

	@Override
	public boolean containsSegmentsWithEndPoint(Point point) {
		return endPointMap.containsKey(point);
	}

	@Override
	public Segment getSegmentWithStartPoint(Point point) {
		return startPointMap.get(point);
	}

	@Override
	public Set<Segment> getSegmentsWithEndPoint(Point point) {
		if (endPointMap.containsKey(point)) {
			return endPointMap.get(point);
		} else {
			return Collections.EMPTY_SET;
		}
	}

	@Override
	public boolean isEmpty() {
		return startPointMap.isEmpty();
	}

	@Override
	public int size() {
		return startPointMap.size();
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public void compressToNextOrder() {
		Map<Point, Segment> lowerOrderStartPointMap = new HashMap<Point, Segment>(startPointMap);
		order++;
		startPointMap.clear();
		endPointMap.clear();
		for (Segment segment : lowerOrderStartPointMap.values()) {
			if (segment.getStartPoint().order() >= order) {
				Segment lastSegment = segment;
				long newLength = segment.getLength();
				while (lastSegment.getEndPoint().order() < order
						&& lowerOrderStartPointMap.containsKey(lastSegment.getEndPoint())) {
					lastSegment = lowerOrderStartPointMap.get(lastSegment.getEndPoint());
					newLength += lastSegment.getLength();
				}
				if (lastSegment.getEndPoint().order() >= order) {
					Segment newSegment = new Segment(segment.getStartPoint(), lastSegment.getEndPoint(), newLength,
							order, hashFunction);
					add(newSegment);
				}
			}
		}
	}

	@Override
	public HashFunction getHashFunction() {
		return hashFunction;
	}
}
