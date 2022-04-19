package com.test.pic.core.services.impl;

import com.test.pic.core.daos.ICustomProductDao;
import com.test.pic.core.services.IImageExportService;
import de.hybris.platform.catalog.model.CatalogUnawareMediaModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.CSVReader;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.test.pic.core.constants.PicCoreConstants.ENCODING;

public class ImageExportService implements IImageExportService {

    private static final int BUFFER_SIZE = 2048;
    private static final Logger LOG = LoggerFactory.getLogger(ImageExportService.class);

    @Resource
    private ICustomProductDao productDao;
    @Resource
    private MediaService mediaService;
    @Resource
    private ModelService modelService;

    @Override
    public List<String> readProductCodes(InputStream file) {
        List<String> productCodes = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(file, ENCODING));
            while (reader.readNextLine()) {
                Map<Integer, String> line = reader.getLine();
                productCodes.add(line.get(0));
            }
            reader.close();
        } catch (IOException e) {
            LOG.error("Error occurred while parcing CSV file", e);
        }
        return productCodes;
    }

    @Override
    public byte[] getProductImages(List<String> productCodes) {
        List<MediaModel> medias = productDao.findPictures(productCodes);
        if (CollectionUtils.isNotEmpty(medias)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(out);
            medias.forEach(media -> {
                try {
                    InputStream inputStream = mediaService.getStreamFromMedia(media);
                    byte buffer[] = new byte[BUFFER_SIZE];
                    zos.putNextEntry(new ZipEntry(media.getCode()));
                    int length;
                    while ((length = inputStream.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                    zos.close();
                } catch (IOException e) {
                    LOG.error("Error occurred while compressing images", e);
                }
            });
            return out.toByteArray();
        }
        return  null;
    }

    @Override
    public MediaModel createOrReplaceMedia(String fileName, byte[] data) {
        MediaModel oldMedia = mediaService.getMedia(fileName);
        if (oldMedia != null) {
            modelService.remove(oldMedia);
        }
        return createMedia(fileName, data);
    }

    private MediaModel createMedia(String fileName, byte[] data) {
        final CatalogUnawareMediaModel media = modelService.create(CatalogUnawareMediaModel.class);
        media.setCode(fileName);
        media.setRealFileName(fileName);
        modelService.save(media);
        mediaService.setStreamForMedia(media, new ByteArrayInputStream(data));
        return media;
    }
}
