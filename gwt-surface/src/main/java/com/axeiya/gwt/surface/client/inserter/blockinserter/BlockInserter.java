package com.axeiya.gwt.surface.client.inserter.blockinserter;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.axeiya.gwt.surface.client.inserter.Inserter;
import com.axeiya.gwt.surface.client.inserter.action.InsertAction;
import com.axeiya.gwt.surface.client.ranges.Range;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

/**
 * Cet Inserter injecte un élément du type générique indiquée autour de la
 * sélection courante
 * 
 * @author Damien Picard
 * 
 * @param <E>
 *          Type encapsulant
 */
abstract public class BlockInserter<E extends Element> extends Inserter {

	protected InsertAction<E> action;

	protected BlockInserter() {
	}

	protected BlockInserter(InsertAction<E> action) {
		this.action = action;
	}

	public void remove(SurfaceSelection selection) {
		Element ancestor = getCommonMatchingAncestor(selection);
		if (ancestor != null && ancestor.getParentElement() != null) {
			// On rattache les enfants de ancestor à son parent
			Node parent = ancestor.getParentElement();
			Node nextSibling, child = ancestor.getFirstChild();
			while (child != null) {
				nextSibling = child.getNextSibling();
				child.removeFromParent();
				parent.insertBefore(child, ancestor);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					action.onRevert((Element) child, selection);
				}
				child = nextSibling;
			}
			ancestor.removeFromParent();
		}
	}

	public void insert(final SurfaceSelection selection) {
		final Range range = selection.getSelection().getRange();

		Node startNode = range.getStartContainer();
		Document document = startNode.getOwnerDocument();
		String startName = startNode.getNodeName();
		Node startParent = startNode.getParentNode();
		Node endNode = range.getEndContainer();
		Node ancest = range.getCommonAncestorContainer();
		if (ancest.equals(startNode) || ancest.equals(endNode)) {
			ancest = startNode.getParentNode();
		}
		final Node ancestor = ancest;
		final Element container = document.createElement(action.getEmptyElement().getTagName());

		// La sélection est sur des noeuds textes contigues
		if (!startNode.equals(endNode) && startNode.getNodeType() == Node.TEXT_NODE && endNode.getNodeType() == Node.TEXT_NODE
				&& startNode.getParentNode().equals(endNode.getParentNode()) && DOMUtil.containsOnlyTextNodesBetweenNodes(startNode.getParentNode(), startNode, endNode)) {
			// Dans ce cas, on doit rassembler les noeuds texte avant traitement
			// et on relance
			insert(DOMUtil.glueTextNodes(selection, document));
			return;
		}

		if (startNode == null || endNode == null) {
			ancestor.appendChild(container);
		} else if (startNode.equals(endNode)) {
			if (startNode.getNodeType() == Node.TEXT_NODE) {
				// Inline insertion
				String texte = startNode.getNodeValue();
				String before = texte.substring(0, range.getStartOffset());
				String inner = texte.substring(range.getStartOffset(), range.getEndOffset());
				String after = texte.substring(range.getEndOffset());

				container.appendChild(document.createTextNode(inner));

				startNode.setNodeValue(before);
				ancestor.insertAfter(container, startNode);
				Node afterNode = document.createTextNode(after);
				ancestor.insertAfter(afterNode, container);
			} else {
				if (range.getStartOffset() >= startNode.getChildCount()) {
					startNode.appendChild(container);
				} else {
					startNode.insertBefore(container, startNode.getChild(range.getStartOffset()));
				}
			}
		} else {
			Node nextSibling, child = ancestor.getFirstChild();
			boolean started = false, finished = false;
			while (child != null && !finished) {
				if (started && range.containsNode(child, false)/*
																												 * !child.isOrHasChild(
																												 * endNode)
																												 */) {
					nextSibling = child.getNextSibling();
					final Node splittingPoint = child;
					Scheduler.get().scheduleFinally(new ScheduledCommand() {
						@Override
						public void execute() {
							splittingPoint.removeFromParent();
							container.appendChild(splittingPoint);
						}
					});

					child = nextSibling;
				} else {
					if (!started && range.containsNode(child, true)/*
																													 * child.isOrHasChild(
																													 * startNode)
																													 */) {
						started = true;
						if (child.equals(startNode)) {
							nextSibling = child.getNextSibling();
							if (child.getNodeType() == Node.TEXT_NODE) {
								String texte = startNode.getNodeValue();
								final String before = texte.substring(0, range.getStartOffset());
								String inner = texte.substring(range.getStartOffset());

								final Node copy = document.createTextNode(inner);
								final Node insertPoint = nextSibling;
								final Node splittingPoint = child;
								Scheduler.get().scheduleFinally(new ScheduledCommand() {
									@Override
									public void execute() {
										container.appendChild(copy);
										ancestor.insertBefore(container, insertPoint);
										splittingPoint.setNodeValue(before);
										DOMUtil.cleanBranch(splittingPoint);
									}
								});
							} else {
								// Dans ce cas, on recopie à partir de l'offset
								final Node insertPoint = nextSibling;
								final Node splittingPoint = child;
								final Node start = startNode;
								final int offset = range.getStartOffset();
								Scheduler.get().scheduleFinally(new ScheduledCommand() {
									@Override
									public void execute() {
										ancestor.insertBefore(container, insertPoint);
										Node copy = splittingPoint.cloneNode(false);
										for (int i = offset; i < start.getChildCount(); i++) {
											copy.appendChild(start.getChild(i));
										}
										container.appendChild(copy);
										DOMUtil.cleanBranch(splittingPoint);
										DOMUtil.cleanBranch(copy);
									}
								});
							}
						} else {
							Node copy = child.cloneNode(false);
							container.appendChild(copy);
							nextSibling = child.getNextSibling();
							final Node splittingPoint = child;
							Scheduler.get().scheduleFinally(new ScheduledCommand() {
								@Override
								public void execute() {
									ancestor.insertAfter(container, splittingPoint);
								}
							});

							extractStart(child, copy, range);
						}
						child = nextSibling;
					} else if (started && range.containsNode(child, true)/*
																																 * child.
																																 * isOrHasChild
																																 * (endNode)
																																 */) {
						finished = true;
						if (child.equals(endNode)) {
							if (child.getNodeType() == Node.TEXT_NODE) {
								String texte = endNode.getNodeValue();
								String inner = texte.substring(0, range.getEndOffset());

								final String after = texte.substring(range.getEndOffset());
								final Node copy = document.createTextNode(inner);
								final Node splittingPoint = child;
								Scheduler.get().scheduleFinally(new ScheduledCommand() {
									@Override
									public void execute() {
										container.appendChild(copy);
										splittingPoint.setNodeValue(after);
										DOMUtil.cleanBranch(splittingPoint);
									}
								});
							} else {
								// Dans ce cas, on recopie jusqu'à l'offset
								final Node splittingPoint = child;
								final Node end = endNode;
								final int offset = range.getEndOffset();
								Scheduler.get().scheduleFinally(new ScheduledCommand() {
									@Override
									public void execute() {
										Node copy = splittingPoint.cloneNode(false);
										for (int i = 0; i < offset; i++) {
											copy.appendChild(end.getChild(i));
										}
										container.appendChild(copy);
										DOMUtil.cleanBranch(splittingPoint);
										DOMUtil.cleanBranch(copy);
									}
								});
							}

						} else {
							Node copy = child.cloneNode(false);
							container.appendChild(copy);
							extractStop(child, copy, range);
						}
					} else {
						child = child.getNextSibling();
					}
				}
			}
		}
		Scheduler.get().scheduleFinally(new ScheduledCommand() {
			@Override
			public void execute() {
				range.selectNode(container);
				selection.getAssociatedSurface().setSelection(range);
				action.doAction(as(container), selection);
			}
		});
	}

	/**
	 * Indique si la sélection courante est assigné
	 * 
	 * @param selection
	 *          Sélection courante
	 * @return Vrai si la sélection est assignée, faux si elle ne l'est pas (donc
	 *         assignable)
	 */
	@Override
	public boolean isSelectionAssignee(SurfaceSelection selection) {
		Element ancestor = getCommonMatchingAncestor(selection);
		if (ancestor != null) {
			return adjustSelectionAssignee(ancestor, selection);
		}
		return false;
	}

	protected Element getCommonMatchingAncestor(SurfaceSelection selection) {
		Element ancestor = (Element) selection.getSelection().getRange().getCommonAncestorContainer();
		return DOMUtil.getFirstAncestorOfType(ancestor, action.getEmptyElement().getTagName());
	}

	/**
	 * Permet de redresser si besoin le prédicat indiquant que l'élément
	 * sélectionné est du type inséré
	 * 
	 * @param matchingAncestor
	 *          Ancêtre commun dont le type correspond au type manipulé par cet
	 *          Inserter
	 * @return Vrai si l'élément est bien assigné, faut sinon (donc assignable)
	 */
	@Override
	protected boolean adjustSelectionAssignee(Element matchingAncestor, SurfaceSelection selection) {
		return true;
	}

	protected void extractStart(final Node from, final Node to, Range range) {
		Node startNode = range.getStartContainer();
		int startOffset = range.getStartOffset();
		Node nextSibling, child = from.getFirstChild();
		// if (from.getNodeType() == Node.TEXT_NODE && to.getNodeType() == Node.TEXT_NODE
		// && from.getNodeValue().equals(to.getNodeValue())) {
		// // Si from est un noeud texte, donc une feuille, on l'enlève simplement de son parent car une
		// // copie de lui a été effectée
		// from.removeFromParent();
		// return;
		// }
		boolean started = false;
		while (child != null) {
			if (!started) {
				if (range.containsNode(child, true)/* child.isOrHasChild(startNode) */) {
					started = true;
					if (startNode.equals(child)) {
						// c'est la dernière feuille
						String texte = startNode.getNodeValue();
						final String before = texte.substring(0, startOffset);
						String inner = texte.substring(startOffset);

						nextSibling = child.getNextSibling();
						final Node innerNode = from.getOwnerDocument().createTextNode(inner);

						final Node splittingPoint = child;
						Scheduler.get().scheduleFinally(new ScheduledCommand() {
							@Override
							public void execute() {
								to.appendChild(innerNode);
								if (!before.isEmpty()) {
									splittingPoint.setNodeValue(before);
								} else {
									splittingPoint.removeFromParent();
								}
							}
						});

						child = nextSibling;
					} else {
						// Sinon, c'est juste une branche sur laquelle on est assis : il faut la recopier
						Node newChild = child.cloneNode(false);
						to.appendChild(newChild);
						extractStart(child, newChild, range);
					}
				} else {
					child = child.getNextSibling();
				}
			} else {
				nextSibling = child.getNextSibling();
				final Node splittingPoint = child;
				Scheduler.get().scheduleFinally(new ScheduledCommand() {
					@Override
					public void execute() {
						to.appendChild(splittingPoint);
					}
				});
				child = nextSibling;
			}
		}
		Scheduler.get().scheduleFinally(new ScheduledCommand() {
			@Override
			public void execute() {
				DOMUtil.cleanBranch(from);
			}
		});
	}

	protected void extractStop(final Node from, final Node to, Range range) {
		Node endNode = range.getEndContainer();
		int endOffset = range.getEndOffset();
		Node nextSibling, child = from.getFirstChild();
		// if (from.getNodeType() == Node.TEXT_NODE) {
		// // Si from est un noeud texte, donc une feuille, on l'enlève simplement de son parent car une
		// // copie de lui a été effectée
		// from.removeFromParent();
		// return;
		// }
		boolean finished = false;
		while (child != null && !finished) {
			if (!range.containsNode(child, false) /* child.isOrHasChild(endNode) */) {
				finished = true;
				if (endNode.equals(child)) {
					// c'est la dernière feuille
					String texte = endNode.getNodeValue();
					String inner = texte.substring(0, endOffset);
					final String after = texte.substring(endOffset);

					nextSibling = child.getNextSibling();
					final Node innerNode = from.getOwnerDocument().createTextNode(inner);

					final Node splittingPoint = child;
					Scheduler.get().scheduleFinally(new ScheduledCommand() {
						@Override
						public void execute() {
							to.appendChild(innerNode);
							if (!after.isEmpty()) {
								splittingPoint.setNodeValue(after);
							} else {
								splittingPoint.removeFromParent();
							}
						}
					});

					child = nextSibling;
				} else {
					// Sinon, c'est juste une branche sur laquelle on est assis : il faut la recopier
					Node newChild = child.cloneNode(false);
					to.appendChild(newChild);
					extractStop(child, newChild, range);
				}
			} else {
				nextSibling = child.getNextSibling();
				final Node splittingPoint = child;
				Scheduler.get().scheduleFinally(new ScheduledCommand() {
					@Override
					public void execute() {
						to.appendChild(splittingPoint);
					}
				});
				child = nextSibling;
			}
		}
		Scheduler.get().scheduleFinally(new ScheduledCommand() {
			@Override
			public void execute() {
				DOMUtil.cleanBranch(from);
			}
		});
	}

	protected abstract E as(Element element);
}
