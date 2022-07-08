package com.nkg.imaginary.common.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageUtil {

	public static BufferedImage merge(BufferedImage... images) {

		if (images.length == 0) {
			return null;
		}

		int width = images[0].getWidth();
		int height = images[0].getHeight();

		BufferedImage combindImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = combindImage.createGraphics();

		for (BufferedImage img : images) {
			g.drawImage(img, 0, 0, null);
			img.flush();
		}

		g.dispose();

		return combindImage;
	}
}
