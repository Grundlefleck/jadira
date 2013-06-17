/*
 *  Copyright 2013 Chris Pheby
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.jadira.cloning.orika;

import java.util.HashSet;
import java.util.Set;

import org.jadira.cloning.unsafe.UnsafeOperations;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

/**
 * UnsafeCopyConverter allows configuration of a number of specific types which should be copied
 * directly using sun.misc.Unsafe instead of creating a mapped copy.<br>
 * <br>
 * This allows you to declare your own set of types which should be copied instead of mapped.
 * 
 * You can use this type with Orika independently of the rest of the cloning framework to perform
 * fast deep clones within Orika.
 */
public class UnsafeCopyConverter extends CustomConverter<Object, Object> {

	private static final UnsafeOperations UNSAFE_OPERATIONS = UnsafeOperations.getUnsafeOperations();

	private final Set<Type<?>> unsafeCopiedTypes = new HashSet<Type<?>>();

	/**
	 * Constructs a new UnsafeDeepCopyConverter configured to handle the provided list of types by
	 * cloning.
	 * 
	 * @param types one or more types that should be copied using the UnsafeDeepCopyConverter
	 */
	public UnsafeCopyConverter(java.lang.reflect.Type... types) {

		for (java.lang.reflect.Type type : types) {
			unsafeCopiedTypes.add(TypeFactory.valueOf(type));
		}
	}

	private boolean shouldCopy(Type<?> type) {
		for (Type<?> registeredType : unsafeCopiedTypes) {
			if (registeredType.isAssignableFrom(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canConvert(Type<?> sourceType, Type<?> destinationType) {
		return shouldCopy(sourceType) && sourceType.equals(destinationType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convert(Object source, Type<? extends Object> destinationType) {

		if (source == null) {
			return null;
		}
		return UNSAFE_OPERATIONS.deepCopy(source);
	}
}
