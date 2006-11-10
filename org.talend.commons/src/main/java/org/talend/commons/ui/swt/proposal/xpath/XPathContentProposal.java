// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.commons.ui.swt.proposal.xpath;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

/**
 * Content proposal based on a IContextParameter. <br/>
 * 
 * $Id: EntryContentProposal.java 1 2006-09-29 17:06:40 +0000 (ven., 29 sept. 2006) nrousseau $
 * 
 */
public class XPathContentProposal implements IContentProposal {

    private String content;
    private Node node;

    /**
     * Constructs a new ContextParameterProposal.
     * @param node 
     * 
     * @param language
     * @param control
     */
    public XPathContentProposal(Node node) {
        super();
        this.node = node;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IContentProposal#getContent()
     */
    public String getContent() {
        if(this.node instanceof Attr) {
            content = "/@" + this.node.getNodeName();
        } else {
            content = "/" + this.node.getNodeName();
        }
        return content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IContentProposal#getCursorPosition()
     */
    public int getCursorPosition() {
        return content.length() + 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IContentProposal#getDescription()
     */
    public String getDescription() {

        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    public String format(Object object) {
        if (object == null) {
            return "";
        }
        return String.valueOf(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IContentProposal#getLabel()
     */
    public String getLabel() {
        String label = null;
        if(this.node instanceof Attr) {
            label = "/@" + this.node.getNodeName();
        } else {
            label = "/" + this.node.getNodeName();
        }
        return label;
    }

}
