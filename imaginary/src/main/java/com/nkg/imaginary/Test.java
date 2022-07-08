package com.nkg.imaginary;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test {

	public static void main(String[] args) {
		new Test().test2();
	}

	BufferedImage image1;
	BufferedImage image2;

	public void test() {
		System.out.println("--------------------------------------------------");

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BufferedImage combindImage;

		try {
			image1 = ImageIO.read(getClass().getClassLoader().getResource("image1.png"));
			image2 = ImageIO.read(getClass().getClassLoader().getResource("image2.png"));

			combindImage = new BufferedImage(599,462,BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = combindImage.createGraphics();

			g.drawImage(image1,0, 0, null);
			image1.flush();
			g.drawImage(image2,0, 0, null);
			image2.flush();

			g.dispose();

			JLabel label = new JLabel();
			window.add(label);
			label.setIcon(new ImageIcon(combindImage));

		} catch (IOException e) {
			e.printStackTrace();
		}

		window.pack();
		window.setVisible(true);
	}

	public void test2() {
		System.out.println("--------------------------------------------------");

		BufferedImage combindImage;

		try {
			image1 = ImageIO.read(getClass().getClassLoader().getResource("image1.png"));
			image2 = ImageIO.read(getClass().getClassLoader().getResource("image2.png"));

			combindImage = new BufferedImage(599,462,BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = combindImage.createGraphics();

			g.drawImage(image1,0, 0, null);
			image1.flush();
			g.drawImage(image2,0, 0, null);
			image2.flush();

			g.dispose();

			ImageIO.write(combindImage, "png", new File("D:/testoutput.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
