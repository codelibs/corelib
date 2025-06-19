/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.core.io;

/**
 * Object representing a collection of classes or resources.
 *
 * @author koichik
 * @see TraversalUtil
 */
public interface Traverser {

    /**
     * Returns <code>true</code> if the class file corresponding to the specified class name exists in the resources handled by this instance.
     * <p>
     * If a root package is specified at instance construction, the specified class name is interpreted as a relative name from the root package.
     * </p>
     *
     * @param className the class name
     * @return <code>true</code> if the class file exists in the resources handled by this instance
     */
    boolean isExistClass(final String className);

    /**
     * Searches for classes handled by this instance and calls the handler for each class.
     * <p>
     * If a root package is specified at instance construction, only classes under the root package are targeted.
     * </p>
     *
     * @param handler the handler to process classes
     */
    void forEach(ClassHandler handler);

    /**
     * Searches for resources handled by this instance and calls the handler for each resource.
     * <p>
     * If a root directory is specified at instance construction, only resources under the root directory are targeted.
     * </p>
     *
     * @param handler the handler to process resources
     */
    void forEach(ResourceHandler handler);

    /**
     * Performs post-processing of resources.
     */
    void close();

}
