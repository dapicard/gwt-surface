package com.axeiya.gwt.surface.client.util;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Text;
import com.google.gwt.regexp.shared.RegExp;
import com.axeiya.gwt.surface.client.ranges.Range;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;

public class DOMUtil {

  public static final String ROOT_ATTRIBUTE = "root_body";
  public static final String DIRTY_ATTRIBUTE = "sc_dirty";
  public static final List<String> SELF_CLOSING_TAGS = Arrays.asList("img", "br", "hr");
  public static final RegExp lt = RegExp.compile("<", "gm");
  public static final RegExp gt = RegExp.compile(">", "gm");

  public static class DOMUtilImpl {
    public String getAttributeStringValue(Element element, String attributeName) {
      return element.getAttribute(attributeName);
    }

    public Node createFocusBr() {
      BRElement br = Document.get().createBRElement();
      br.setAttribute(DIRTY_ATTRIBUTE, "true");
      return br;
    };
  }

  public static class DOMUtilImplIE extends DOMUtilImpl {
    public native String getAttributeStringValue(Element element, String attributeName) /*-{
			if (attributeName == "style") {
				return element.style.cssText.toLowerCase();
			} else if (attributeName == "class") {
				return element.className;
			}
			return eval('(element.' + attributeName + ')');
    }-*/;

    public Node createFocusBr() {
      return Document.get().createTextNode("");
    }
  }

  private static DOMUtilImpl impl = GWT.create(DOMUtilImpl.class);

  /**
   * Indique si le noeud passé en paramètre contient des enfants forts :<br>
   * Un noeud est fort si :
   * <ul>
   * <li>Il contient au moins un noeud de type ELEMENT</li>
   * <li>Il contient au moins un noeud de type TEXT non vide</li>
   * </ul>
   * 
   * @param node
   * @return
   */
  public static boolean hasStrongNode(Node node) {
    try {
      node.getNodeType();
    } catch (Exception e) {
      return true;
    }
    if (node.getNodeType() == Node.TEXT_NODE) {
      return !node.getNodeValue().isEmpty();
    }
    if (node.getNodeName().equalsIgnoreCase("img")) {
      return true;
    }
    Node nextSibling, child = node.getFirstChild();
    while (child != null) {
      nextSibling = child.getNextSibling();
      if (child.getNodeType() == Node.TEXT_NODE) {
        if (child.getNodeValue() != null && !child.getNodeValue().isEmpty()) {
          return true;
        }
      } else if (child.getNodeName().equalsIgnoreCase("br") && nextSibling == null) {
        return !BRElement.as(child).getAttribute(DIRTY_ATTRIBUTE).equals("true");
      } else {
        return true;
      }
      child = child.getNextSibling();
    }
    return false;
  }

  public static Node getPreviousStrongSibling(Node node) {
    Node previous = node.getPreviousSibling();
    while (previous != null && !hasStrongNode(previous)) {
      previous = previous.getPreviousSibling();
    }
    return previous;
  }

  public static Node getNextStrongSibling(Node node) {
    Node next = node.getNextSibling();
    while (next != null && !hasStrongNode(next)) {
      next = next.getNextSibling();
    }
    return next;
  }

  /**
   * Indique si le noeud passé en paramètre est seul dans son parent
   * 
   * @param child
   * @return
   */
  public static boolean isAloneChild(Node child) {
    return child.getParentNode().getFirstChild().equals(child)
        && child.getParentNode().getFirstChild().getNextSibling() == null;
  }

  /**
   * Nettoie la branche sur laquelle est assis le noeud passé en paramètre. Supprime les noeuds
   * textes vides ; supprime les éléments vides
   * 
   * @param node
   */
  public static void cleanBranch(Node node) {
    Node parent;
    while (!hasStrongNode(node)) {
      parent = node.getParentNode();
      node.removeFromParent();
      node = parent;
    }
  }

  /**
   * Indique si un noeud ne contient que des TextNodes
   * 
   * @param node
   * @return
   */
  public static boolean containsOnlyTextNodesBetweenNodes(Node parent, Node startChild,
      Node endChild) {
    Node child = parent.getFirstChild();
    boolean started = false, finished = false;
    while (child != null && !finished) {
      if (child.equals(startChild)) {
        started = true;
      }
      if (started) {
        if (child.getNodeType() != Node.TEXT_NODE) {
          return false;
        }
      }
      if (child.equals(endChild)) {
        finished = true;
      }
      child = child.getNextSibling();
    }
    return true;
  }

  public static SurfaceSelection glueTextNodes(SurfaceSelection selection, Document document) {
    Range range = selection.getSelection().getRange();
    Node startNode = range.getStartContainer();
    Node endNode = range.getEndContainer();
    Node commonParent = startNode.getParentNode();
    Node sibling = startNode, nextSibling;
    String out = "";
    int endOffset = 0;
    while (sibling != null && !sibling.equals(endNode)) {
      endOffset += sibling.getNodeValue().length();
      out += sibling.getNodeValue();
      sibling = sibling.getNextSibling();
    }
    endOffset += range.getEndOffset();
    out += endNode.getNodeValue();
    Text replacementNode = document.createTextNode(out);
    commonParent.insertBefore(replacementNode, startNode);
    sibling = startNode;
    while (sibling != null && !sibling.equals(endNode)) {
      nextSibling = sibling.getNextSibling();
      sibling.removeFromParent();
      sibling = nextSibling;
    }
    endNode.removeFromParent();
    // Reste à mettre en place la nouvelle sélection
    range.setStart(replacementNode, range.getStartOffset());
    range.setEnd(replacementNode, endOffset);
    selection.getAssociatedSurface().setSelection(range);
    return selection;
  }

  public static Node createFocusBr() {
    return impl.createFocusBr();
  }

  public static boolean atEnd(Element ancestor, Range range) {
    Node endNode = range.getEndContainer();
    int endOffset = range.getEndOffset();
    if (endNode.getNodeType() == Node.TEXT_NODE) {
      String allContent = ancestor.getInnerText();
      String endText = endNode.getNodeValue();
      allContent = allContent.substring(allContent.length() - endText.length());
      return range.isCursor() && endOffset == endText.length() && endText.equals(allContent);
    } else {
      if (ancestor.equals(endNode)) {
        int childCount = getSaneChildCount(ancestor);
        return endOffset >= (childCount);
      } else {
        // FIXME : je pense qu'il faudrait vérifier quand même
        return false;
      }
    }
  }

  public static int getSaneChildCount(Node node) {
    if (node.getNodeType() == Node.TEXT_NODE) {
      return 0;
    }
    int count = 0;
    Node child = node.getFirstChild();
    while (child != null) {
      if (child.getNodeType() == Node.TEXT_NODE) {
        count++;
      } else {
        Element element = (Element) child;
        if (child.getNextSibling() != null) {
          count++;
        } else if (!"true".equals(element.getAttribute(DIRTY_ATTRIBUTE))) {
          count++;
        }
      }
      child = child.getNextSibling();
    }
    return count;
  }

  public static Node getCommonAncestor(Node node1, Node node2) {
    if (node1.isOrHasChild(node2)) {
      return node1;
    }
    if (node2.isOrHasChild(node1)) {
      return node2;
    }
    Element ancestor = node1.getParentElement();
    while (ancestor != null) {
      if (ancestor.isOrHasChild(node2)) {
        return ancestor;
      }
      if ("true".equals(ancestor.getAttribute(ROOT_ATTRIBUTE))) {
        return null;
      }
      ancestor = ancestor.getParentElement();
    }
    return null;
  }

  public static void debugNode(Node node) {
    int nodeType = -1;
    try {
      nodeType = node.getNodeType();
    } catch (Exception e) {
      GWT.log("undefined nodeType", e);
    }
    GWT.log("<" + node.getNodeName() + " nodeType=\"" + nodeType + "\">" + node.getNodeValue()
        + "</" + node.getNodeName() + ">");
  }

  public static Element getFirstAncestorOfType(Node node, String tag) {
    if (node == null) {
      return null;
    }
    if (node.getNodeType() == Node.TEXT_NODE) {
      node = node.getParentNode();
    }
    Element ancestor = (Element) node;
    while (ancestor != null) {
      if (ancestor.getTagName() != null && ancestor.getTagName().equalsIgnoreCase(tag)) {
        return ancestor;
      }
      if ("true".equals(ancestor.getAttribute(ROOT_ATTRIBUTE))) {
        return null;
      }
      ancestor = ancestor.getParentElement();
    }
    return null;
  }

  public static Element getFirstAncestorInTypes(Node node, List<String> tags) {
    if (node == null) {
      return null;
    }
    if (node.getNodeType() == Node.TEXT_NODE) {
      node = node.getParentNode();
    }
    Element ancestor = (Element) node;
    while (ancestor != null) {
      if (ancestor.getTagName() != null && tags.contains(ancestor.getTagName().toLowerCase())) {
        return ancestor;
      }
      if ("true".equals(ancestor.getAttribute(ROOT_ATTRIBUTE))) {
        return null;
      }
      ancestor = ancestor.getParentElement();
    }
    return null;
  }

  public static Element getFirstChildOfType(Element element, String tag) {
    @SuppressWarnings("unchecked")
    JsArray<Element> elements = (JsArray<Element>) JsArray.createArray();
    Element elem = element;
    do {
      if (elem.getTagName().equalsIgnoreCase(tag)) {
        return elem;
      }
      Node child = elem.getFirstChild();
      while (child != null) {
        if (child.getNodeType() == Node.ELEMENT_NODE) {
          elements.push((Element) child);
        }
        child = child.getNextSibling();
      }
      elem = elements.shift();
    } while (elem != null);
    return null;
  }

  /**
   * Supprime tout le contenu du noeud
   * 
   * @param node
   */
  public static void clear(Node node) {
    if (node.getNodeType() == Node.TEXT_NODE) {
      node.setNodeValue("");
    } else {
      Node nextSibling, child = node.getFirstChild();
      while (child != null) {
        nextSibling = child.getNextSibling();
        child.removeFromParent();
        child = nextSibling;
      }
    }
  }

  public static void getXhtml(Node node, StringBuilder sb) {

    if (node.getNodeType() == Node.TEXT_NODE) {
      sb.append(lt.replace(gt.replace(node.getNodeValue(), "&gt;"), "&lt;"));
    } else if (node.getNodeType() == Node.ELEMENT_NODE) {
      Element element = (Element) node;
      sb.append("<" + node.getNodeName().toLowerCase());
      int i = 0;
      JsArray<Node> attributes = getAttributes(element);
      while (i < attributes.length()) {
        Node attribute = attributes.get(i);
        String nodeName = attribute.getNodeName();
        String nodeValue = impl.getAttributeStringValue(element, nodeName);
        sb.append(" " + nodeName.toLowerCase() + "=\""
            + (nodeValue != null ? nodeValue.replaceAll("&", "&amp;") : "") + "\"");
        i++;
      }
      if (!element.hasChildNodes()) {
        if (SELF_CLOSING_TAGS.contains(element.getTagName().toLowerCase())) {
          sb.append("/>");
        } else {
          sb.append("></" + element.getTagName().toLowerCase() + ">");
        }
      } else {
        sb.append(">");
        Node child = element.getFirstChild();
        while (child != null) {
          getXhtml(child, sb);
          child = child.getNextSibling();
        }
        sb.append("</" + element.getNodeName().toLowerCase() + ">");
      }
    }
  }

  private static native JsArray<Node> getAttributes(Element element) /*-{
		var attributes = [];
		for ( var i = 0; i < element.attributes.length; i++) {
			if (element.attributes[i].specified) {
				attributes.push(element.attributes[i]);
			}
		}
		return attributes;
  }-*/;

  public static void debugJSObject(JavaScriptObject object) {
    GWT.log(dump(object, 0));
  }

  private static native String dump(JavaScriptObject arr, int level) /*-{
		if (level > 10) {
			return "";
		}
		var dumped_text = "";

		var level_padding = "";
		for ( var j = 0; j < level + 1; j++)
			level_padding += "    ";

		if (typeof (arr) == 'object') {
			for ( var item in arr) {
				var value = arr[item];

				if (typeof (value) == 'object') {
					//					dumped_text += level_padding + "'" + item + "' ...\n";
					//					dumped_text += @com.axeiya.stillcollab.wysiwyg.client.util.DOMUtil::dump(Lcom/google/gwt/core/client/JavaScriptObject;I)(value, level + 1);
				} else {
					dumped_text += level_padding + "'" + item + "' => \""
							+ value + "\"\n";
				}
			}
		} else {
			dumped_text = "===>" + arr + "<===(" + typeof (arr) + ")";
		}
		return dumped_text;
  }-*/;
}
