/**
 * 
 */
package net.zeng.jxsite.module.product.web.admin;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.product.model.Product;
import net.zeng.jxsite.module.product.service.ProductService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLView;

/**
 * @author zeng.xiaoning
 * @deprecated
 */
public class ProductAction extends MultipleAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ProductService productService;

	private String catalog;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ProductAction() {
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
				e.addElement("id").addText(p.getId() + "");
				e.addElement("name").addText(p.getName() + "");
				e.addElement("manager").addText(p.getManager() + "");
				e.addElement("status").addText(p.getStatus() + "");
				e.addElement("images").addText(p.getImages() + "");

				e.addElement("catalog").addText(p.getCatalog().getName() + "");

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
		}

		return new XMLView(doc);
	}

	public AbstractView doEdit() throws MVCException {
		return null;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getCatalog() {
		return catalog;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
}
