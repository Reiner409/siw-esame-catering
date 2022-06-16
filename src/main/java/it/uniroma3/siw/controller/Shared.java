package it.uniroma3.siw.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class Shared {
	
	static final String absPath = "src/main/resources/static";

	//Metodo necessario per il salvataggio dell'immagine
	protected static String SavePicture(Long id, String path, MultipartFile image)
	{
		String pictureFolder = "";
		try {
			//Verifico la presenza di un'immagine
			if (!image.isEmpty()) {

				//Ottengo i bytes dell'immagine
				byte[] bytes = image.getBytes();
				File dir = new File(absPath+path);

				//Cerco la cartella
				if (!dir.exists())
					dir.mkdirs();

				//Carico l'immagine
				File uploadFile = new File(dir.getAbsolutePath() + File.separator + id + ".png");
				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
				outputStream.write(bytes);
				outputStream.close();

				//Imposto come nome dell'immagine l'id dell'oggetto.
				pictureFolder = path+id+".png";
				
			}
			else
			{
				//Imposto l'immagine di default
				pictureFolder = path+"default.png";
			}

		} catch (Exception e) {
			//Nel caso in cui è stata lanciata qualche eccezione, imposto l'immagine di defautl
			pictureFolder = path+"default.png";
			
		}
		return pictureFolder;
	}

}
