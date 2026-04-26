/*
 * Copyright (C) 2020 Google Inc.
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

import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.InternalFlags.CustomClassLoadingOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry-point for defining dynamically generated classes.
 *
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
public final class ClassDefining {
  private ClassDefining() {}

  private static final Logger logger = Logger.getLogger(ClassDefining.class.getName());

  private static final String CLASS_DEFINING_UNSUPPORTED =
      "MethodHandles.Lookup is not accessible and custom classloading is turned OFF.";

  // initialization-on-demand...
  private static class ClassDefinerHolder {
    static final ClassDefiner INSTANCE;
    static final boolean USES_LOOKUP_DEFINER;

    static {
      Object[] result = bindClassDefiner();
      INSTANCE = (ClassDefiner) result[0];
      USES_LOOKUP_DEFINER = (Boolean) result[1];
    }
  }

  /** Defines a new class relative to the host. */
  public static Class<?> define(Class<?> hostClass, byte[] bytecode) throws Exception {
    return ClassDefinerHolder.INSTANCE.define(hostClass, bytecode);
  }

  /** Returns true if the current class definer allows access to package-private members. */
  public static boolean hasPackageAccess() {
    return ClassDefinerHolder.USES_LOOKUP_DEFINER;
  }

  /**
   * Returns true if it's possible to load by name proxies defined from the given host.
   * Always true since all current definers produce named classes.
   */
  public static boolean canLoadProxyByName(Class<?> hostClass) {
    return true;
  }

  /** Binds the preferred {@link ClassDefiner} instance and returns {definer, usesLookup}. */
  static Object[] bindClassDefiner() {
    CustomClassLoadingOption loadingOption = InternalFlags.getCustomClassLoadingOption();
    if (loadingOption == CustomClassLoadingOption.ANONYMOUS) {
      logger.log(Level.WARNING,
          "guice_custom_class_loading=ANONYMOUS is no longer supported (Unsafe removed). "
              + "Falling back to BRIDGE semantics.");
      loadingOption = CustomClassLoadingOption.BRIDGE;
    }
    if (loadingOption == CustomClassLoadingOption.CHILD) {
      return new Object[]{new ChildClassDefiner(), false};
    } else if (loadingOption == CustomClassLoadingOption.BRIDGE && LookupClassDefiner.isAccessible()) {
      // BRIDGE: try lookup-based definition, fall back to child class loader on failure
      ClassDefiner lookupDefiner = new LookupClassDefiner();
      ClassDefiner childDefiner = new ChildClassDefiner();
      ClassDefiner bridge = (hostClass, bytecode) -> {
        try {
          return lookupDefiner.define(hostClass, bytecode);
        } catch (Exception lookupFailure) {
          logger.log(Level.FINE,
              "Lookup-based class definition failed for " + hostClass.getName()
                  + "; falling back to child class loader.",
              lookupFailure);
          try {
            return childDefiner.define(hostClass, bytecode);
          } catch (Exception childFailure) {
            childFailure.addSuppressed(lookupFailure);
            throw childFailure;
          }
        }
      };
      return new Object[]{bridge, true};
    } else if (LookupClassDefiner.isAccessible()) {
      return new Object[]{new LookupClassDefiner(), true};
    } else if (loadingOption != CustomClassLoadingOption.OFF) {
      return new Object[]{new ChildClassDefiner(), false};
    } else {
      logger.warning(CLASS_DEFINING_UNSUPPORTED);
      ClassDefiner unsupported = (hostClass, bytecode) -> {
        throw new UnsupportedOperationException(
            "Cannot define class, " + CLASS_DEFINING_UNSUPPORTED);
      };
      return new Object[]{unsupported, false};
    }
  }
}
