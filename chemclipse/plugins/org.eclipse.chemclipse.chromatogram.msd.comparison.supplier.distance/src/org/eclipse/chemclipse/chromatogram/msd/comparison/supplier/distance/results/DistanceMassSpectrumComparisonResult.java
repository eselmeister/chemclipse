/*******************************************************************************
 * Copyright (c) 2014, 2016 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig. - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.comparison.supplier.distance.results;

import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.AbstractMassSpectrumComparisonResult;

public class DistanceMassSpectrumComparisonResult extends AbstractMassSpectrumComparisonResult {

	public DistanceMassSpectrumComparisonResult(float matchFactor, float reverseMatchFactor) {
		super(matchFactor, reverseMatchFactor);
	}
}
