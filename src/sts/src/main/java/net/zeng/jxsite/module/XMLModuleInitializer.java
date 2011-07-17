/**
 * 
 */
package net.zeng.jxsite.module;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.BlockSetting;
import net.zeng.jxsite.module.system.model.Configuration;
import net.zeng.jxsite.module.system.model.Menu;
import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.util.BooleanUtil;
import net.zeng.util.IDUtil;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class XMLModuleInitializer implements ModuleInitializer {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	protected Logger logger = Logger.getLogger(getClass());

	private String configPath;
	private Module module;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public XMLModuleInitializer() {
	}

	public XMLModuleInitializer(Module module, String configPath) {
		this.module = module;
		this.configPath = configPath;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void init() throws InitializationException {
		logger.info("Initialize module from XML: " + this.configPath);
		// Parse XML
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");
		try {
			Document doc = reader.read(new File(this.configPath));
			Element root = doc.getRootElement();

			// ID
			String id = root.elementText("id");
			this.module.setId(id);

			// Name
			String name = root.elementText("name");
			if (StringUtil.notBlank(name)) {
				this.module.setName(name);
			} else {
				throw new InitializationException("Empty name!");
			}
			// Index
			String index = root.elementText("index");
			this.module.setIndex(NumberUtil.string2Integer(index, 0));

			// Admin Menu
			Element menusEle = root.element("menus");
			for (Object obj : menusEle.elements()) {
				Element menuEle = (Element) obj;
				String menuId = menuEle.attributeValue("id");
				String menuType = menuEle.attributeValue("type");
				if (StringUtil.isBlank(menuId)) {
					menuId = this.module.getId() + "-" + menuType;
				} else if (!menuId.startsWith(this.module.getId())) {
					menuId = this.module.getId() + "-" + menuId;
				}
				Menu menu = new Menu();
				menu.setId(menuId);
				menu.setType(menuType);
				menu.setModule(module);
				// menu.setParent(menu);
				parseMenu(menu, menuEle);
				module.addMenu(menu);
			}

			// Icons
			Element iconsEle = root.element("icons");
			parseIcons(iconsEle);

			// Configurtion
			Element configs = root.element("configs");
			parseConfigurations(configs);

			// Blocks
			Element blocks = root.element("blocks");
			parseBlocks(blocks);

			// Page
			Element pages = root.element("pages");
			parsePages(pages);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new InitializationException(e);
		}

		// After initialized, the module is ready to work online.
		this.module.setStatus(Module.READY);
	}

	private void parsePages(Element pages) throws InitializationException {
		if (pages == null) {
			return;
		}
		List<?> elements = pages.elements();
		for (Object obj : elements) {
			Element ele = (Element) obj;
			Page page = new Page();

			// ID
			String id = ele.elementText("id");
			if (StringUtil.isBlank(id)) {
				id = ele.attributeValue("id");
			}
			if (StringUtil.notBlank(id)) {
				page.setId(id);
			} else {
				throw new InitializationException("Page ID not found!");
			}

			// Name
			String name = ele.elementText("name");
			if (StringUtil.notBlank(name)) {
				page.setName(name);
			} else {
				page.setName("Unknown!");
			}

			// Url
			String url = ele.elementText("url");
			if (StringUtil.notBlank(url)) {
				page.setUrl(url);
			} else {
				page.setUrl("/" + id.replace("-", "/") + ".htm");
			}

			// Default Block
			String blockId = ele.elementText("defaultBlock");
			// if (blockId.indexOf(".") < 0) {
			// blockId = this.module.getId() + "." + blockId;
			// }
			BlockPrototype defaultBlock = new BlockPrototype(blockId);
			page.setDefaultBlock(defaultBlock);

			// Description
			String description = ele.elementText("description");
			page.setDescription(description);

			// Other ...

			// Add page
			page.setModule(this.module);
			this.module.addPage(page);
		}
	}

	private void parseMenu(Menu menu, Element menuEle) throws InitializationException {
		List<?> elements = menuEle.elements();
		for (Object obj : elements) {
			Element ele = (Element) obj;
			String nodeName = ele.getName();
			Menu item = new Menu();
			// ID
			String itemId = ele.elementText("id");
			if (StringUtil.notBlank(itemId)) {
				item.setId(itemId);
			} else {
				logger.warn("No ID found! use default instead.");
				item.setIcon(IDUtil.generate());
			}
			// item.setId(IDUtil.generate());
			// Name
			String itemName = ele.elementText("name");
			if (StringUtil.isBlank(itemName)) {
				itemName = ele.attributeValue("name");
			}
			if (StringUtil.notBlank(itemName)) {
				item.setName(itemName);
			} else {
				throw new InitializationException("Empty item name!");
			}
			if ("item".equals(nodeName)) { // Simple Item
				// Parent
				item.setParent(menu);
				// Type
				item.setType(Menu.LEAF);
				// Index
				String itemIndex = ele.elementText("index");
				item.setIndex(NumberUtil.string2Integer(itemIndex, 0));
				// Icon
				String itemIcon = ele.elementText("icon");
				if (StringUtil.notBlank("icon")) {
					item.setIcon(itemIcon);
				} else {
					item.setIcon("blank.gif"); // TODO
				}
				// URL
				String itemURL = ele.elementText("href");
				item.setUrl(itemURL); // Allow blank
				// Add to menu
				menu.addItem(item);
			} else if ("menu".equals(nodeName)) { // Submenu
				// Type
				item.setType(Menu.INNER);
				// Parent
				item.setParent(menu);
				// Recursion
				parseMenu(item, ele);
				// Add to menu
				menu.addItem(item);
			} else {
				// Ignore ...
			}
		}
	}

	private void parseIcons(Element iconsEle) throws InitializationException {
		if (iconsEle == null) {
			return; // No icons
		}
		List<?> elements = iconsEle.elements();
		for (Object obj : elements) {
			Element ele = (Element) obj;
			String id = ele.attributeValue("id");
			if (StringUtil.isBlank(id)) {
				throw new InitializationException("Null ID of icon!");
			}
			String path = ele.getTextTrim();
			this.module.addIcon(id, path);
		}
	}

	private void parseConfigurations(Element configs) throws InitializationException {
		if (configs == null) {
			return; // No configurations
		}
		List<?> elements = configs.elements();
		for (Object obj : elements) {
			Element ele = (Element) obj;
			// Key
			String key = ele.elementTextTrim("key");

			if (StringUtil.isBlank(key)) {
				throw new InitializationException("Null key of configuration!");
			} // ~ key should not be null

			if (!key.startsWith(this.module.getId() + ".")) {
				key = this.module.getId() + "." + key;
			} // ~ key should start with module id

			// Name
			String name = ele.elementTextTrim("name");
			// Value
			String value = ele.elementTextTrim("value");
			// Description
			String desc = ele.elementTextTrim("description");

			Configuration config = new Configuration();
			config.setKey(key);
			config.setName(name);
			config.setValue(value);
			config.setDescription(desc);
			config.setGroup(this.module.getId());
			this.module.addConfiguration(config);
		}
	}

	private void parseBlocks(Element blocks) throws InitializationException {
		if (blocks == null) {
			return;
		}
		List<?> elements = blocks.elements();
		for (Object obj : elements) {
			Element ele = (Element) obj;
			BlockPrototype prototype = new BlockPrototype();
			// ID
			String id = ele.attributeValue("id");
			if (StringUtil.isBlank(id)) {
				id = ele.elementText("id");
			}
			if (StringUtil.notBlank(id)) {
				if (id.startsWith(this.module.getId() + ".")) {
					prototype.setId(id);
				} else {
					prototype.setId(this.module.getId() + "." + id);
				}
			} else {
				throw new InitializationException("Block prototype ID not found!");
			}
			// Name
			String name = ele.elementText("name");
			if (StringUtil.notBlank(name)) {
				prototype.setName(name);
			} else {
				throw new InitializationException("Block prototype name not found!");
			}
			// Type
			String type = ele.elementText("type");
			if (StringUtil.notBlank(type)) {
				prototype.setType(type);
			} else {
				prototype.setType(BlockPrototype.STATIC);
			}
			// Singleton
			String singleton = ele.elementText("singleton");
			prototype.setSingleton(BooleanUtil.string2Boolean(singleton));
			// Template
			String template = ele.elementText("template");
			if (StringUtil.notBlank(template)) {
				prototype.setTemplate(template);
			} else {
				throw new InitializationException("Block prototype name not found!");
			}
			// Settings
			Element settingsEle = ele.element("settings");
			if (settingsEle != null) {
				List<?> settings = settingsEle.elements();
				for (Object o : settings) {
					Element e = (Element) o;
					BlockSetting bs = new BlockSetting();
					// Key
					String sKey = e.elementText("key");
					if (StringUtil.notBlank(sKey)) {
						bs.setKey(sKey);
					} else {
						throw new InitializationException("Block setting key not found!");
					}
					// Name
					String sName = e.elementText("name");
					if (StringUtil.notBlank(sName)) {
						bs.setName(sName);
					} else {
						throw new InitializationException("Block setting name not found!");
					}
					// Value
					String sValue = e.elementText("value");
					if (StringUtil.notBlank(sValue)) {
						bs.setValue(sValue);
					}
					// Usage
					String sUsage = e.elementText("usage");
					if (StringUtil.notBlank(sUsage)) {
						bs.setUsage(sUsage);
					}
					// Add to prototype
					prototype.addSetting(bs);
				}
			}
			// Add to module
			this.module.addBlockPrototype(prototype);
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
