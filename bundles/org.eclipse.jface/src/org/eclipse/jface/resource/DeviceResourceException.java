/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.resource;


/**
 * @since 3.1
 */
public class DeviceResourceException extends Exception {
    
	/**
	 * All serializable objects should have a stable serialVersionUID
	 */
	private static final long serialVersionUID = 11454598756198L;
    
	/**
	 * Creates a DeviceResourceException indicating an error attempting to
	 * create a resource and an embedded low-level exception describing the cause 
	 * 
	 * @param missingResource
     * @param cause cause of the exception (or null if none)
	 */
    public DeviceResourceException(DeviceResourceDescriptor missingResource, Throwable cause) {
        super("Unable to create resource " + missingResource.toString(), cause); //$NON-NLS-1$
    }
    
    /**
     * Creates a DeviceResourceException indicating an error attempting to
     * create a resource 
     * 
     * @param missingResource
     */
    public DeviceResourceException(DeviceResourceDescriptor missingResource) {
        this(missingResource, null);
    }
}
