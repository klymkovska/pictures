package com.test.pic.core.daos.impl;

import com.test.pic.core.daos.ICustomProductDao;
import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.daos.impl.DefaultProductDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomProductDao extends DefaultProductDao implements ICustomProductDao {

    @Resource
    private CatalogService catalogService;

    public CustomProductDao(String typecode) {
        super(typecode);
    }

    public List<MediaModel> findPictures(List<String> productCodes) {
        final Map<String, Object> params = new HashMap<>();
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT {m:").append(MediaModel.PK).append("} ");
        stringBuilder.append("FROM {").append(ProductModel._TYPECODE).append(" AS p ");
        stringBuilder.append("JOIN ").append(MediaModel._TYPECODE).append(" AS m ");
        stringBuilder.append("ON {p:").append(ProductModel.PICTURE).append("}={m:").append(MediaModel.PK).append("} } ");
        stringBuilder.append("WHERE {p:").append(ProductModel.CODE).append("} in (?codes) ");
        stringBuilder.append("AND {p:").append(ProductModel.CATALOGVERSION).append("}=?cv ");

        params.put("codes", productCodes);
        params.put("cv", catalogService.getDefaultCatalog().getActiveCatalogVersion());
        final FlexibleSearchQuery query = new FlexibleSearchQuery(stringBuilder.toString());
        query.addQueryParameters(params);

        SearchResult<MediaModel> searchResult = getFlexibleSearchService().search(query);
        return searchResult.getResult();
    }
}
