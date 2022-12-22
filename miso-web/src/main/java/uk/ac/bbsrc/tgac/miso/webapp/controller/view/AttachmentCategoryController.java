package uk.ac.bbsrc.tgac.miso.webapp.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.bbsrc.tgac.miso.core.data.impl.AttachmentCategory;
import uk.ac.bbsrc.tgac.miso.core.security.AuthorizationManager;
import uk.ac.bbsrc.tgac.miso.core.service.AttachmentCategoryService;
import uk.ac.bbsrc.tgac.miso.core.service.ProviderService;
import uk.ac.bbsrc.tgac.miso.dto.AttachmentCategoryDto;
import uk.ac.bbsrc.tgac.miso.dto.Dtos;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/attachmentcategories")
public class AttachmentCategoryController extends AbstractTypeDataController<AttachmentCategory, AttachmentCategoryDto> {

  @Autowired
  private AttachmentCategoryService attachmentCategoryService;

  @Autowired
  private AuthorizationManager authorizationManager;

  public AttachmentCategoryController() {
    super("Attachment Categories", "attachmentcategory", "attachmentcategory", true);
  }

  @GetMapping("/list")
  public ModelAndView list(ModelMap model) throws IOException {
    return listStatic(attachmentCategoryService.list(), model);
  }

  @GetMapping("/bulk/new")
  public ModelAndView create(@RequestParam("quantity") Integer quantity, ModelMap model) throws IOException {
    return bulkCreate(quantity, model);
  }

  @PostMapping("/bulk/edit")
  public ModelAndView edit(@RequestParam Map<String, String> formData, ModelMap model) throws IOException {
    return bulkEdit(formData, model);
  }

  @Override
  protected AuthorizationManager getAuthorizationManager() {
    return authorizationManager;
  }

  @Override
  protected ProviderService<AttachmentCategory> getService() {
    return attachmentCategoryService;
  }

  @Override
  protected AttachmentCategoryDto toDto(AttachmentCategory object) {
    return Dtos.asDto(object);
  }

  @Override
  protected AttachmentCategoryDto makeDto() {
    return new AttachmentCategoryDto();
  }

}
