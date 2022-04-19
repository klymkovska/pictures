package com.test.pic.backoffice.actions;

import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.editor.defaultfileupload.FileUploadResult;
import com.test.pic.core.model.SearchPicturesCronJobModel;
import com.test.pic.core.services.IImageExportService;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import java.util.Map;

import static org.picbackoffice.constants.YacceleratorbackofficeConstants.FILE_UPLOAD_PROPERTY;

public class UploadFileAction implements CockpitAction<SearchPicturesCronJobModel, Object> {
    
    @Resource
    private IImageExportService imageExportService;
    @Resource
    private ModelService modelService;

    @Override
    public ActionResult<Object> perform(ActionContext<SearchPicturesCronJobModel> ctx) {
        final Map map = (Map) ctx.getParameter(ActionContext.PARENT_WIDGET_MODEL);
        if (map != null) {
            final FileUploadResult result = (FileUploadResult) map.get(FILE_UPLOAD_PROPERTY);
            MediaModel mediaFile = imageExportService.createOrReplaceMedia(result.getName(), result.getData());
            ctx.getData().setProductFile(mediaFile);
            modelService.save(ctx.getData());
        }

        return new ActionResult<>(ActionResult.SUCCESS);
    }

    @Override
    public boolean canPerform(ActionContext<SearchPicturesCronJobModel> ctx) {
        return CockpitAction.super.canPerform(ctx);
    }

    @Override
    public boolean needsConfirmation(ActionContext<SearchPicturesCronJobModel> ctx) {
        return CockpitAction.super.needsConfirmation(ctx);
    }

    @Override
    public String getConfirmationMessage(ActionContext<SearchPicturesCronJobModel> ctx) {
        return CockpitAction.super.getConfirmationMessage(ctx);
    }
}
