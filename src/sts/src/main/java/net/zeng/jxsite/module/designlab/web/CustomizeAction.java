/**
 * 
 */
package net.zeng.jxsite.module.designlab.web;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.designlab.DesignLabConstants;
import net.zeng.jxsite.module.designlab.model.Design;
import net.zeng.jxsite.module.designlab.model.FontMeta;
import net.zeng.jxsite.module.designlab.model.GalleryImage;
import net.zeng.jxsite.module.designlab.model.ImageItem;
import net.zeng.jxsite.module.designlab.model.TextItem;
import net.zeng.jxsite.module.designlab.service.DesignService;
import net.zeng.jxsite.module.designlab.service.FontMetaService;
import net.zeng.jxsite.module.designlab.service.GalleryImageService;
import net.zeng.jxsite.module.product.model.Catalog;
import net.zeng.jxsite.module.product.model.Product;
import net.zeng.jxsite.module.product.service.ProductService;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.JSONUtil;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class CustomizeAction extends MultipleAction {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;
	static final String FRONT = "front";
	static final String BACK = "back";

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String name;
	private String email;

	private String catalog;

	// Style customization

	/** {id:04600,x:625.5,y:206,w:264,h:312} */
	private String style;

	// Art customization

	/** [{id:20090121133930169,x:725.5,y:286,w:60,h:37}] */
	private String front;
	/** [{id:20090121133930169,x:725.5,y:286,w:60,h:37}] */
	private String back;

	// Text customization

	private String text;
	private String type; // FRONT or BACK
	private String textFront;
	private String textBack;

	private String textFont;
	private String textSize;
	private String textColor;

	// Service

	private DesignService designService;
	private ProductService productService;
	private FontMetaService fontMetaService;
	private GalleryImageService galleryImageService;
	private ConfigurationService configurationService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public CustomizeAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doLoad() throws MVCException {
		Design design = this.designService.getById(getId());
		if (design != null) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("response");
			Element ele = root.addElement("design");
			ele.addElement("id").addText(design.getId() + "");
			ele.addElement("name").addText(design.getName() + "");
			StringBuilder sbFront = new StringBuilder();
			StringBuilder sbBack = new StringBuilder();
			for (ImageItem item : design.getImageItems()) {
				if (item.isFront()) {
					sbFront.append(item.toString()).append(",");
				} else {
					sbBack.append(item.toString()).append(",");
				}
			}
			if (sbFront.toString().endsWith(",")) {
				sbFront.deleteCharAt(sbFront.length() - 1);
			}
			if (sbBack.toString().endsWith(",")) {
				sbBack.deleteCharAt(sbBack.length() - 1);
			}
			ele.addElement("front").addText("[" + sbFront.toString() + "]");
			ele.addElement("back").addText("[" + sbBack.toString() + "]");
			return new XMLView(doc);
		}
		return XMLView.SUCCESS;
	}

	public AbstractView doSave() throws MVCException {
		Design design = new Design();
		// Name
		if (StringUtil.notBlank(this.name)) {
			design.setName(this.name);
		} else {
			return new XMLErrorView("name", "Name cannot be blank!");
		}
		// Email
		design.setEmail(this.email);
		// Style
		logger.info("Parsing style: " + this.style);
		Map<String, Object> stylePropertyMap = JSONUtil.parseObject(this.style);
		Product style = this.productService.getById((String) stylePropertyMap.get("id"));
		if (style == null) {
			return new XMLErrorView("name", "Style Unavailable!");
		}
		design.setStyle(style);
		// Front Arts
		logger.info("Parsing front arts: " + this.front);
		List<Map<String, Object>> frontArts = JSONUtil.parseArray(this.front);
		if (frontArts != null) {
			int z = 0;
			for (Map<String, Object> art : frontArts) {
				GalleryImage image = this.galleryImageService.getById((String) art.get("id"));
				if (image == null) {
					return new XMLErrorView("name", "Image Unavailable!");
				}
				ImageItem item = new ImageItem();
				item.setImage(image);
				item.setName(z + "-" + image.getName());
				item.setX(NumberUtil.string2Float((String) art.get("x"), 0.0f));
				item.setY(NumberUtil.string2Float((String) art.get("y"), 0.0f));
				item.setZ(z++); // increase z-index
				item.setWidth(NumberUtil.string2Integer((String) art.get("w"), 0));
				item.setHeight(NumberUtil.string2Integer((String) art.get("h"), 0));
				item.setFront(true);
				design.addImage(item);
			}
		}
		// Back Arts
		logger.info("Parsing back arts: " + this.back);
		List<Map<String, Object>> backArts = JSONUtil.parseArray(this.back);
		if (backArts != null) {
			int z = 0;
			for (Map<String, Object> art : backArts) {
				GalleryImage image = this.galleryImageService.getById((String) art.get("id"));
				if (image == null) {
					return new XMLErrorView("name", "Image Unavailable!");
				}
				ImageItem item = new ImageItem();
				item.setImage(image);
				item.setName(z + "-" + image.getName());
				item.setX(NumberUtil.string2Float((String) art.get("x"), 0.0f));
				item.setY(NumberUtil.string2Float((String) art.get("y"), 0.0f));
				item.setZ(z++); // increase z-index
				item.setWidth(NumberUtil.string2Integer((String) art.get("w"), 0));
				item.setHeight(NumberUtil.string2Integer((String) art.get("h"), 0));
				item.setFront(false);
				design.addImage(item);
			}
		}

		// Designer
		design.setDesigner("testing");
		// Save Design
		if (designService.save(design)) {
			return XMLView.SUCCESS;
		} else {
			throw new RuntimeException();
		}
	}

	public AbstractView doList() throws MVCException {
		Integer totalCount = getProductService().getCount(getQ(), getCatalog());
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("reponse").addAttribute("success", "true");

		if (totalCount <= 0) {
			root.addElement("totalCount").addText(0 + "");
		} else {
			root.addElement("totalCount").addText(totalCount + "");
			List<?> products = getProductService().search(getQ(), getCatalog(), getStartInt(),
					getLimitInt());
			for (Object obj : products) {
				Product p = (Product) obj;
				Element e = root.addElement("style");
				addProductObjectToXmlElement(e, p);
			}
		}

		return new XMLView(doc);
	}

	private void addProductObjectToXmlElement(Element e, Product p) {
		e.addElement("id").addText(p.getId() + "");
		e.addElement("name").addText(p.getName() + "");
		// e.addElement("manager").addText(p.getManager() + "");
		// e.addElement("status").addText(p.getStatus() + "");
		// e.addElement("images").addText(p.getImages() + "");

		// e.addElement("imageSingle").addText(getImage(p.getImageSingle()));
		e.addElement("imageFront").addText(getImage(p.getImageFront()));
		e.addElement("imageBack").addText(getImage(p.getImageBack()));
		// e.addElement("imageLeft").addText(getImage(p.getImageLeft()));
		// e.addElement("imageRight").addText(getImage(p.getImageRight()));
		// e.addElement("imageAbove").addText(getImage(p.getImageAbove()));
		// e.addElement("imageUnder").addText(getImage(p.getImageUnder()));
		// e.addElement("attachment").addText(p.getAttachment() + "");

		Catalog catalog = p.getCatalog();
		if (catalog == null) {
			e.addElement("catalog").addText(Catalog.DEFAULT);
		} else {
			e.addElement("catalog").addText(p.getCatalog() + "");
		}

		e.addElement("price").addText(p.getPrice() + "");
		e.addElement("currency").addText(p.getCurrency() + "");
		e.addElement("material").addText(p.getMaterial() + "");
		e.addElement("shipping").addText(p.getShipping() + "");
		e.addElement("usage").addText(p.getUsage() + "");

		e.addElement("viewCount").addText(p.getViewCount() + "");
		e.addElement("soldCount").addText(p.getSoldCount() + "");
		e.addElement("totalCount").addText(p.getTotalCount() + "");
		e.addElement("orderCount").addText(p.getOrderCount() + "");

		e.addElement("description").addText(p.getDescription() + "");
		// e.addElement("createdTime").addText(p.getCreatedTime() + "");
		// e.addElement("updatedTime").addText(p.getUpdatedTime() + "");
	}

	private String getImage(String imageName) {
		return StringUtil.isBlank(imageName) ? "noimage.gif" : imageName;
	}

	public AbstractView doGetFonts() throws MVCException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		List<?> fonts = this.fontMetaService.getAll();
		if (fonts != null && fonts.size() > 0) {
			root.addElement("totalCount").addText(fonts.size() + "");
			for (Object obj : fonts) {
				if (obj != null) {
					FontMeta font = (FontMeta) obj;
					Element ele = root.addElement("font");
					ele.addElement("id").addText(font.getId() + "");
					ele.addElement("name").addText(font.getName() + "");
					ele.addElement("image").addText(font.getId() + "/" + font.getImage());
				}
			}
		}
		return new XMLView(doc);
	}

	public AbstractView doAddText() throws MVCException {
		TextItem textItem = new TextItem();
		textItem.setId(getId());

		if (StringUtil.notBlank(getText())) {
			textItem.setName(getText());
			// front or back
			if (BACK.equals(getType())) {
				textItem.setFront(false);
			} else {
				textItem.setFront(true);
			}
			// Font
			if (StringUtil.notBlank(getFront())) {
				FontMeta font = this.fontMetaService.getById(getFront());
				if (font != null) {
					textItem.setFont(font);
				}
			}
			// Size
			if (StringUtil.notBlank(getTextSize())) {
				textItem.setTextSize(NumberUtil.string2Integer(getTextSize(),
						FontMeta.DEFAULT_SIZE));
			}
			// Style
			// Shape
			// Outline
			// Rotate

			// build image
			String tmp = this.configurationService.getSiteTmp() + DesignLabConstants.USERS_DIR + "/test";
			this.designService.createTextImage(tmp, textItem);
			
			Document doc = DocumentHelper.createDocument();
			return new XMLView(doc);
		} else {
			return new XMLErrorView("text", "Text cannot be null!");
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getFront() {
		return front;
	}

	public void setFront(String front) {
		this.front = front;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTextFront() {
		return textFront;
	}

	public void setTextFront(String textFront) {
		this.textFront = textFront;
	}

	public String getTextBack() {
		return textBack;
	}

	public void setTextBack(String textBack) {
		this.textBack = textBack;
	}

	public String getTextFont() {
		return textFont;
	}

	public void setTextFont(String textFont) {
		this.textFont = textFont;
	}

	public String getTextSize() {
		return textSize;
	}

	public void setTextSize(String textSize) {
		this.textSize = textSize;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public DesignService getDesignService() {
		return designService;
	}

	public void setDesignService(DesignService designService) {
		this.designService = designService;
	}

	public GalleryImageService getGalleryImageService() {
		return galleryImageService;
	}

	public void setGalleryImageService(GalleryImageService galleryImageService) {
		this.galleryImageService = galleryImageService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public FontMetaService getFontMetaService() {
		return fontMetaService;
	}

	public void setFontMetaService(FontMetaService fontMetaService) {
		this.fontMetaService = fontMetaService;
	}

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

}
