/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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
package org.codelibs.core.xml;

import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;

import org.codelibs.core.io.ResourceUtil;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * {@link SAXParserFactoryUtil}のテストです。
 *
 * @author koichik
 */
public class SAXParserFactoryUtilTest extends TestCase {

    boolean included;

    /**
     * {@link SAXParserFactoryUtil#setXIncludeAware}のテストです。
     *
     * @throws Exception
     */
    public void testSetXIncludeAware() throws Exception {
        final SAXParserFactory spf = SAXParserFactoryUtil.newInstance();
        SAXParserFactoryUtil.setXIncludeAware(spf, true);
        spf.setNamespaceAware(true);
        final SAXParser parser = SAXParserFactoryUtil.newSAXParser(spf);

        final InputSource is = new InputSource(ResourceUtil.getResourceAsStream("org/codelibs/core/xml/include.xml"));
        is.setSystemId("include.xml");
        parser.parse(is, new DefaultHandler() {

            @Override
            public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
                    throws SAXException {
                if ("bar".equals(qName)) {
                    included = true;
                }
            }

            @Override
            public InputSource resolveEntity(final String publicId, final String systemId) throws IOException, SAXException {
                final InputSource is = new InputSource(ResourceUtil.getResourceAsStream("org/codelibs/core/xml/included.xml"));
                is.setSystemId("included.xml");
                return is;
            }

        });
        assertTrue(included);
    }
}
