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
package org.codelibs.core.net;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.io.CloseableUtil;
import org.codelibs.core.io.ResourceUtil;

/**
 * Utility class for MIME types.
 *
 * @author shot
 */
public abstract class MimeTypeUtil {

    /**
     * Do not instantiate.
     */
    private MimeTypeUtil() {
    }

    /**
     * Guesses the content type.
     *
     * @param path
     *            The path. Must not be {@literal null} or an empty string.
     * @return The content type.
     */
    public static String guessContentType(final String path) {
        assertArgumentNotEmpty("path", path);

        final InputStream is = ResourceUtil.getResourceAsStream(path);
        try {
            final String mimetype = URLConnection.guessContentTypeFromStream(is);
            if (mimetype != null) {
                return mimetype;
            }
            return URLConnection.guessContentTypeFromName(path);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        } finally {
            CloseableUtil.close(is);
        }
    }

}
