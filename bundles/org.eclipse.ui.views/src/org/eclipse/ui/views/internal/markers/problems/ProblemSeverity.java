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

package org.eclipse.ui.views.internal.markers.problems;

import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.internal.markers.ImageFactory;
import org.eclipse.ui.views.internal.markers.IField;


class ProblemSeverity implements IField {
	
	private static final String IMAGE_ERROR_PATH = "obj16/error_tsk.gif"; //$NON-NLS-1$
	private static final String IMAGE_WARNING_PATH = "obj16/warn_tsk.gif"; //$NON-NLS-1$
	private static final String IMAGE_INFO_PATH = "obj16/info_tsk.gif"; //$NON-NLS-1$
	
	private String name;
	private String description;
	private Image image;
	
	public ProblemSeverity() {
		name = ProblemViewConstants.PROBLEM_SEVERITY;
		description = Messages.getString(name + ".description"); //$NON-NLS-1$
		image = null;
	}

	/**
	 * @see org.eclipse.ui.views.markerview.IField#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see org.eclipse.ui.views.markerview.IField#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @see org.eclipse.ui.views.markerview.IField#getDescriptionImage()
	 */
	public Image getDescriptionImage() {
		return image;
	}

	/**
	 * @see org.eclipse.ui.views.markerview.IField#getColumnHeaderText()
	 */
	public String getColumnHeaderText() {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.ui.views.markerview.IField#getColumnHeaderImage()
	 */
	public Image getColumnHeaderImage() {
		return null;
	}

	/**
	 * @see org.eclipse.ui.views.markerview.IField#getValue(java.lang.Object)
	 */
	public String getValue(Object obj) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.ui.views.markerview.IField#getImage(java.lang.Object)
	 */
	public Image getImage(Object obj) {
		if (obj == null || !(obj instanceof IMarker)) {
			return null;
		}
		IMarker marker = (IMarker) obj;
		int severity = marker.getAttribute(IMarker.SEVERITY, -1);
		if (severity == IMarker.SEVERITY_ERROR) {
			return ImageFactory.getImage(IMAGE_ERROR_PATH);
		}
		if (severity == IMarker.SEVERITY_WARNING) {
			return ImageFactory.getImage(IMAGE_WARNING_PATH);
		}
		if (severity == IMarker.SEVERITY_INFO) {
			return ImageFactory.getImage(IMAGE_INFO_PATH);
		}
		return null;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (!(other instanceof IField)) {
			return false;
		}
		IField otherProperty = (IField) other;
		return (this.name.equals(otherProperty.getName()));
	}

	/**
	 * @see org.eclipse.ui.views.markerview.IField#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null || !(obj1 instanceof IMarker) || !(obj2 instanceof IMarker)) {
			return 0;
		}
		IMarker marker1 = (IMarker) obj1;
		IMarker marker2 = (IMarker) obj2;
		int severity1 = marker1.getAttribute(IMarker.SEVERITY, -1);
		int severity2 = marker2.getAttribute(IMarker.SEVERITY, -1);
		return severity1 - severity2;
	}

}