package xswingver2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class ModelsByXML {

	public static void main(String[] args) {
		TestParseXml.parseSitesByStreamReader();
		TestParseXml.parseLinesByStreamReader();
	}

	//
	public ModelsByXML() {

	}

	//
	private static XMLStreamReader getStreamReader() {
		String xmlFile = ModelsByXML.class.getResource("/").getFile() + "xswingver2/users.xml";
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

	//
	public void parseSitesByStreamReader(Map<String, LabelModel> sitesM, Map<String, LineModel> linesM) {
		XMLStreamReader reader = ModelsByXML.getStreamReader();
		LabelModel site = null;
		LineModel line = null;
		try {
			while (reader.hasNext()) {
				if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if ("site".equalsIgnoreCase(reader.getLocalName())) {
						site = new LabelModel();
						site.setId(reader.getAttributeValue(0));
						site.setIcon(reader.getAttributeValue(1));
						site.setIcon_f(reader.getAttributeValue(2));
						site.setXpos(Integer.parseInt(reader.getAttributeValue(3)));
						site.setYpos(Integer.parseInt(reader.getAttributeValue(4)));
						site.setWidth(Integer.parseInt(reader.getAttributeValue(5)));
						site.setHeight(Integer.parseInt(reader.getAttributeValue(6)));
						site.setStatus(Boolean.parseBoolean(reader.getAttributeValue(7)));
						site.setName(reader.getAttributeValue(8));
						sitesM.put(site.getId(), site);
					} else if ("line".equalsIgnoreCase(reader.getLocalName())) {
						line = new LineModel();
						line.setId(reader.getAttributeValue(0));
						line.setLineWidth(reader.getAttributeValue(1));
						line.setDash(reader.getAttributeValue(2));
						line.setColor(reader.getAttributeValue(3));
						line.setX1pos(Integer.parseInt(reader.getAttributeValue(4)));
						line.setY1pos(Integer.parseInt(reader.getAttributeValue(5)));
						line.setX2pos(Integer.parseInt(reader.getAttributeValue(6)));
						line.setY2pos(Integer.parseInt(reader.getAttributeValue(7)));
						line.setStatus(Boolean.parseBoolean(reader.getAttributeValue(8).trim()));
						line.setName(reader.getAttributeValue(9));
						line.setType(reader.getAttributeValue(11).trim());
						linesM.put(line.getId(), line);
					}
				}
				reader.next();
			}
			reader.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	//
	public void parseNetSitesByStreamReader(List<NetSiteModel> results) {
		XMLStreamReader reader = ModelsByXML.getStreamReader();
		NetSiteModel nsm = null;
		try {
			while (reader.hasNext()) {
				if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if ("line".equalsIgnoreCase(reader.getLocalName())) {
						if (!"three".equals(reader.getAttributeValue(11).trim())) {
							nsm = new NetSiteModel();
							nsm.setId(reader.getAttributeValue(0).trim());
							nsm.setStatus(Boolean.parseBoolean(reader.getAttributeValue(8).trim()));
							nsm.setName(reader.getAttributeValue(9));
							nsm.setIpaddr(reader.getAttributeValue(10).trim());
							results.add(nsm);
						}
					}
				}
				reader.next();
			}
			reader.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	//
	public static void printSitesByStreamReader() {
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

	//
	public static void printLinesByStreamReader() {
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
