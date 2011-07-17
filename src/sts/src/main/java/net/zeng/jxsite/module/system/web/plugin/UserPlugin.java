/**
 * 
 */
package net.zeng.jxsite.module.system.web.plugin;

import javax.servlet.http.HttpSession;

import net.zeng.mvc.InletServlet;
import net.zeng.mvc.MVCContext;
import net.zeng.mvc.plugin.Plugable;
import net.zeng.mvc.plugin.PluginException;
import net.zeng.mvc.view.DispatchView;

/**
 * @author zeng.xiaoning
 * 
 */
public class UserPlugin implements Plugable {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public UserPlugin() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void disable() throws PluginException {
		// TODO Auto-generated method stub

	}

	public void enable() throws PluginException {
		String url = MVCContext.getInstance().getActionHierachy();
		String urlSuffix = MVCContext.getInstance().getURLSuffix(InletServlet.PARAM_URL_PAGE);
		if (url.contains("admin/") && !url.equals("admin/index." + urlSuffix)) {
			HttpSession session = MVCContext.getInstance().getRequest().getSession(true);
			String username = (String) session.getAttribute("username");
			String password = (String) session.getAttribute("password");
			if (!"admin".equals(username) || !"weizeng".equals(password)) {
				DispatchView view = new DispatchView("/admin/index." + urlSuffix);
				MVCContext.getInstance().getRequest().setAttribute("_VIEW_", view);
				// RequestDispatcher dispatcher =
				// MVCContext.getInstance().getRequest()
				// .getRequestDispatcher("/admin/index.htm");
				// try {
				// dispatcher.forward(MVCContext.getInstance().getRequest(),
				// MVCContext.getInstance()
				// .getResponse());
				// } catch (ServletException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}
		
		// Check identification, null for no login
		
		// find acl_page_identity
		
		// get acl_entry with acl_page_identity and acl_sid
		
		// if matches, pass
		
		// else 
		
		// if page , redirect
		
		// if not anonymous but not login, redirect to login
		
		// if not anonymous but not allowed, redirect to error
		
		// else if not page, AJAX

	}
	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
}
