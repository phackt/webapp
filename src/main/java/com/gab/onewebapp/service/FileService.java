package com.gab.onewebapp.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.config.ApplicationDevConfig;
import com.gab.onewebapp.dao.FileDao;
import com.gab.onewebapp.model.FileEntity;

@Service
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private ApplicationDevConfig appConfig;
	
	public FileService(){
	}
	
	@Transactional
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
					StringUtils.substring(FilenameUtils.removeExtension(originalFilename),0,ApplicationDevConfig.MAX_FILENAME_LENGTH - addVersioningString.length())
					+ addVersioningString;
			
			//On met à jour l'entité à créer
			newFileEntity.setStoredFilename(storedFilename);
			newFileEntity.setVersion(version);
			
			logger.info("file " + originalFilename + " already exists, new version is " + version);
		}
		
		//Nom du fichier à sauvegarder
		String storedFileFullPath = this.appConfig.getUploadDirPath() + File.separator + storedFilename;
		
		//Creation du fichier sur le serveur
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(storedFileFullPath)));
		bos.write(bytesFilename);
		bos.close();
		
		fileDao.saveOrUpdate(newFileEntity);
		
	}

	@Transactional(readOnly = true)
	public List<FileEntity> findAll() {
		return this.fileDao.findAll();
	}

	@Transactional
	public void deleteFile(Long id) {
		FileEntity fileToDelete = this.fileDao.findById(id);
		this.fileDao.delete(fileToDelete);
	}
	
	@Transactional(readOnly=true)
	public Long getLastVersion(String originalFilename) {
		return this.fileDao.getLastVersion(originalFilename);
	}
	
	@Transactional(readOnly=true)
	public FileEntity findById(long id){
		return this.fileDao.findById(id); 
	}
}
