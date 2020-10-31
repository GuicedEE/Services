/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
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

package com.sun.faces.taglib.html_basic;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.validator.MethodExpressionValidator;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

/*
 * ******* GENERATED CODE - DO NOT EDIT *******
 */

public class InputHiddenTag extends UIComponentELTag {

    // Setter Methods
    // PROPERTY: converter
    private javax.el.ValueExpression converter;

    public void setConverter(javax.el.ValueExpression converter) {
        this.converter = converter;
    }

    // PROPERTY: converterMessage
    private javax.el.ValueExpression converterMessage;

    public void setConverterMessage(javax.el.ValueExpression converterMessage) {
        this.converterMessage = converterMessage;
    }

    // PROPERTY: immediate
    private javax.el.ValueExpression immediate;

    public void setImmediate(javax.el.ValueExpression immediate) {
        this.immediate = immediate;
    }

    // PROPERTY: required
    private javax.el.ValueExpression required;

    public void setRequired(javax.el.ValueExpression required) {
        this.required = required;
    }

    // PROPERTY: requiredMessage
    private javax.el.ValueExpression requiredMessage;

    public void setRequiredMessage(javax.el.ValueExpression requiredMessage) {
        this.requiredMessage = requiredMessage;
    }

    // PROPERTY: validator
    private javax.el.MethodExpression validator;

    public void setValidator(javax.el.MethodExpression validator) {
        this.validator = validator;
    }

    // PROPERTY: validatorMessage
    private javax.el.ValueExpression validatorMessage;

    public void setValidatorMessage(javax.el.ValueExpression validatorMessage) {
        this.validatorMessage = validatorMessage;
    }

    // PROPERTY: value
    private javax.el.ValueExpression value;

    public void setValue(javax.el.ValueExpression value) {
        this.value = value;
    }

    // PROPERTY: valueChangeListener
    private javax.el.MethodExpression valueChangeListener;

    public void setValueChangeListener(javax.el.MethodExpression valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    // General Methods
    @Override
    public String getRendererType() {
        return "javax.faces.Hidden";
    }

    @Override
    public String getComponentType() {
        return "javax.faces.HtmlInputHidden";
    }

    @Override
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        javax.faces.component.UIInput input = null;
        try {
            input = (javax.faces.component.UIInput) component;
        } catch (ClassCastException cce) {
            throw new IllegalStateException(
                    "Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIInput.  Perhaps you're missing a tag?");
        }

        if (converter != null) {
            if (!converter.isLiteralText()) {
                input.setValueExpression("converter", converter);
            } else {
                Converter conv = FacesContext.getCurrentInstance().getApplication().createConverter(converter.getExpressionString());
                input.setConverter(conv);
            }
        }

        if (converterMessage != null) {
            input.setValueExpression("converterMessage", converterMessage);
        }
        if (immediate != null) {
            input.setValueExpression("immediate", immediate);
        }
        if (required != null) {
            input.setValueExpression("required", required);
        }
        if (requiredMessage != null) {
            input.setValueExpression("requiredMessage", requiredMessage);
        }
        if (validator != null) {
            input.addValidator(new MethodExpressionValidator(validator));
        }
        if (validatorMessage != null) {
            input.setValueExpression("validatorMessage", validatorMessage);
        }
        if (value != null) {
            input.setValueExpression("value", value);
        }
        if (valueChangeListener != null) {
            input.addValueChangeListener(new MethodExpressionValueChangeListener(valueChangeListener));
        }
    }

    // Methods From TagSupport
    @Override
    public int doStartTag() throws JspException {
        try {
            return super.doStartTag();
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            throw new JspException(root);
        }
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            return super.doEndTag();
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            throw new JspException(root);
        }
    }

    // RELEASE
    @Override
    public void release() {
        super.release();

        // component properties
        converter = null;
        converterMessage = null;
        immediate = null;
        required = null;
        requiredMessage = null;
        validator = null;
        validatorMessage = null;
        value = null;
        valueChangeListener = null;

        // rendered attributes
    }

    public String getDebugString() {
        return "id: " + getId() + " class: " + this.getClass().getName();
    }

}
