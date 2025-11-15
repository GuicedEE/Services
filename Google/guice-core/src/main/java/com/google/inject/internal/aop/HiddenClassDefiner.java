/*
 * Copyright (C) 2021 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject.internal.aop;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * {@link ClassDefiner} that defines classes using {@code MethodHandles.Lookup#defineHiddenClass}.
 *
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class HiddenClassDefiner implements ClassDefiner {

  private static final Object HIDDEN_CLASS_OPTIONS;
  private static final Method HIDDEN_DEFINE_METHOD;

  static {
    try {
      HIDDEN_CLASS_OPTIONS = classOptions("NESTMATE");
      HIDDEN_DEFINE_METHOD =
          Lookup.class.getMethod(
              "defineHiddenClass", byte[].class, boolean.class, HIDDEN_CLASS_OPTIONS.getClass());
    } catch (ReflectiveOperationException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  @Override
  public Class<?> define(Class<?> hostClass, byte[] bytecode) throws Exception {
    Lookup lookup = MethodHandles.privateLookupIn(hostClass, MethodHandles.lookup());
    Lookup definedLookup =
        (Lookup)
            HIDDEN_DEFINE_METHOD.invoke(
                lookup, bytecode, false, HIDDEN_CLASS_OPTIONS);
    return definedLookup.lookupClass();
  }

  /** Creates {@link MethodHandles.Lookup.ClassOption} array with the named options. */
  @SuppressWarnings("unchecked")
  private static Object classOptions(String... options) throws ClassNotFoundException {
    @SuppressWarnings("rawtypes") // Unavoidable, only way to use Enum.valueOf
    Class optionClass = Class.forName(Lookup.class.getName() + "$ClassOption");
    Object classOptions = Array.newInstance(optionClass, options.length);
    for (int i = 0; i < options.length; i++) {
      Array.set(classOptions, i, Enum.valueOf(optionClass, options[i]));
    }
    return classOptions;
  }
}
