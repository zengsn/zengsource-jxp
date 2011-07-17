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
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import net.zeng.jxsite.module.designlab.dao.FontMetaDao;
import net.zeng.jxsite.module.designlab.model.FontMeta;
import net.zeng.util.GraphicsUtil;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class FontMetaServiceImpl implements FontMetaService {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private FontMetaDao fontMetaDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public FontMetaServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public List<?> getAll() {
		return this.fontMetaDao.queryAll();
	}

	public FontMeta getById(String id) {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return (FontMeta) this.fontMetaDao.queryById(id);
	}

	public void save(FontMeta font) {
		this.fontMetaDao.save(font);
	}

	public void scanFontDirectory(File fontsDir) {
		if (fontsDir != null && fontsDir.isDirectory()) {
			File[] dirArr = fontsDir.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			});
			for (File dir : dirArr) {
				FontMeta font = new FontMeta();
				FontMeta.parseMeta(font, dir);
				// Check database
				FontMeta dbFont = getById(font.getId());
				if (dbFont == null) { // If not found
					save(font); // Save it
				}
			}
		}
	}

	public void createSnapshot(FontMeta fontMeta, int[] size) {
		BufferedImage snap = new BufferedImage(size[0], size[1], BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = snap.createGraphics();
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontMeta.getTTFFile());
			// font = new Font("Serif", Font.PLAIN, 36);
			Font font2 = font.deriveFont(32.0f);
			System.out.println(font2.getSize());
			g2.setFont(font2);
			FontRenderContext frc = g2.getFontRenderContext();
			String text = fontMeta.getName();
			// String text = "ABC";
			Rectangle2D boundsText = font2.getStringBounds(text, frc);
			int wText = (int) boundsText.getWidth();
			int hText = (int) boundsText.getHeight();
			int rX = (size[0] - wText) / 2;
			int rY = (size[1] - hText) / 2;
			// g2.setColor(Color.LIGHT_GRAY);
			// g2.fillRect(rX, rY, wText, hText);
			g2.fillRect(1, 1, size[0] - 2, size[1] - 2);
			g2.setColor(Color.BLACK);
			int xTextTemp = rX - (int) boundsText.getX();
			int yTextTemp = rY - (int) boundsText.getY();
			g2.drawString(text, xTextTemp, yTextTemp);
			String snapName = "snap-" + IDUtil.generate() + ".gif";
			File output = new File(fontMeta.getDirectory() + "/" + snapName);
			GraphicsUtil.write(snap, output);
			fontMeta.setImage(snapName); // update
			save(fontMeta); // Save in database
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public FontMetaDao getFontMetaDao() {
		return fontMetaDao;
	}

	public void setFontMetaDao(FontMetaDao fontMetaDao) {
		this.fontMetaDao = fontMetaDao;
	}

}
