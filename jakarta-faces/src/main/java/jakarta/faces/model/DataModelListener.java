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

package javax.faces.model;

import java.util.EventListener;

/**
 * <p>
 * <strong>DataModelListener</strong> represents an event listener that wishes to be notified of {@link DataModelEvent}s
 * occurring on a particular {@link DataModel} instance.
 * </p>
 */

public interface DataModelListener extends EventListener {

    /**
     * <p>
     * Notification that a particular row index, with the associated row data, has been selected for processing.
     * </p>
     *
     * @param event The {@link DataModelEvent} we are processing
     */
    void rowSelected(DataModelEvent event);

}