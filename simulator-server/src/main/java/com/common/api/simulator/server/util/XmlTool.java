package com.common.api.simulator.server.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;

import java.util.List;

public class XmlTool {

    /**
     * String 转 org.dom4j.Document
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static Document strToDocument(String xml) throws DocumentException {
        return DocumentHelper.parseText(xml);
    }

    /**
     * org.dom4j.Document 转  com.alibaba.fastjson.JSONObject
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static JSONObject documentToJSONObject(String xml) throws DocumentException {
        return elementToJSONObject(strToDocument(xml).getRootElement());
    }

    /**
     * org.dom4j.Element 转  com.alibaba.fastjson.JSONObject
     * @param node
     * @return
     */
    public static JSONObject elementToJSONObject(Element node) {
        JSONObject result = new JSONObject();
        // 当前节点的名称、文本内容和属性
        List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
        for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
            result.put(attr.getName(), attr.getValue());
        }
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();// 所有一级子节点的list
        if (!listElement.isEmpty()) {
            for (Element e : listElement) {// 遍历所有一级子节点
                if (e.attributes().isEmpty() && e.elements().isEmpty()) // 判断一级节点是否有属性和子节点
                    result.put(e.getName(), e.getTextTrim());// 沒有则将当前节点作为上级节点的属性对待
                else {
                    if (!result.containsKey(e.getName())) // 判断父节点是否存在该一级节点名称的属性
                        result.put(e.getName(), new JSONArray());// 没有则创建
                    ((JSONArray) result.get(e.getName())).add(elementToJSONObject(e));// 将该一级节点放入该节点名称的属性对应的值中
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws DocumentException {
        System.out.println(XmlTool.documentToJSONObject("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsm=\"http://wsm.advcash/\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <wsm:sendMoney>\n" +
                "            <arg0>\n" +
                "                <apiName>channel-advcash</apiName>\n" +
                "                <authenticationToken>ea3eb9000f472b2f60e1df1ecd642f3f7bfac6b483de87fb6aa3f3f41fb7215a</authenticationToken>\n" +
                "                <accountEmail>payment@siupay.com</accountEmail>\n" +
                "            </arg0>\n" +
                "            <arg1>\n" +
                "                <amount>0.12</amount>\n" +
                "                <currency>USD</currency>\n" +
                "                <email>kim@corp.siupay.com</email>\n" +
                "                <note>kim-test</note>\n" +
                "                <savePaymentTemplate>false</savePaymentTemplate>\n" +
                "                <note>aabbcc</note>\n" +
                "            </arg1>\n" +
                "        </wsm:sendMoney>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>"));
    }
}