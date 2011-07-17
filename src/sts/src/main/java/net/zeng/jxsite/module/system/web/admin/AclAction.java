/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.AclClass;
import net.zeng.jxsite.module.system.model.security.AclEntry;
import net.zeng.jxsite.module.system.model.security.AclObject;
import net.zeng.jxsite.module.system.model.security.Role;
import net.zeng.jxsite.module.system.model.security.RoleLinkUser;
import net.zeng.jxsite.module.system.service.AclClassService;
import net.zeng.jxsite.module.system.service.AclEntryService;
import net.zeng.jxsite.module.system.service.AclObjectService;
import net.zeng.jxsite.module.system.service.RoleService;
import net.zeng.jxsite.module.system.service.UserInfoService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.DateUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class AclAction extends MultipleAction {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String description;

	private String uid;
	private String username;

	private RoleService roleService;
	private UserInfoService userInfoService;
	private AclClassService aclClassService;
	private AclObjectService aclObjectService;
	private AclEntryService aclEntryService;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclAction() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doListRoles() throws MVCException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		int totalCount = this.roleService.searchCount(getQ());
		if (totalCount == 0) {
			root.addElement("totalCount").addText("0");
		} else {
			List<?> roles = this.roleService.search(getQ(), getStartInt(), getLimitInt());
			for (Object obj : roles) {
				Role role = (Role) obj;
				Element ele = root.addElement("role");
				ele.addElement("id").addText(role.getId() + "");
				ele.addElement("name").addText(role.getName() + "");
				ele.addElement("description").addText(role.getDescription() + "");
				ele.addElement("isWrapper").addText(role.isWrapper() + "");
				// users
				ele.addElement("users").addText(role.getUserCount() + "");
				// permissions
				ele.addElement("updatedTime").addText(
						role.getUpdatedTime() == null ? "" : DateUtil.format(
								role.getUpdatedTime(), DateUtil.FULL_CN2));
			}
		}
		return new XMLView(doc);
	}

	public AbstractView doListClasses() throws MVCException {
		Element root = XMLView.getXmlResponseRoot();
		int totalCount = this.aclClassService.searchCount(getQ());
		if (totalCount == 0) {
			root.addElement("totalCount").addText("0");
		} else {
			List<?> aclClasses = this.aclClassService.search(getQ(), getStartInt(), getLimitInt());
			for (Object obj : aclClasses) {
				AclClass aclClass = (AclClass) obj;
				Element ele = root.addElement("class");
				ele.addElement("id").addText(aclClass.getId() + "");
				ele.addElement("name").addText(aclClass.getName() + "");
				ele.addElement("description").addText(aclClass.getDescription() + "");
				ele.addElement("objectCount").addText(aclClass.getObjectCount() + "");
				ele.addElement("updatedTime").addText(//
						aclClass.getUpdatedTime() == null ? "" : DateUtil.format(aclClass
								.getUpdatedTime(), DateUtil.FULL_CN2));
			}
		}
		return new XMLView(root.getDocument());
	}

	public AbstractView doListObjects() throws MVCException {
		Element root = XMLView.getXmlResponseRoot();
		int totalCount = this.aclObjectService.searchCount(getQ());
		if (totalCount == 0) {
			root.addElement("totalCount").addText("0");
		} else {
			List<?> aclObjects = this.aclObjectService
					.search(getQ(), getStartInt(), getLimitInt());
			for (Object obj : aclObjects) {
				AclObject aclObject = (AclObject) obj;
				Element ele = root.addElement("class");
				ele.addElement("id").addText(aclObject.getId() + "");
				ele.addElement("objectId").addText(aclObject.getSecureObject().getId() + "");
				ele.addElement("objectName").addText(aclObject.getSecureObject().getName() + "");
				ele.addElement("ownerId").addText(aclObject.getOwner().getId() + "");
				ele.addElement("ownerName").addText(aclObject.getOwner().getName() + "");
				ele.addElement("classId").addText(aclObject.getAclClass().getId() + "");
				ele.addElement("className").addText(aclObject.getAclClass().getName() + "");
				ele.addElement("createdTime").addText(//
						aclObject.getCreatedTime() == null ? "" : DateUtil.format(aclObject
								.getCreatedTime(), DateUtil.FULL_CN2));
			}
		}
		return new XMLView(root.getDocument());
	}

	public AbstractView doListEntries() throws MVCException {
		Element root = XMLView.getXmlResponseRoot();
		int totalCount = this.aclEntryService.searchCount(getQ());
		if (totalCount == 0) {
			root.addElement("totalCount").addText("0");
		} else {
			List<?> aclEntries = this.aclEntryService.search(getQ(), getStartInt(), getLimitInt());
			for (Object obj : aclEntries) {
				AclEntry aclEntry = (AclEntry) obj;
				Element ele = root.addElement("class");
				ele.addElement("id").addText(aclEntry.getId() + "");
				ele.addElement("roleId").addText(aclEntry.getRole().getId() + "");
				ele.addElement("objectId").addText(aclEntry.getAclObject().getId() + "");
				ele.addElement("mask").addText(aclEntry.getMask() + "");
				ele.addElement("createdTime").addText(//
						aclEntry.getCreatedTime() == null ? "" : DateUtil.format(aclEntry
								.getCreatedTime(), DateUtil.FULL_CN2));
			}
		}
		return new XMLView(root.getDocument());
	}

	public AbstractView doSaveRole() throws MVCException {
		Role role = null;
		if (StringUtil.isBlank(getId())) {
			role = new Role();
		} else {
			role = this.roleService.getById(getId());
			if (role == null) { // 
				return new XMLErrorView("name", "该角色不存在!");
			}
		}
		// check name - not null for new role
		if (StringUtil.isBlank(role.getName()) && StringUtil.isBlank(getName())) {
			return new XMLErrorView("name", "名称不能为空!");
		}
		// check name - unique
		Role sameNameRole = this.roleService.findByName(getName());
		if (sameNameRole != null) {
			return new XMLErrorView("name", "角色[" + getName() + "]已经存在!");
		}
		// update role
		if (StringUtil.notBlank(getName())) {
			role.setName(getName());
		}
		if (StringUtil.notBlank(getDescription())) {
			role.setDescription(getDescription());
		}
		// save into db
		this.roleService.save(role);
		// return success
		return XMLView.SUCCESS;
	}

	public AbstractView doGetUsersOfRole() throws MVCException {
		Role role = this.roleService.getById(getId());
		if (role == null) {
			return new XMLErrorView("id", "角色不存在!");
		}

		Element root = XMLView.getXmlResponseRoot();
		Integer totalCount = this.userInfoService.searchCount(role);
		if (totalCount > 0) {
			root.addElement("totalCount").addText(totalCount + "");
			List<?> users = this.userInfoService.search(role, getStartInt(), getLimitInt());
			for (Object obj : users) {
				UserInfo user = (UserInfo) obj;
				Element ele = root.addElement("user");
				ele.addElement("id").addText(user.getId());
				ele.addElement("username").addText(user.getUser().getUsername());
				ele.addElement("status").addText(user.getStatus() + "");
				ele.addElement("lastLoginTime").addText( //
						user.getLastLoginTime() == null ? "" : //
								DateUtil.format(user.getLastLoginTime(), DateUtil.FULL_CN2));
			}
		} else {
			root.addElement("totalCount").addText("0");
		}

		return new XMLView(root.getDocument());
	}

	/**
	 * Request Parameters: (role)id, username
	 * 
	 * @return XMLView.SUCCESS or XMLErrorView
	 * @throws MVCException
	 */
	public AbstractView doAddUserToRole() throws MVCException {
		Role role = this.roleService.getById(getId());
		if (role == null) {
			return new XMLErrorView("id", "角色不存在!");
		}
		UserInfo user = null;
		if (StringUtil.notBlank(getUid())) {
			user = this.userInfoService.getById(getUid());
		}
		if (user == null && StringUtil.notBlank(getUsername())) {
			user = this.userInfoService.getById(getUsername());
		}
		if (user == null) {
			return new XMLErrorView("id", "用户不存在!");
		}
		// add link record
		RoleLinkUser link = this.roleService.addUser(role, user);
		logger.info("Add new role[" + role.getName() //
				+ "] to user[" + user.getUser().getUsername() //
				+ "] with link[" + link.getIndex() + "].");

		return XMLView.SUCCESS;
	}

	public AbstractView doRemoveUserFromRole() throws MVCException {
		// TODO Auto-generated method stub
		return super.doService();
	}

	public AbstractView doSaveClass() throws MVCException {
		AclClass aclClass = null;
		if (StringUtil.isBlank(getId())) {
			aclClass = new AclClass();
		} else {
			aclClass = this.aclClassService.getById(getId());
			if (aclClass == null) { // 
				return new XMLErrorView("name", "该控制类不存在!");
			}
		}
		// Check name - not null for new class
		if (StringUtil.isBlank(aclClass.getName()) && StringUtil.isBlank(getName())) {
			return new XMLErrorView("name", "名称不能为空!");
		}
		// check name - unique
		AclClass sameNameClass = this.aclClassService.getByClassName(getName());
		if (sameNameClass != null) {
			return new XMLErrorView("name", "控制类[" + getName() + "]已经存在!");
		}
		// update name
		if (StringUtil.notBlank(getName())) {
			aclClass.setName(getName());
		}
		// update description
		if (StringUtil.notBlank(getDescription())) {
			aclClass.setDescription(getDescription());
		}
		// save into db
		this.aclClassService.save(aclClass);

		return XMLView.SUCCESS;
	}

	public AbstractView doSaveObject() throws MVCException {

		return XMLView.SUCCESS;
	}

	public AbstractView doSaveEntry() throws MVCException {

		return XMLView.SUCCESS;
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleInfoService) {
		this.roleService = roleInfoService;
	}

	public AclClassService getAclClassService() {
		return aclClassService;
	}

	public void setAclClassService(AclClassService aclClassService) {
		this.aclClassService = aclClassService;
	}

	public AclObjectService getAclObjectService() {
		return aclObjectService;
	}

	public void setAclObjectService(AclObjectService aclObjectService) {
		this.aclObjectService = aclObjectService;
	}

	public AclEntryService getAclEntryService() {
		return aclEntryService;
	}

	public void setAclEntryService(AclEntryService aclEntryService) {
		this.aclEntryService = aclEntryService;
	}

}
