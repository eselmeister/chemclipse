/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.model.columns;

import junit.framework.TestCase;

public class SeparationColumnFactory_2_Test extends TestCase {

	public void test1() {

		assertEquals(4, SeparationColumnFactory.getSeparationColumns().size());
	}
}