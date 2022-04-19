/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.test.pic.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.test.pic.core.constants.PicCoreConstants;
import com.test.pic.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class PicCoreManager extends GeneratedPicCoreManager
{
	public static final PicCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (PicCoreManager) em.getExtension(PicCoreConstants.EXTENSIONNAME);
	}
}
