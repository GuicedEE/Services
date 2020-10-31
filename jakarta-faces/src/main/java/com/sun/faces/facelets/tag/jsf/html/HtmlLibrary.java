/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.facelets.tag.jsf.html;

/**
 * @author Jacob Hookom
 */
public final class HtmlLibrary extends AbstractHtmlLibrary {

    public final static String Namespace = "http://java.sun.com/jsf/html";
    public final static String XMLNSNamespace = "http://xmlns.jcp.org/jsf/html";

    public final static HtmlLibrary Instance = new HtmlLibrary();

    public HtmlLibrary() {
        this(Namespace);
    }

    public HtmlLibrary(String namespace) {
        super(namespace);

        addHtmlComponent("body", "javax.faces.OutputBody", "javax.faces.Body");

        addHtmlComponent("button", "javax.faces.HtmlOutcomeTargetButton", "javax.faces.Button");

        addHtmlComponent("column", "javax.faces.Column", null);

        addHtmlComponent("commandButton", "javax.faces.HtmlCommandButton", "javax.faces.Button");

        addHtmlComponent("commandLink", "javax.faces.HtmlCommandLink", "javax.faces.Link");

        addHtmlComponent("commandScript", "javax.faces.HtmlCommandScript", "javax.faces.Script");

        addHtmlComponent("dataTable", "javax.faces.HtmlDataTable", "javax.faces.Table");

        addHtmlComponent("form", "javax.faces.HtmlForm", "javax.faces.Form");

        addHtmlComponent("graphicImage", "javax.faces.HtmlGraphicImage", "javax.faces.Image");

        addHtmlComponent("head", "javax.faces.Output", "javax.faces.Head");

        addHtmlComponent("html", "javax.faces.Output", "javax.faces.Html");

        addHtmlComponent("doctype", "javax.faces.Output", "javax.faces.Doctype");

        addHtmlComponent("inputFile", "javax.faces.HtmlInputFile", "javax.faces.File");

        addHtmlComponent("inputHidden", "javax.faces.HtmlInputHidden", "javax.faces.Hidden");

        addHtmlComponent("inputSecret", "javax.faces.HtmlInputSecret", "javax.faces.Secret");

        addHtmlComponent("inputText", "javax.faces.HtmlInputText", "javax.faces.Text");

        addHtmlComponent("inputTextarea", "javax.faces.HtmlInputTextarea", "javax.faces.Textarea");

        addHtmlComponent("link", "javax.faces.HtmlOutcomeTargetLink", "javax.faces.Link");

        addHtmlComponent("message", "javax.faces.HtmlMessage", "javax.faces.Message");

        addHtmlComponent("messages", "javax.faces.HtmlMessages", "javax.faces.Messages");

        addHtmlComponent("outputFormat", "javax.faces.HtmlOutputFormat", "javax.faces.Format");

        addHtmlComponent("outputLabel", "javax.faces.HtmlOutputLabel", "javax.faces.Label");

        addHtmlComponent("outputLink", "javax.faces.HtmlOutputLink", "javax.faces.Link");

        addHtmlComponent("outputText", "javax.faces.HtmlOutputText", "javax.faces.Text");

        this.addComponent("outputScript", "javax.faces.Output", "javax.faces.resource.Script", ScriptResourceHandler.class);

        this.addComponent("outputStylesheet", "javax.faces.Output", "javax.faces.resource.Stylesheet", StylesheetResourceHandler.class);

        addHtmlComponent("panelGrid", "javax.faces.HtmlPanelGrid", "javax.faces.Grid");

        addHtmlComponent("panelGroup", "javax.faces.HtmlPanelGroup", "javax.faces.Group");

        addHtmlComponent("selectBooleanCheckbox", "javax.faces.HtmlSelectBooleanCheckbox", "javax.faces.Checkbox");

        addHtmlComponent("selectManyCheckbox", "javax.faces.HtmlSelectManyCheckbox", "javax.faces.Checkbox");

        addHtmlComponent("selectManyListbox", "javax.faces.HtmlSelectManyListbox", "javax.faces.Listbox");

        addHtmlComponent("selectManyMenu", "javax.faces.HtmlSelectManyMenu", "javax.faces.Menu");

        addHtmlComponent("selectOneListbox", "javax.faces.HtmlSelectOneListbox", "javax.faces.Listbox");

        addHtmlComponent("selectOneMenu", "javax.faces.HtmlSelectOneMenu", "javax.faces.Menu");

        addHtmlComponent("selectOneRadio", "javax.faces.HtmlSelectOneRadio", "javax.faces.Radio");

        addHtmlComponent("title", "javax.faces.Output", "javax.faces.Title");
    }

}
