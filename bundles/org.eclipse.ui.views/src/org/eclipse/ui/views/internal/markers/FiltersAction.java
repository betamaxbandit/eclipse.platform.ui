/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.views.internal.markers;

import org.eclipse.jface.action.Action;


/**
 * This action opens a Filters Dialog and notifies the Marker View if the user has
 * modified the filter via the filters dialog.
 */
class FiltersAction extends Action {
	
	private MarkerView view;
	private FiltersDialog dialog;
	
	/**
	 * Creates the action
	 */
	public FiltersAction(MarkerView view, FiltersDialog dialog) {
		super(Messages.getString("filtersAction.title")); //$NON-NLS-1$
		setImageDescriptor(ImageFactory.getImageDescriptor("clcl16/filter_ps.gif")); //$NON-NLS-1$
		this.view = view;
		this.dialog = dialog;
		setEnabled(true);
	}
	
	/**
	 * Opens the dialog. Notifies the view if the filter has been modified.
	 */
	public void run() {
		if (dialog == null) {
			return;
		}
		if (dialog.open() == FiltersDialog.OK && dialog.isDirty()) {
			view.filtersChanged();
		}
	}

	
}
