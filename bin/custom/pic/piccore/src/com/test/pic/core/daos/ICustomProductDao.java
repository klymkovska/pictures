package com.test.pic.core.daos;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.product.daos.ProductDao;

import java.util.List;

public interface ICustomProductDao extends ProductDao {

    List<MediaModel> findPictures(List<String> productCodes);
}
