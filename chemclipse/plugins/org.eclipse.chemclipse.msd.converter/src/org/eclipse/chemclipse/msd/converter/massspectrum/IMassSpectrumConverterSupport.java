/*******************************************************************************
 * Copyright (c) 2008, 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - Adjust to new API
 *******************************************************************************/
package org.eclipse.chemclipse.msd.converter.massspectrum;

import org.eclipse.chemclipse.converter.core.IConverterSupport;
import org.eclipse.chemclipse.processing.DataCategory;

/**
 * @author eselmeister
 */
public interface IMassSpectrumConverterSupport extends IConverterSupport {

	@Override
	default String getName() {

		return "Mass Spectrum";
	}

	@Override
	default DataCategory getDataCategory() {

		return DataCategory.MSD;
	}
}
