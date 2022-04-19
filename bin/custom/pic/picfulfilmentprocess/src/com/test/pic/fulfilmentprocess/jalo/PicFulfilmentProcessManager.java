/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.test.pic.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.test.pic.fulfilmentprocess.constants.PicFulfilmentProcessConstants;

public class PicFulfilmentProcessManager extends GeneratedPicFulfilmentProcessManager
{
	public static final PicFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (PicFulfilmentProcessManager) em.getExtension(PicFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
