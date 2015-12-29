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
package org.eclipse.chemclipse.database.model;

import junit.framework.TestCase;

public class AbstractDatabaseProxy_2_Test extends TestCase {

	private String database2 = "local:C:\\tmp\\.chemclipse\\0.8.0\\org.eclipse.chemclipse.chromatogram.msd.quantitation.supplier.chemclipse\\DefaultDB";

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testGetDatabaseName_2() {

		MyProxy myProxy = new MyProxy(database2, database2);
		assertEquals(database2, myProxy.getDatabaseName());
	}

	public void testGetDatabaseUrl_2() {

		MyProxy myProxy = new MyProxy(database2, database2);
		assertEquals(database2, myProxy.getDatabaseUrl());
	}

	public class MyProxy extends DatabaseProxy {

		public MyProxy(String databaseUrl, String databaseName) {
			super(databaseUrl, databaseName);
		}
	}
}
