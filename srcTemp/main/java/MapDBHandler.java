import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 *  Parses OSM XML files using an XML SAX parser. Used to construct the graph of roads for
 *  pathfinding, under some constraints.
 *  See OSM documentation on
 *  <a href="http://wiki.openstreetmap.org/wiki/Key:highway">the highway tag</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Way">the way XML element</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Node">the node XML element</a>,
 *  and the java
 *  <a href="https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html">SAX parser tutorial</a>.
 *  @author Alan Yao
 */
public class MapDBHandler extends DefaultHandler {
    /**
     * Only allow for non-service roads; this prevents going on pedestrian streets as much as
     * possible. Note that in Berkeley, many of the campus roads are tagged as motor vehicle
     * roads, but in practice we walk all over them with such impunity that we forget cars can
     * actually drive on them.
     */
    private static final Set<String> ALLOWED_HIGHWAY_TYPES = new HashSet<>(Arrays.asList
            ("motorway", "trunk", "primary", "secondary", "tertiary", "unclassified",
                    "residential", "living_street", "motorway_link", "trunk_link", "primary_link",
                    "secondary_link", "tertiary_link"));
    private String activeState = "";
    private final GraphDB g;

    public MapDBHandler(GraphDB g) {
        this.g = g;
    }

    private Node currNode;
    private ArrayList<Long> toAdd = new ArrayList<>();

    /**
     * Called at the beginning of an element. Typically, you will want to handle each element in
     * here, and you may want to track the parent element.
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or
     *            if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are
     *              not available. This tells us which element we're looking at.
     * @param attributes The attributes attached to the element. If there are no attributes, it
     *                   shall be an empty Attributes object.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see Attributes
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        /* Some example code on how you might begin to parse XML files. */
        if (qName.equals("node")) {
            activeState = "node";
            currNode = new Node(Long.parseLong(attributes.getValue("id")),
                    Double.parseDouble(attributes.getValue("lon")),
                    Double.parseDouble(attributes.getValue("lat")));
            GraphDB.NODESET.put(Long.parseLong(attributes.getValue("id")), currNode);
        } else if (qName.equals("way")) {
            activeState = "way";
        } else if (activeState.equals("node") && qName.equals("tag")
                && attributes.getValue("k").equals("name")) {
            currNode.name = attributes.getValue("v");
            LinkedList ll = GraphDB.NAMETONODELIST.get(currNode.name);
            if (ll == null) {
                ll = new LinkedList();
            }
            ll.add(currNode);
            if (!GraphDB.NAMELIST.contains(currNode.name)) {
                GraphDB.NAMELIST.add(currNode.name);

            }
            GraphDB.NAMETONODELIST.put(currNode.name, ll);
        } else if (activeState.equals("way") && qName.equals("nd")) {
            toAdd.add(Long.parseLong(attributes.getValue("ref")));
        } else if (activeState.equals("way") && qName.equals("tag")
                && attributes.getValue("k").equals("highway")) {
            String v = attributes.getValue("v");
            if (ALLOWED_HIGHWAY_TYPES.contains(v)) {
                toAdd.add((long) -1);
            }
        }
    }

    /**
     * Receive notification of the end of an element. You may want to take specific terminating
     * actions here, like finalizing vertices or edges found.
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or
     *            if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are
     *              not available.
     * @throws SAXException  Any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("way")) {
            if (toAdd.get(toAdd.size() - 1) < 0) {
                for (int i = 0; i < toAdd.size() - 2; i++) {
                    int j = i + 1;
                    Node n1 = GraphDB.NODESET.get(toAdd.get(i));
                    Node n2 = GraphDB.NODESET.get(toAdd.get(j));
                    n1.connected.add(n2);
                    n2.connected.add(n1);
                }
            }
            toAdd.clear();
        }
    }

}
