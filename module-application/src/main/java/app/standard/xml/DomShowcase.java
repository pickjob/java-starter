package app.standard.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Document Object Model APIs (DOM)
 *      DocumentBuilderFactory: DocumentBuilder工厂
 *      DocumentBuilder
 *
 * @author: pickjob@126.com
 * @date: 2024-09-08
 */
public class DomShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String xml = """
                <?xml version="1.0"?>
                <root>
                    <child1>hello</child1>
                    <child2>world</child2>
                </root>
                """;
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = domFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        NodeList nodeList = doc.getElementsByTagName("child1");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            logger.info("{} content {}", node.getNodeName(), node.getTextContent());
        }
    }
}