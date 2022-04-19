package com.test.pic.core.job;

import com.test.pic.core.model.SearchPicturesCronJobModel;
import com.test.pic.core.services.IImageExportService;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

import static com.test.pic.core.constants.PicCoreConstants.ARCHIVE_NAME;

public class SearchPicturesJob extends AbstractJobPerformable<SearchPicturesCronJobModel> {

    @Resource
    private IImageExportService imageExportService;
    @Resource
    private MediaService mediaService;
    @Resource
    private ModelService modelService;

    @Override
    public PerformResult perform(SearchPicturesCronJobModel searchPicturesJobModel) {
        MediaModel media = searchPicturesJobModel.getProductFile();
        InputStream productCodesFile = mediaService.getStreamFromMedia(media);
        List<String> productCodes = imageExportService.readProductCodes(productCodesFile);
        byte[] zipBytes = imageExportService.getProductImages(productCodes);
        if (zipBytes != null) {
            MediaModel exportMedia = imageExportService.createOrReplaceMedia(ARCHIVE_NAME, zipBytes);
            searchPicturesJobModel.setImages(exportMedia);
            modelService.save(searchPicturesJobModel);
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }
}
