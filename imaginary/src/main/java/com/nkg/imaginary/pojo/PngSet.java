package com.nkg.imaginary.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PngSet {

	public List<File> main = new ArrayList<>();
	public List<File> bg = new ArrayList<>();
	public List<File> accessories = new ArrayList<>();

	public PngSet() {}

	public PngSet(List<File> files) {
		addPng(files);
	}

	public void addPng(List<File> files) {
		for (File f : files) {
			addPng(f);
		}
	}

	public void addPng(File file) {

		//檔名檢核
		String fileName = file.toString();
		int index = fileName.lastIndexOf('.');
		if (index < 0) {
			//沒有附檔名
			return;
		}

		if (!fileName.substring(index + 1).equalsIgnoreCase("png")) {
			//非PNG檔
			return;
		}

		if (file.getParentFile().getName().equalsIgnoreCase("main")) {
			main.add(file);
			return;
		}

		if (file.getParentFile().getName().equalsIgnoreCase("bg")) {
			bg.add(file);
			return;
		}

		if (file.isFile()) {
			accessories.add(file);
			return;
		}
	}

	public int count() {
		return main.size() + bg.size() + accessories.size();
	}
}
