package com.test.pic.core.services;

import de.hybris.platform.core.model.media.MediaModel;

import java.io.InputStream;
import java.util.List;

public interface IImageExportService {

    List<String> readProductCodes(InputStream file);

    byte[] getProductImages(List<String> productCodes);

    MediaModel createOrReplaceMedia(String fileName, byte[] data);
}
