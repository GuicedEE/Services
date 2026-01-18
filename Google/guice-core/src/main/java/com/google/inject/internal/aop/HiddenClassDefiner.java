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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@link ClassDefiner} that defines classes using {@code MethodHandles.Lookup#defineHiddenClass}.
 *
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class HiddenClassDefiner implements ClassDefiner {

  @Override
  public Class<?> define(Class<?> hostClass, byte[] bytecode) throws Exception {
    Module guiceModule = HiddenClassDefiner.class.getModule();
    Module hostModule = hostClass.getModule();
    if (guiceModule.isNamed() && hostModule.isNamed()) {
      if (!guiceModule.canRead(hostModule)) {
        guiceModule.addReads(hostModule);
      }
      if (!hostModule.isOpen(hostClass.getPackageName(), guiceModule)) {
        hostModule.addOpens(hostClass.getPackageName(), guiceModule);
      }
    }
    try {
      Lookup lookup;
      if (guiceModule.equals(hostModule)) {
        lookup = MethodHandles.privateLookupIn(hostClass, MethodHandles.lookup());
      } else {
        lookup = MethodHandles.lookup().in(hostClass);
      }
      return defineHiddenClass(lookup, bytecode, hostClass);
    } catch (Throwable t) {
      throw t instanceof Exception ? (Exception) t : new RuntimeException(t);
    }
  }

  private Class<?> defineHiddenClass(Lookup lookup, byte[] bytecode, Class<?> hostClass) throws Exception {
    try {
      return lookup.defineHiddenClass(bytecode, false, Lookup.ClassOption.NESTMATE).lookupClass();
    } catch (IllegalAccessException e) {
      // 1. Try getModuleLookup()
      try {
        Method getModuleLookup = hostClass.getDeclaredMethod("getModuleLookup");
        getModuleLookup.setAccessible(true);
        Lookup nextLookup = (Lookup) getModuleLookup.invoke(null);
        if (nextLookup != null && !nextLookup.equals(lookup)) {
          return nextLookup.defineHiddenClass(bytecode, false, Lookup.ClassOption.NESTMATE).lookupClass();
        }
      } catch (Throwable ignored) {
      }

      // 2. Try privateLookupIn
      try {
        Lookup nextLookup = MethodHandles.privateLookupIn(hostClass, MethodHandles.lookup());
        if (nextLookup != null && !nextLookup.equals(lookup)) {
          return nextLookup.defineHiddenClass(bytecode, false, Lookup.ClassOption.NESTMATE).lookupClass();
        }
      } catch (IllegalAccessException ignored) {
      }

      // 3. Try lookup().in()
      Lookup fallbackLookup = MethodHandles.lookup().in(hostClass);
      if (!fallbackLookup.equals(lookup)) {
        try {
          return fallbackLookup.defineHiddenClass(bytecode, false, Lookup.ClassOption.NESTMATE).lookupClass();
        } catch (IllegalAccessException ignored) {
        }
      }

      throw e;
    }
  }
}
