/**
 * 
 */
package net.zeng.jxsite.module.product.web.admin;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.product.model.Catalog;
import net.zeng.jxsite.module.product.model.Product;
import net.zeng.jxsite.module.product.service.ProductService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class ManageAction extends MultipleAction {

	private static final long serialVersionUID = -3059682758844688405L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ProductService productService;
	private String catalog;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ManageAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

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
				Element e = root.addElement("product");
				addProductObjectToXmlElement(e, p);
			}
		}

		return new XMLView(doc);
	}

	private String getImage(String imageName) {
		return StringUtil.isBlank(imageName) ? "noimage.gif" : imageName;
	}

	public AbstractView doLoad() throws MVCException {
		if (StringUtil.isBlank(getId())) {
			return new XMLErrorView("name", "No ID found!");
		}
		Product product = getProductService().getById(getId());
		if (product == null) {
			return new XMLErrorView("name", "No product found!");
		}
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Element ele = root.addElement("Product");
		addProductObjectToXmlElement(ele, product);
		return new XMLView(doc);
	}

	private void addProductObjectToXmlElement(Element e, Product p) {
		e.addElement("id").addText(p.getId() + "");
		e.addElement("name").addText(p.getName() + "");
		e.addElement("manager").addText(p.getManager() + "");
		e.addElement("status").addText(p.getStatus() + "");
		e.addElement("images").addText(p.getImages() + "");

		e.addElement("imageSingle").addText(getImage(p.getImageSingle()));
		e.addElement("imageFront").addText(getImage(p.getImageFront()));
		e.addElement("imageBack").addText(getImage(p.getImageBack()));
		e.addElement("imageLeft").addText(getImage(p.getImageLeft()));
		e.addElement("imageRight").addText(getImage(p.getImageRight()));
		e.addElement("imageAbove").addText(getImage(p.getImageAbove()));
		e.addElement("imageUnder").addText(getImage(p.getImageUnder()));
		e.addElement("attachment").addText(p.getAttachment() + "");

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
		e.addElement("createdTime").addText(p.getCreatedTime() + "");
		e.addElement("updatedTime").addText(p.getUpdatedTime() + "");
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

}
