/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import javax.servlet.http.HttpSession;

import net.zeng.mvc.MVCContext;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.GenericAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLView;

/**
 * @author snzeng
 * @since 6.0
 */
public class IndexAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private String username;
	private String password;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public IndexAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	protected AbstractView doService() throws MVCException {
		if ("admin".equals(username) && "weizeng".equals(password)) {
			MVCContext mvcContext = MVCContext.getInstance();
			HttpSession session = mvcContext.getRequest().getSession(true);
			session.setAttribute("username", "admin");
			session.setAttribute("password", "weizeng");
			return XMLView.SUCCESS;
		} else {
			// XMLErrorView errorView = new XMLErrorView();
			// errorView.put("usename", "Username/Password mismatched!");
			// errorView.put("password", "Username/Password mismatched!");
			// return errorView;
			return super.doService();
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
