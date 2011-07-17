/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;
import org.apache.commons.beanutils.BeanUtils;

import net.zeng.jxsite.module.system.model.Configuration;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.GenericAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class ConfigAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ConfigurationService configurationService;

	private String key;
	private String group;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ConfigAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MVCException {
		if (StringUtil.isBlank(getOpt())) {
			return super.doService();
		} else if (getOpt().equals("save")) {
			return addConfig();
		} else { // if (getOpt().equals("list")) {
			return listConfigs();
		}
	}

	private AbstractView addConfig() throws MVCException {
		Configuration config = new Configuration();
		if (StringUtil.notBlank(getId())) { // load data
			config = this.configurationService.getById(getId());
			if (getField().equals("desc")) {
				config.setDescription(getValue());
			}
			try {
				BeanUtils.setProperty(config, getField(), getValue());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			config.setName(getName());
			config.setKey(getKey());
			config.setValue(getValue());
			config.setGroup(getGroup());
		}
		this.configurationService.save(config);

		return XMLView.SUCCESS;
	}

	private AbstractView listConfigs() throws MVCException {
		// Check parameters
		int start = getStartInt();
		int limit = getLimitInt();
		String query = getQ();
		// Query total
		int totalCount = this.configurationService.getTotlaCount(query);
		// Return XML
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		root.addElement("totalCount").addText(totalCount + "");
		if (totalCount > 0) {
			List<?> configs = this.configurationService.search(query, group, start, limit);
			for (Object obj : configs) {
				Element cfgEle = root.addElement("Config");
				Configuration config = (Configuration) obj;
				cfgEle.addElement("id").addText(config.getId() + "");
				cfgEle.addElement("name").addText(config.getName() + "");
				cfgEle.addElement("key").addText(config.getKey() + "");
				cfgEle.addElement("value").addText(config.getValue() + "");
				cfgEle.addElement("group").addText(config.getGroup() + "");
				cfgEle.addElement("desc").addText(config.getDescription() + "");
			}
		}
		return new XMLView(doc);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
