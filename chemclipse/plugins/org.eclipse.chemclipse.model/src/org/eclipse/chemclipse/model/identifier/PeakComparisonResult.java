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
package org.eclipse.chemclipse.model.identifier;

public class PeakComparisonResult extends AbstractPeakComparisonResult implements IPeakComparisonResult {

	public PeakComparisonResult(float matchQuality, float reverseMatchQuality) {
		super(matchQuality, reverseMatchQuality);
	}

	public PeakComparisonResult(float matchFactor, float reverseMatchFactor, float probability) {
		super(matchFactor, reverseMatchFactor, probability);
	}
}
