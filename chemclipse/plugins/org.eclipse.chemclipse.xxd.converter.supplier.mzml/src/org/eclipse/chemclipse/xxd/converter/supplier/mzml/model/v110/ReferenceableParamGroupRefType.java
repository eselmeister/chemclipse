/*******************************************************************************
 * Copyright (c) 2015, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceableParamGroupRefType")
public class ReferenceableParamGroupRefType {

	@XmlAttribute(name = "ref", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	private ReferenceableParamGroupType ref;

	public ReferenceableParamGroupType getRef() {

		return ref;
	}

	public void setRef(ReferenceableParamGroupType value) {

		this.ref = value;
	}
}