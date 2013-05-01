package com.axeiya.gwt.surface.client.processor;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public class ParagraphProcessor implements Processor {

  @Override
  public BodyElement process(BodyElement source) {
    if (!DOMUtil.hasStrongNode(source)) {
      Element paragraph = Document.get().createPElement();
      paragraph.appendChild(DOMUtil.createFocusBr());
      source.appendChild(paragraph);
    }
    return source;
  }

}
