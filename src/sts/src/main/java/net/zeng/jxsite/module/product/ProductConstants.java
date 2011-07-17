/**
 * 
 */
package net.zeng.jxsite.module.product;

import net.zeng.jxsite.module.system.SystemConstants;

/**
 * @author snzeng
 * @since 6.0
 */
public interface ProductConstants extends SystemConstants {

	public static final String MODULE_PRODUCT = "product";
	
	/** "/images/product" */
	public static final String UPLOAD_IMAGE = IMAGES_DIR + "/" + MODULE_PRODUCT;
	
	/** "/attachments/product" */
	public static final String UPLOAD_ATTACH = ATTACH_DIR + "/" + MODULE_PRODUCT;
	
}
