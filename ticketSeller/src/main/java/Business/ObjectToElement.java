/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Business;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ObjectToElement {

    abstract public Element toXMLElement(Document doc, Object object);
    
}
