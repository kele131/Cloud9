/*
 * Cloud9: A MapReduce Library for Hadoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package edu.umd.cloud9.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Random;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import edu.umd.cloud9.util.MapII.Entry;

public class HMapIITest {

	@Test
	public void testBasic1() {

		int size = 100000;
		Random r = new Random();
		int[] ints = new int[size];

		MapII map = new HMapII();
		for (int i = 0; i < size; i++) {
			int k = r.nextInt(size);
			map.put(i, k);
			ints[i] = k;
		}

		for (int i = 0; i < size; i++) {
			int v = map.get(i);

			assertEquals(ints[i], v);
			assertTrue(map.containsKey(i));
		}

	}

	@Test
	public void testUpdate() {

		int size = 100000;
		Random r = new Random();
		int[] ints = new int[size];

		MapII map = new HMapII();
		for (int i = 0; i < size; i++) {
			int k = r.nextInt(size);
			map.put(i, k);
			ints[i] = k;
		}

		assertEquals(size, map.size());

		for (int i = 0; i < size; i++) {
			map.put(i, ints[i] + 1);
		}

		assertEquals(size, map.size());

		for (int i = 0; i < size; i++) {
			int v = map.get(i);

			assertEquals(ints[i] + 1, v);
			assertTrue(map.containsKey(i));
		}

	}
	
	@Test
	public void testBasic() throws IOException {
		HMapII m = new HMapII();

		m.put(1, 5);
		m.put(2, 22);

		float value;

		assertEquals(m.size(), 2);

		value = m.get(1);
		assertTrue(value == 5.0f);

		value = m.remove(1);
		assertEquals(m.size(), 1);

		value = m.get(2);
		assertTrue(value == 22.0f);
	}

	@Test
	public void testPlus() throws IOException {
		HMapII m1 = new HMapII();

		m1.put(1, 5);
		m1.put(2, 22);

		HMapII m2 = new HMapII();

		m2.put(1, 4);
		m2.put(3, 5);

		m1.plus(m2);

		assertEquals(m1.size(), 3);
		assertTrue(m1.get(1) == 9);
		assertTrue(m1.get(2) == 22);
		assertTrue(m1.get(3) == 5);
	}

	@Test
	public void testDot() throws IOException {
		HMapII m1 = new HMapII();

		m1.put(1, 2);
		m1.put(2, 1);
		m1.put(3, 3);

		HMapII m2 = new HMapII();

		m2.put(1, 1);
		m2.put(2, 4);
		m2.put(4, 5);

		int s = m1.dot(m2);

		assertTrue(s == 6);
	}

	@Test
	public void testSortedEntries1() {
		HMapII m = new HMapII();

		m.put(1, 5);
		m.put(2, 2);
		m.put(3, 3);
		m.put(4, 3);
		m.put(5, 1);

		Entry[] e = m.getEntriesSortedByValue();
		assertEquals(5, e.length);

		assertEquals(1, e[0].getKey());
		assertEquals(5.0f, (float) e[0].getValue(), 10E-6);

		assertEquals(3, e[1].getKey());
		assertEquals(3.0f, (float) e[1].getValue(), 10E-6);

		assertEquals(4, e[2].getKey());
		assertEquals(3.0f, (float) e[2].getValue(), 10E-6);

		assertEquals(2, e[3].getKey());
		assertEquals(2.0f, (float) e[3].getValue(), 10E-6);

		assertEquals(5, e[4].getKey());
		assertEquals(1.0f, (float) e[4].getValue(), 10E-6);
	}

	@Test
	public void testSortedEntries2() {
		HMapII m = new HMapII();

		m.put(1, 5);
		m.put(2, 2);
		m.put(3, 3);
		m.put(4, 3);
		m.put(5, 1);

		Entry[] e = m.getEntriesSortedByValue(2);

		assertEquals(2, e.length);

		assertEquals(1, e[0].getKey());
		assertEquals(5, e[0].getValue());

		assertEquals(3, e[1].getKey());
		assertEquals(3, e[1].getValue());
	}

	@Test
	public void testSortedEntries3() {
		HMapII m = new HMapII();

		m.put(1, 5);
		m.put(2, 2);

		Entry[] e = m.getEntriesSortedByValue(5);

		assertEquals(2, e.length);

		assertEquals(1, e[0].getKey());
		assertEquals(5, e[0].getValue());

		assertEquals(2, e[1].getKey());
		assertEquals(2, e[1].getValue());
	}

	@Test
	public void testSortedEntries4() {
		HMapII m = new HMapII();

		Entry[] e = m.getEntriesSortedByValue();
		assertTrue(e == null);
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(HMapIITest.class);
	}

}