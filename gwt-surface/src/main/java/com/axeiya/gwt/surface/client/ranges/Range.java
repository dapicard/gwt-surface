package com.axeiya.gwt.surface.client.ranges;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Node;
import com.axeiya.gwt.surface.client.util.DOMUtil;

final public class Range extends JavaScriptObject {

  protected Range() {
  }

  public native final static Range createRange() /*-{
		return $wnd['rangy'].createRange();
  }-*/;

  public native final static Range createIFrameRange(IFrameElement iframe) /*-{
		return $wnd['rangy'].createRange(iframe);
  }-*/;

  public native String toHtml() /*-{
		return this.toHtml();
  }-*/;

  /**
   * Returns the node within which the range starts.
   * 
   * @return
   */
  public native Node getStartContainer() /*-{
		return this.startContainer;
  }-*/;

  /**
   * Returns number representing the offset of the start of the range within its startContainer. In
   * the case of character-based nodes (text nodes, CDATA nodes and comment nodes), this is the
   * number of characters before the start of the range. For other nodes, it's the number of child
   * nodes of startContainer preceding the start of the range.
   * 
   * @return
   */
  public native int getStartOffset() /*-{
		return this.startOffset;
  }-*/;

  /**
   * Returns the node within which the range ends.
   * 
   * @return
   */
  public native Node getEndContainer() /*-{
		return this.endContainer;
  }-*/;

  /**
   * Returns number representing the offset of the end of the range within its endContainer. In the
   * case of character-based nodes (text nodes, CDATA nodes and comment nodes), this is the number
   * of characters before the end of the range. For other nodes, it's the number of child nodes of
   * endContainer preceding the end of the range.
   * 
   * @return
   */
  public native int getEndOffset() /*-{
		return this.endOffset;
  }-*/;

  /**
   * Returns the deepest node within the document that contains the whole range
   * 
   * @return
   */
  // public native Node getCommonAncestorContainer() /*-{
  // return this.commonAncestorContainer
  // }-*/;

  public Node getCommonAncestorContainer() {
    if(getStartContainer() == null || getEndContainer() == null) {
      return null;
    }
    return DOMUtil.getCommonAncestor(getStartContainer(), getEndContainer());
  }

  /**
   * Returns a Boolean indicating whether the range is collapsed (i.e. the start boundary is
   * identical to the end boundary).
   * 
   * @return
   */
  public native boolean isCursor() /*-{
		return this.collapsed;
  }-*/;

  public native void selectNode(Node node) /*-{
		this.selectNode(node);
  }-*/;

  public native void setStart(Node node, int offset) /*-{
		this.setStart(node, offset);
  }-*/;

  public native void setEnd(Node node, int offset) /*-{
		this.setEnd(node, offset);
  }-*/;

  /**
   * Returns a Boolean indicating whether the position represented by the specified node and offset
   * is contained within the current range. The boundary positions of the range are considered to be
   * within the range for the purposes of this method.
   * 
   * @param node
   * @param offset
   * @return
   */
  public native boolean isPointInRange(Node node, int offset) /*-{
		return this.isPointInRange(node, offset);
  }-*/;

  /**
   * Returns a Boolean indicating whether the range contains (or partially contains, if partial is
   * true) the specified node.
   * 
   * @param node
   * @param partial
   * @return
   */
  public native boolean containsNode(Node node, boolean partial) /*-{
		return this.containsNode(node, partial);
  }-*/;

  /**
   * Returns a Boolean indicating whether the range contains the specified node's contents.
   * 
   * @param node
   * @return
   */
  public native boolean containsNodeContents(Node node) /*-{
		return this.containsNodeContents(node);
  }-*/;

  /**
   * Returns a Boolean indicating whether the range contains all of the text (within text nodes)
   * contained within node. This is to provide an intuitive means of checking whether a range
   * "contains" a node if you consider the range as just in terms of the text it contains without
   * having to worry about niggly details about range boundaries.
   * 
   * @param node
   * @return
   */
  public native boolean containsNodeText(Node node) /*-{
		return this.containsNodeText(node);
  }-*/;

  /**
   * Returns a Boolean indicating whether the current range completely contains range.
   * 
   * @param range
   * @return
   */
  public native boolean containsRange(Range range) /*-{
		return this.containsRange(range);
  }-*/;

  public boolean equivalent(Range other) {
    return other != null && this.getStartContainer() == other.getStartContainer()
        && this.getStartOffset() == other.getStartOffset()
        && this.getEndContainer() == other.getEndContainer()
        && this.getEndOffset() == other.getEndOffset();
  }

  /**
   * Deletes the content contained within the range from the document.
   */
  public native void deleteContents() /*-{
		this.deleteContents();
  }-*/;

  /**
   * Collapses the range to either its the start or end boundary, depending on toStart.
   */
  public native void collapse(boolean toStart) /*-{
		this.collapse(toStart);
  }-*/;

}
