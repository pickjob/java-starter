package app.standard.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Simple API for XML Parsing (SAX)
 *      SAXParserFactory
 *      SAXParser
 *          Handler:
 *              ContentHandler
 *              ErrorHandler
 *              DTDHandler
 *              EntityResolver
 *          DefaultHandler: ContentHandler, ErrorHandler, DTDHandler, EntityResolver
 *
 * @author: pickjob@126.com
 * @date: 2024-09-08
 */
public class SAXShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        String xml = """
                <?xml version="1.0"?>
                <root>
                    <child1>hello</child1>
                    <child2>world</child2>
                </root>
                """;
        SAXParserFactory saxFactory = SAXParserFactory.newDefaultInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                logger.info("startElement: {}", qName);
            }
            @Override
            public void endElement (String uri, String localName, String qName) throws SAXException {
                logger.info("endElement: {}", qName);
            }
        };
        saxParser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
    }
}