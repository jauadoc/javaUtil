package com.sun.utils.utils;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * XmlParse use dom4j, use xpath to find node.
 * @author sunx(sunxin@strongit.com.cn)
 */
public class T_XmlParser{
    /**
     * xml's document Object
     */
    private Document document;

    /**
     * Constructor
     * @param xmlFilePath xml's file path
     */
    public T_XmlParser(String xmlFilePath) {
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(new File(xmlFilePath));
        } catch (DocumentException e) {
            System.out.println("xml文件读取异常");
            System.out.println(e);
        }
    }

    /**
     * user xpath expression get Element list
     * @param xpathExpress
     * @return element list
     */
    @SuppressWarnings("unchecked")
    public List<Element> getElements(String xpathExpress){

        return this.document.selectNodes(xpathExpress);
    }


    /**
     * user xpath expression get single Element
     * @param xpathExpress
     * @return element
     */
    public Element getELement(String xpathExpress){

        return (Element) this.document.selectSingleNode(xpathExpress);
    }

    /**
     * get element content
     * @param element
     * @return element's content
     */
    public String getElementContent(Element element){

        return element.getText().trim();
    }

    /**
     * get element content
     * if not exist return null
     * @param element
     * @return element's content
     */
    public String getElementContent(String xpathExpress){

        Element element = getELement(xpathExpress);
        if(null != element){
            return element.getText().trim();
        }else{
            return null;
        }
    }

    /**
     * xpathExpression is exist Element
     * @param xpathExpress
     * @return is element exist
     */
    public boolean exist(String xpathExpress){

        if(null == getELement(xpathExpress)){
            return false;
        }

        return true;
    }

}
