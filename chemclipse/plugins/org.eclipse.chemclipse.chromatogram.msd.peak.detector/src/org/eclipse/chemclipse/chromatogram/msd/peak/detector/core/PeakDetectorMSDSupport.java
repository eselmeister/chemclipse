/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jan Holy - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.peak.detector.core;

import org.eclipse.chemclipse.chromatogram.peak.detector.core.AbstractPeakDetectorSupport;
import org.eclipse.chemclipse.chromatogram.peak.detector.exceptions.NoPeakDetectorAvailableException;

public class PeakDetectorMSDSupport extends AbstractPeakDetectorSupport<IPeakDetectorMSDSupplier> implements IPeakDetectorMSDSupport {

	public PeakDetectorMSDSupport() {
		super();
	}

	@Override
	public IPeakDetectorMSDSupplier getPeakDetectorSupplier(String peakDetectorId) throws NoPeakDetectorAvailableException {

		return getSupplier(peakDetectorId);
	}
}
