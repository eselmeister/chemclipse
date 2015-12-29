/*******************************************************************************
 * Copyright (c) 2012, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.process.exceptions;

public class ProcessingException extends Exception {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 6588977957850747386L;

	public ProcessingException() {
		super();
	}

	public ProcessingException(String message) {
		super(message);
	}

	public ProcessingException(Throwable throwable) {
		super(throwable);
	}
}
