/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.msd.model.core.comparator;

import java.util.Comparator;

import org.eclipse.chemclipse.model.comparator.SortOrder;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;

public class ChromatogramPeakMSDComparator implements Comparator<IChromatogramPeakMSD> {

	private SortOrder sortOrder;

	public ChromatogramPeakMSDComparator() {
		sortOrder = SortOrder.ASC;
	}

	public ChromatogramPeakMSDComparator(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int compare(IChromatogramPeakMSD peak1, IChromatogramPeakMSD peak2) {

		int retentionTime1 = peak1.getPeakModel().getRetentionTimeAtPeakMaximum();
		int retentionTime2 = peak2.getPeakModel().getRetentionTimeAtPeakMaximum();
		//
		int returnValue;
		switch(sortOrder) {
			case ASC:
				returnValue = Integer.compare(retentionTime1, retentionTime2);
				break;
			case DESC:
				returnValue = Integer.compare(retentionTime2, retentionTime1);
				break;
			default:
				returnValue = Integer.compare(retentionTime1, retentionTime2);
				break;
		}
		return returnValue;
	}
}
