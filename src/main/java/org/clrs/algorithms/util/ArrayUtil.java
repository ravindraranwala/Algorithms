package org.clrs.algorithms.util;

import java.lang.reflect.Array;

public class ArrayUtil {
	private ArrayUtil() {
		throw new AssertionError("Non instantiable !");
	}

	public static <T> T[] concat(T[] first, T[] second) {
		/*
		 * We have only T type or it's subtype elements in the given arrays. Both arrays
		 * must have the same reifiable type too (The compiler ensures that), so the
		 * following cast is guaranteed to be safe.
		 */
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(first.getClass().getComponentType(), first.length + second.length);
		final int firstLen = first.length;
		System.arraycopy(first, 0, result, 0, firstLen);
		System.arraycopy(second, 0, result, firstLen, second.length);
		return result;
	}
}
