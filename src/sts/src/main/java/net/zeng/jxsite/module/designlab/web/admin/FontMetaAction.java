/**
 * 
 */
package net.zeng.jxsite.module.designlab.web.admin;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.designlab.model.FontMeta;
import net.zeng.jxsite.module.designlab.DesignLabConstants;
import net.zeng.jxsite.module.designlab.service.FontMetaService;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.DateUtil;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;
import net.zeng.mvc.MVCException;

/**
 * @author snzeng
 * @since 6.0
 */
public class FontMetaAction extends MultipleAction {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ConfigurationService ConfigurationService;
	private FontMetaService fontMetaService;

	private String status;
	private String size;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public FontMetaAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		List<?> allFonts = this.fontMetaService.getAll();
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		if (allFonts != null && allFonts.size() > 0) {
			for (Object obj : allFonts) {
				FontMeta font = (FontMeta) obj;
				if (font != null) {
					Element ele = root.addElement("font");
					ele.addElement("id").addText(font.getId() + "");
					ele.addElement("name").addText(font.getName() + "");
					ele.addElement("file").addText(font.getFile() + "");
					ele.addElement("image").addText(font.getId() + "/" + font.getImage() + "");
					ele.addElement("status").addText(font.getStatus() + "");
					ele.addElement("description").addText(font.getDescription() + "");
					ele.addElement("updatedTime").addText(
							DateUtil.format(font.getUpdatedTime(), DateUtil.FULL_CN) + "");
				}
			}
		}
		return new XMLView(doc);
	}

	public AbstractView doUpdate() throws MVCException {
		if (StringUtil.notBlank(getId())) {
			String[] idArr = getId().split(",");
			if (StringUtil.notBlank(getStatus())) {
				for (String id : idArr) {
					FontMeta font = this.fontMetaService.getById(id);
					if (font != null) {
						if ("on".equals(getStatus())) {
							font.setStatus(FontMeta.OPEN);
						} else if ("off".equals(getStatus())) {
							font.setStatus(FontMeta.CLOSED);
						} else {
							continue;
						}
						this.fontMetaService.save(font);
					}
				}
			}
		}
		return XMLView.SUCCESS;
	}

	public AbstractView doScan() throws MVCException {
		String fontsDirectory = this.ConfigurationService.getSiteUpload()
				+ DesignLabConstants.FONTS_DIR;
		File fontDir = new File(fontsDirectory);
		this.fontMetaService.scanFontDirectory(fontDir);
		return XMLView.SUCCESS;
	}

	public AbstractView doSnapshot() throws MVCException {
		String fontsDirectory = this.ConfigurationService.getSiteUpload()
				+ DesignLabConstants.FONTS_DIR;
		if (StringUtil.notBlank(getId())) {
			String[] idArr = getId().split(",");
			String size = StringUtil.isBlank(getSize()) ? "240*48" : getSize();
			int width = NumberUtil.string2Integer(size.split("\\*")[0], 240);
			int height = NumberUtil.string2Integer(size.split("\\*")[1], 48);
			for (String id : idArr) {
				FontMeta font = this.fontMetaService.getById(id);
				font.setDirectory(fontsDirectory + "/" + id);
				if (font != null) {
					this.fontMetaService.createSnapshot(font, new int[] { width, height });
				}
			}
		}
		return XMLView.SUCCESS;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public FontMetaService getFontMetaService() {
		return fontMetaService;
	}

	public void setFontMetaService(FontMetaService fontMetaService) {
		this.fontMetaService = fontMetaService;
	}

	public ConfigurationService getConfigurationService() {
		return ConfigurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		ConfigurationService = configurationService;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
