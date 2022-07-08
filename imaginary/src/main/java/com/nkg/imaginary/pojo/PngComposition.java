package com.nkg.imaginary.pojo;

import java.io.File;
import java.util.List;

public class PngComposition {

	public final File main;
	public final File bgnd;
	public final File[] accessories;

	public PngComposition(File main, File bgnd, File... accessories) {
		this.main = main;
		this.bgnd = bgnd;
		this.accessories = accessories;
	}

	public PngComposition(File main, File bgnd, List<File> accessories) {
		this.main = main;
		this.bgnd = bgnd;
		this.accessories = accessories.toArray(new File[0]);
	}
}
