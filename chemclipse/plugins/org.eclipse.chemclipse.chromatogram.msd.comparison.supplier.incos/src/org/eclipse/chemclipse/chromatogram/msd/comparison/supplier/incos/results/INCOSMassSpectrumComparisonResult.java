/*******************************************************************************
 * Copyright (c) 2008, 2016 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.comparison.supplier.incos.results;

import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.AbstractMassSpectrumComparisonResult;

public class INCOSMassSpectrumComparisonResult extends AbstractMassSpectrumComparisonResult {

	/**
	 * Renew the UUID on change.
	 */
	private static final long serialVersionUID = 8980174287452222536L;

	public INCOSMassSpectrumComparisonResult(float matchFactor, float reverseMatchFactor) {
		super(matchFactor, reverseMatchFactor);
	}
}
