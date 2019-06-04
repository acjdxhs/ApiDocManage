package com.scut.backend.uits;

import com.scut.backend.model.ApiNode;
import org.dom4j.*;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

public class XmlUtil {

    /**
     * xml 转 apiNode
     */
    public static ApiNode xmlToApiNode(String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            ApiNode result = new ApiNode();
            xmlToApiNodeHelper(root, result);
            return result;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * xml 转 apiNode
     * @param root
     * @param node
     */
    public static void xmlToApiNodeHelper(Element root, ApiNode node) {
        if (root == null) {
            return;
        }
        node.setName(root.getName());
        Attribute desc = root.attribute("nodeDesc");
        if (desc != null) {
            node.setDesc(desc.getValue());
            root.remove(desc);
        }
        for (Iterator it = root.attributeIterator(); it.hasNext(); ) {
            Attribute attribute = (Attribute) it.next();
            node.getAttribute().put(attribute.getName(), attribute.getValue());
        }

        for (Iterator it = root.elementIterator(); it.hasNext();) {
            Element e = (Element) it.next();
            ApiNode subNode = new ApiNode();
            xmlToApiNodeHelper(e, subNode);
            node.getChildren().add(subNode);
        }
    }

    /**
     * apiNode 转 xml
     * @param root
     * @return
     */
    public static String apiNodeToXml(ApiNode root) {
        Element element = DocumentHelper.createElement(root.getName());
        apiNodeToXmlHelper(root, element);
        OutputFormat format = new OutputFormat();
        format.setEncoding("UTF-8");
        format.setNewlines(true);
        format.setIndent("    ");
        StringWriter stringWriter = new StringWriter();
        XMLWriter writer = new XMLWriter(stringWriter, format);
        try {
            writer.write(element);
            writer.flush();
            writer.close();
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void apiNodeToXmlHelper(ApiNode root, Element element) {
        if (root == null) {
            return;
        }
        element.setName(root.getName());
        element.addAttribute("nodeDesc", root.getDesc());
        for (Map.Entry<String,String> entry : root.getAttribute().entrySet()) {
            element.addAttribute(entry.getKey(), entry.getValue());
        }

        for (ApiNode node: root.getChildren()) {
            Element e = element.addElement(node.getName());
            apiNodeToXmlHelper(node, e);
        }
    }

    public static void main(String[] args) throws DocumentException {
        String text = "<root desc=\"主目录\" rootAttr1=\"r1\" rootAttr2=\"r2\">\n" +
                "\t<sub1 desc=\"sub1\" subAttr1=\"haha\">\n" +
                "\t\t<sub21 desc=\"sub21\" subAttr21=\"hahaha\"></sub21>\n" +
                "\t\t<sub31 desc=\"sub31\" subAttr21=\"hahahaha\"></sub31>\n" +
                "\t</sub1>\n" +
                "\t<sub1 desc=\"sub2\" subAttr1=\"hehe\"></sub1>\n" +
                "\t<sub1 desc=\"sub3\" subAttr1=\"xixi\"></sub1>\n" +
                "\t<sub1></sub1>\n" +
                "</root>";

    }
}
