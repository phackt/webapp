package com.gab.onewebapp.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gab.onewebapp.config.ApplicationConfig;
import com.gab.onewebapp.dao.FileDao;
import com.gab.onewebapp.model.FileEntity;

@Service
public class FileService {

	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private ApplicationConfig appConfig;
	
	public FileService(){
	}
	
	public void saveOrUpdate(byte[] bytesFilename, String originalFilename, String description) throws IOException{
		
		String storedFilename = originalFilename;
		FileEntity newFileEntity = new FileEntity(originalFilename, storedFilename, description);
		
		//On regarde si un fichier du même nom existe => versionning
		if(!(fileDao.findByOriginalFilename(originalFilename)).isEmpty()){
			Long version = fileDao.getLastVersion(originalFilename) + 1;
			
			String addVersioningString = "_v" 
					+ version
					+ FilenameUtils.EXTENSION_SEPARATOR_STR
					+ FilenameUtils.getExtension(originalFilename);
			
			storedFilename = 
					StringUtils.substring(FilenameUtils.removeExtension(originalFilename),0,ApplicationConfig.MAX_FILENAME_LENGTH - addVersioningString.length())
					+ addVersioningString;
			
			//On met à jour l'entité à créer
			newFileEntity.setStoredFilename(storedFilename);
			newFileEntity.setVersion(version);
		}
		
		//Nom du fichier à sauvegarder
		String storedFileFullPath = this.appConfig.getUploadDirPath() + File.separator + storedFilename;
		
		//Creation du fichier sur le serveur
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(storedFileFullPath)));
		bos.write(bytesFilename);
		bos.close();
		
		fileDao.saveOrUpdate(newFileEntity);
		
	}

	public List<FileEntity> findAll() {
		return this.fileDao.findAll();
	}

	public void deleteFile(Long id) {
		FileEntity fileToDelete = this.fileDao.findById(id);
		this.fileDao.delete(fileToDelete);
	}
}
