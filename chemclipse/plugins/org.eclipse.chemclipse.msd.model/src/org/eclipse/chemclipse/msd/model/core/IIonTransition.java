/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.msd.model.core;

import java.io.Serializable;

public interface IIonTransition extends Serializable {

	double getQ1StartIon();

	double getQ1StopIon();

	int getQ1Ion(); // Q1 - precision 0

	double getDeltaQ1Ion();

	double getQ3StartIon();

	double getQ3StopIon();

	double getQ3Ion(); // Q3 - precision 1

	double getDeltaQ3Ion();

	double getCollisionEnergy();

	int getTransitionGroup();

	double getQ1Resolution();

	double getQ3Resolution();
}