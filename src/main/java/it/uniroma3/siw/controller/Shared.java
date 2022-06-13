package it.uniroma3.siw.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class Shared {
	
	static final String absPath = "src/main/resources/static";
	
	protected static String SavePicture(Long id, String path, MultipartFile image)
	{
		String pictureFolder = "";
		try {
			if (!image.isEmpty()) {
				byte[] bytes = image.getBytes();
				File dir = new File(absPath+path);
				if (!dir.exists())
					dir.mkdirs();

				File uploadFile = new File(dir.getAbsolutePath() + File.separator + id + ".png");
				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
				outputStream.write(bytes);
				outputStream.close();

				pictureFolder = path+id+".png";
				
			}

		} catch (Exception e) {
			pictureFolder = path+"default.png";
			
		}
		return pictureFolder;
	}

}
