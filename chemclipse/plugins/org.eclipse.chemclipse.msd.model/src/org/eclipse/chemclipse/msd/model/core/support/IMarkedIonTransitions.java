/*******************************************************************************
 * Copyright (c) 2013, 2018 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.msd.model.core.support;

import java.util.Set;

import org.eclipse.chemclipse.msd.model.core.IIonTransition;

public interface IMarkedIonTransitions {

	Set<IMarkedIonTransition> getAll();

	Set<IIonTransition> getSelectedIonTransitions();
}
