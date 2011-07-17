/**
 * 
 */
package net.zeng.jxsite.module.designlab.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.zeng.jxsite.module.designlab.dao.DesignDao;
import net.zeng.jxsite.module.designlab.dao.ImageItemDao;
import net.zeng.jxsite.module.designlab.dao.TextItemDao;
import net.zeng.jxsite.module.designlab.model.Design;
import net.zeng.jxsite.module.designlab.model.FontMeta;
import net.zeng.jxsite.module.designlab.model.ImageItem;
import net.zeng.jxsite.module.designlab.model.TextItem;
import net.zeng.util.GraphicsUtil;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class DesignServiceImpl implements DesignService {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private DesignDao designDao;
	private TextItemDao textItemDao;
	private ImageItemDao imageItemDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public DesignServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public boolean save(Design design) {
		// Save image items
		for (ImageItem item : design.getImageItems()) {
			item.setDesign(null);
			this.imageItemDao.save(item);
			item.setDesign(design);
		}
		// Save design
		this.designDao.save(design);

		// Build file

		return true;
	}

	public Design getById(String id) {
		if (StringUtil.notBlank(id)) {
			return this.designDao.queryById(id);
		}
		return null;
	}

	public TextItem getTextItem(String id) {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return (TextItem) this.textItemDao.queryById(id);
	}
	
	public void createTextImage(String dir, TextItem textItem) {
		int imgWidth = 200;
		int imgHeight = 200;
		BufferedImage bi = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bi.createGraphics();
		FontMeta fontMeta = textItem.getFont();

		try {
			Font font = null;
			if (fontMeta != null) {
				float size = textItem.getTextSize() + 0.0f;
				font = Font.createFont(Font.TRUETYPE_FONT, fontMeta.getTTFFile()).deriveFont(size);
			} else { // TODO - use default font
				font = new Font("Serif", Font.PLAIN, 16);
			}
			g2.setFont(font);
			FontRenderContext frc = g2.getFontRenderContext();
			String text = fontMeta.getName();
			// String text = "ABC";
			Rectangle2D boundsText = font.getStringBounds(text, frc);
			int wText = (int) boundsText.getWidth();
			int hText = (int) boundsText.getHeight();
			int rX = (imgWidth - wText) / 2;
			int rY = (imgHeight - hText) / 2;
			// g2.setColor(Color.LIGHT_GRAY);
			// g2.fillRect(rX, rY, wText, hText);
			g2.fillRect(1, 1, imgWidth - 2, imgHeight - 2);
			g2.setColor(Color.BLACK);
			int xTextTemp = rX - (int) boundsText.getX();
			int yTextTemp = rY - (int) boundsText.getY();
			g2.drawString(text, xTextTemp, yTextTemp);
			if (StringUtil.isBlank(textItem.getId())) {
				textItem.setId(IDUtil.generate());
			}
			String imgName = "text-" + textItem.getId() + ".gif";
			File output = new File(dir + "/" + imgName);
			GraphicsUtil.write(bi, output);
			textItem.setImage(imgName);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public DesignDao getDesignDao() {
		return designDao;
	}

	public void setDesignDao(DesignDao designDao) {
		this.designDao = designDao;
	}

	public TextItemDao getTextItemDao() {
		return textItemDao;
	}

	public void setTextItemDao(TextItemDao textItemDao) {
		this.textItemDao = textItemDao;
	}

	public ImageItemDao getImageItemDao() {
		return imageItemDao;
	}

	public void setImageItemDao(ImageItemDao imageItemDao) {
		this.imageItemDao = imageItemDao;
	}

}
