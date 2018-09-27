package xswingver2;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class TestParseXml {
	public static void main(String[] args) {
		TestParseXml.parseSitesByStreamReader();
		TestParseXml.parseLinesByStreamReader();
		
	}
	public static XMLStreamReader getStreamReader() {
		String xmlFile = TestParseXml.class.getResource("/").getFile() + "xswingver2/users.xml";
		XMLInputFactory factory = XMLInputFactory.newFactory();
		try {
			XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(xmlFile));
			return reader;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void parseSitesByStreamReader() {
		XMLStreamReader reader = TestParseXml.getStreamReader();
		try {
			while (reader.hasNext()) {
				if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if ("site".equalsIgnoreCase(reader.getLocalName())) {
						for (int index = 0; index < reader.getAttributeCount(); index++) {
							System.out.print(
									reader.getAttributeLocalName(index) + "=" + reader.getAttributeValue(index) + ";");
						}
						System.out.println();
					}
				}
				reader.next();
			}
			reader.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	public static void parseLinesByStreamReader() {
		XMLStreamReader reader = TestParseXml.getStreamReader();
		try {
			while (reader.hasNext()) {
				if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if ("line".equalsIgnoreCase(reader.getLocalName())) {
						for (int index = 0; index < reader.getAttributeCount(); index++) {
							System.out.print(
									reader.getAttributeLocalName(index) + "=" + reader.getAttributeValue(index) + ";");
						}
						System.out.println();
					}
				}
				reader.next();
			}
			reader.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
}
