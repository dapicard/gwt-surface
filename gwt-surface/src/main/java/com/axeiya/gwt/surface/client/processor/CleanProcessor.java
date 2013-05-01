package com.axeiya.gwt.surface.client.processor;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class CleanProcessor implements Processor {

  @Override
  public BodyElement process(BodyElement source) {
    JsArray<Node> toProcess = (JsArray<Node>) JsArray.createArray();
    extractChild(source, toProcess);
    Node node;
    while ((node = toProcess.shift()) != null) {
      int type;
      try {
        type = node.getNodeType();
        if (type == Node.TEXT_NODE) {
          if (node.getNodeValue().isEmpty()) {
            // On supprime les noeuds textes vides
            node.removeFromParent();
          }
        } else if (type == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          if (element.getAttribute(DOMUtil.DIRTY_ATTRIBUTE) != null
              && element.getAttribute(DOMUtil.DIRTY_ATTRIBUTE).equals("true")) {
            node.removeFromParent();
          } else {
            extractChild(node, toProcess);
          }
        }
      } catch (Exception e) {
        node.removeFromParent();
      }
    }
    return source;
  }

  private void extractChild(Node node, JsArray<Node> queue) {
    Node child = node.getFirstChild();
    while (child != null) {
      queue.push(child);
      child = child.getNextSibling();
    }
  }

}
