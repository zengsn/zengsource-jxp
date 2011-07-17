/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.User;
import net.zeng.jxsite.module.system.service.UserInfoService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.DateUtil;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class UserAction extends MultipleAction {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String username;
	private String email;
	private String enabled;

	private UserInfoService userInfoService;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public UserAction() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Integer totalCount = this.userInfoService.searchCount(getQ());
		root.addElement("totalCount").addText(totalCount + "");
		if (totalCount > 0) {
			List<?> users = this.userInfoService.search(getQ(), getStartInt(), getLimitInt());
			for (Object obj : users) {
				if (obj != null) {
					UserInfo userInfo = (UserInfo) obj;
					Element ele = root.addElement("user");
					ele.addElement("id").addText(userInfo.getId() + "");
					ele.addElement("username").addText(userInfo.getUser().getUsername() + "");
					ele.addElement("status").addText(userInfo.getStatus() + "");
					ele.addElement("lastLoginTime").addText(
							userInfo.getLastLoginTime() == null ? "" : DateUtil.format(userInfo
									.getLastLoginTime(), "yyyy-MM-dd HH:mm:SS"));
				}
			}
		}
		return new XMLView(doc);
	}

	public AbstractView doLoad() throws MVCException {
		UserInfo userInfo = this.userInfoService.getById(getId());
		if (userInfo == null) {
			return new XMLErrorView("username", "用户不存在!");
		}
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Element ele = root.addElement("user");
		ele.addElement("id").addText(userInfo.getId() + "");
		ele.addElement("username").addText(userInfo.getUser().getUsername() + "");
		ele.addElement("email").addText(userInfo.getEmail() + "");
		ele.addElement("enabled").addText(userInfo.getUser().getEnabled() + "");
		return new XMLView(doc);
	}

	public AbstractView doSave() throws MVCException {
		String oneOrMore = getId();
		if (StringUtil.isBlank(oneOrMore)) { // Create an userInfo
			UserInfo userInfo = new UserInfo();
			// Security User
			User identity = new User();
			identity.setUsername(getUsername());
			identity.setPassword(this.userInfoService.generateDefaultPassword());
			identity.setEnabled(NumberUtil.string2Integer(getEnabled(), 1));// status
			userInfo.setUser(identity);
			// userInfo Info
			userInfo.setEmail(getEmail());
			// Save
			this.userInfoService.save(userInfo);
			// Logging
			logger.info("Create new user <> " + getUsername());
			// Send Email with Password
			this.userInfoService.mailPassword(userInfo);
			// Logging
			logger.info("Create new user <> " + getUsername() + " ... password SENT!");
		} else if (oneOrMore.indexOf(',') == -1) { // Edit single userInfo
			UserInfo userInfo = this.userInfoService.getById(oneOrMore);
			if (userInfo == null) {
				return new XMLErrorView("username", "用户不存在!");
			}
			// User status
			userInfo.getUser().setEnabled(NumberUtil.string2Integer(getEnabled(), 1));
			// userInfo Info
			userInfo.setEmail(getEmail());
			// Save
			this.userInfoService.save(userInfo);
			// Logging
			logger.info("Update user <> " + getUsername() + "," + getEnabled() + "," + getEmail());
		} else { // Edit multiple userInfos
			String[] idArray = oneOrMore.split(",");
			for (String anId : idArray) {
				if (StringUtil.notBlank(anId)) {
					UserInfo userInfo = this.userInfoService.getById(oneOrMore);
					if (userInfo == null) {
						// Ignored
					} else { // only for status
						userInfo.getUser().setEnabled(
								NumberUtil.string2Integer(getEnabled(), 1));
						// Save
						this.userInfoService.save(userInfo);
						// Logging
						logger.info("Change status of user <> "
								+ userInfo.getUser().getUsername() + " to " + getEnabled());
					}
				}
			}
		}
		return XMLView.SUCCESS;
	}

	public AbstractView doDelete() throws MVCException {
		String oneOrMore = getId();
		if (StringUtil.isBlank(oneOrMore)) {
			return new XMLErrorView("username", "请选择至少一个用户进行操作!");
		}
		String[] idArray = oneOrMore.split(",");
		for (String anId : idArray) {
			if (StringUtil.notBlank(anId)) {
				UserInfo userInfo = this.userInfoService.getById(anId);
				if (userInfo != null) {
					this.userInfoService.disable(userInfo);
					// Logging
					logger.info("Disable an userInfo <> " + userInfo.getUser());
				}
			}
		}
		return XMLView.SUCCESS;
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

}
