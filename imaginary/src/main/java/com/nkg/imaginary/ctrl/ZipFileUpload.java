package com.nkg.imaginary.ctrl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nkg.imaginary.Context;
import com.nkg.imaginary.common.util.ImageUtil;
import com.nkg.imaginary.pojo.PngComposition;
import com.nkg.imaginary.pojo.PngSet;
import com.nkg.imaginary.service.PngCombineService;

@RestController
public class ZipFileUpload extends BaseController {

	public static final int BUFFER_SIZE = 1048576;
	public static final int ACCESSORIES_LIMIT = 20;

	@Autowired private PngCombineService pngCombineServicer;

	@RequestMapping(value = "/zip_upload",
			method = RequestMethod.POST)
	public String merge(
			@RequestParam("zip_file") MultipartFile file,
			@RequestParam int limit,
			HttpServletResponse resp) throws Exception {

		if (limit <= 0) {
			return "What The F... ?";
		}

		logger.info("uploaded: " + file.getSize());
		List<File> srcPng = new ArrayList<>();

		//UPZIP START ------------------------------------------------------------
		byte[] buffer = new byte[BUFFER_SIZE];
		ZipInputStream zis = new ZipInputStream(file.getInputStream());
		ZipEntry zipEntry = zis.getNextEntry();
		File destDir = Files.createTempDirectory(Context.UPLOAD_TMP_DIR).toFile();
		logger.info("[DIR MATCH] " + destDir.getAbsolutePath() + " | " + file.getName());

		while (zipEntry != null) {

			File newFile = newFile(destDir, zipEntry);
			if (zipEntry.isDirectory()) {
				if (!newFile.isDirectory() && !newFile.mkdirs()) {
					throw new IOException("Failed to create directory " + newFile);
				}
			}
			else {
				// fix for Windows-created archives
				File parent = newFile.getParentFile();
				if (!parent.isDirectory() && !parent.mkdirs()) {
					throw new IOException("Failed to create directory " + parent);
				}

				// write file content
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();

				srcPng.add(newFile);
			}

			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		//UPZIP END ------------------------------------------------------------
		logger.info("unzip completed.");

		//Verify
		PngSet pngSet = new PngSet(srcPng);
		if (pngSet.count() == 0) {
			return "No any PNG";
		}
		else if (pngSet.main.isEmpty()) {
			return "No main PNG";
		}
		else if (pngSet.bg.isEmpty()) {
			return "No background PNG";
		}
		else if (pngSet.accessories.size() > ACCESSORIES_LIMIT) {
			return "Accessories can't larger than " + ACCESSORIES_LIMIT;
		}

		//Composition algorithm
		List<PngComposition> result = pngCombineServicer.combine(pngSet, limit);
		logger.info("Composition completed, RESULT: " + result.size());

		//Perform PNG generate
		File outputDir = Files.createTempDirectory(destDir.toPath(), "produce").toFile();
		DecimalFormat df = new DecimalFormat("00000");
		for (int i = 0; i < result.size(); i++) {
			drawPng(result.get(i), new File(outputDir, df.format(i)+".png"));
		}
		logger.info("Draw all completed.");

		//zip dir
		resp.setContentType("application/zip");
		resp.setHeader("Content-disposition", "attachment; filename="+ destDir.getName() + ".zip");
		pack(outputDir.getAbsolutePath(), resp.getOutputStream());
		logger.info("Pack completed.");

		return "OK";
	}

	public static void drawPng(PngComposition src, File file) throws IOException {
		BufferedImage main = ImageIO.read(src.main);
		BufferedImage bgnd = ImageIO.read(src.bgnd);
		BufferedImage combindImage = ImageUtil.merge(bgnd, main);

		for (File f : src.accessories) {
			BufferedImage img = ImageIO.read(f);
			combindImage = ImageUtil.merge(combindImage, img);
		}

		ImageIO.write(combindImage, "png", file);
	}


	//避免漏洞 Zip Slip，防止將文件寫入目標文件夾之外的文件系統
	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	public static void pack(String sourceDirPath, OutputStream out) throws IOException {
		try (ZipOutputStream zs = new ZipOutputStream(out)) {
			Path pp = Paths.get(sourceDirPath);
			Files.walk(pp)
				.filter(path -> !Files.isDirectory(path))
				.forEach(path -> {
					ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
					try {
						zs.putNextEntry(zipEntry);
						Files.copy(path, zs);
						zs.closeEntry();
					}
					catch (IOException e) {
						System.err.println(e);
					}
			});
		}
	}

	public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
		Path p = Files.createFile(Paths.get(zipFilePath));

		try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
			Path pp = Paths.get(sourceDirPath);
			Files.walk(pp)
				.filter(path -> !Files.isDirectory(path))
				.forEach(path -> {
					ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
					try {
						zs.putNextEntry(zipEntry);
						Files.copy(path, zs);
						zs.closeEntry();
					}
					catch (IOException e) {
						System.err.println(e);
					}
			});
		}
	}
}
