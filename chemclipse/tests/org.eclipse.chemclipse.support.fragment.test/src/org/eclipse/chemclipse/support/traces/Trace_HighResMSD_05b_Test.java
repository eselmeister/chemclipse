/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.support.traces;

public class Trace_HighResMSD_05b_Test extends TraceTestCase {

	private TraceHighResMSD trace;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		trace = TraceFactory.parseTrace("400.01627±50ppm", TraceHighResMSD.class);
	}

	public void testMZ() {

		assertEquals(400.01627d, trace.getMZ());
	}

	public void testDelta() {

		assertEquals(0.02d, trace.getDelta(), 0.01d);
	}

	public void testUseRange() {

		assertTrue(trace.isUseRange());
	}

	public void testStartMZ() {

		assertEquals(399.9962691865d, trace.getStartMZ(), 0.0000000001d);
	}

	public void testStopMZ() {

		assertEquals(400.0362708135d, trace.getStopMZ(), 0.0000000001d);
	}

	public void testScaleFactor() {

		assertEquals(1.0d, trace.getScaleFactor());
	}

	public void testString() {

		assertEquals("400.01627±50ppm", trace.toString());
	}
}