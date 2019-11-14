/*******************************************************************************
 * Copyright (c) 2014, 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - update to reflect changes in INoiseCalculator API
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.xxd.calculator.supplier.noise.dyson.core;

import org.eclipse.chemclipse.chromatogram.xxd.calculator.supplier.noise.dyson.TestPathHelper;

public class NoiseCalculator_2_ITest extends ChromatogramReaderTestCase {

	private static final int SEGMENT_WIDTH = 13;
	private NoiseCalculator noiseCalculator;

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_CHROMATOGRAM_2);
		super.setUp();
		noiseCalculator = new NoiseCalculator();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testReader_1() {

		/*
		 * The loading time of the chromatogram takes a while.
		 * That's why several tests are made here.
		 */
		assertEquals(0.0f, noiseCalculator.getSignalToNoiseRatio(chromatogram, SEGMENT_WIDTH, 0));
		assertEquals(0.3802281369f, noiseCalculator.getSignalToNoiseRatio(chromatogram, SEGMENT_WIDTH, 50));
		assertEquals(1.0f, noiseCalculator.getSignalToNoiseRatio(chromatogram, SEGMENT_WIDTH, 131.5f));
		assertEquals(608.3650190114f, noiseCalculator.getSignalToNoiseRatio(chromatogram, SEGMENT_WIDTH, 80000));
	}
}
