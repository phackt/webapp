package com.gab.onewebapp.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.config.ApplicationCommonConfig;
import com.gab.onewebapp.dao.FileDao;
import com.gab.onewebapp.dao.UserDao;
import com.gab.onewebapp.model.FileEntity;
import com.gab.onewebapp.model.UserEntity;

@Service
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ApplicationCommonConfig appConfig;
	
	@Transactional
	public void saveOrUpdate(byte[] bytesFilename, String originalFilename, String description) throws IOException{
		
		//On récupère l'entité du principal (non stockée comme entité) dans le security context
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity currentUser = this.userDao.findByUsername(username);
		
		String storedFilename = originalFilename;
		FileEntity newFileEntity = new FileEntity(currentUser, originalFilename, storedFilename, description);
		
		//On regarde si un fichier du même nom existe => versionning
		if(!(fileDao.findByOriginalFilename(username,originalFilename)).isEmpty()){
			Long version = fileDao.getLastVersion(username,originalFilename) + 1;
			
			String addVersioningString = "_v" 
					+ version
					+ FilenameUtils.EXTENSION_SEPARATOR_STR
					+ FilenameUtils.getExtension(originalFilename);
			
			storedFilename = 
					StringUtils.substring(FilenameUtils.removeExtension(originalFilename),0,ApplicationCommonConfig.MAX_FILENAME_LENGTH - addVersioningString.length())
					+ addVersioningString;
			
			//On met à jour l'entité à créer
			newFileEntity.setStoredFilename(storedFilename);
			newFileEntity.setVersion(version);
			
			logger.info("file " + originalFilename + " already exists, new version is " + version);
		}
		
		
		//Vérification que le répertoire exists
		String strUserUploadDir = this.appConfig.getUploadDirPath() + File.separator + currentUser.getId();
		Path pathUserUploadDir = Paths.get(strUserUploadDir);
		
		if(!Files.exists(pathUserUploadDir)){
			Files.createDirectory(pathUserUploadDir);
		}
		
		//Nom du fichier à sauvegarder
		String storedFileFullPath = strUserUploadDir + File.separator + storedFilename;
		
		//Creation du fichier sur le serveur
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(storedFileFullPath)));
		bos.write(bytesFilename);
		bos.close();
		
		fileDao.saveOrUpdate(newFileEntity);
		
	}

	/**
	 * Choice has been done not to make UserEntity implementing UserDetails
	 * Even if OneToMany is LAZY, later a UserEntity can be the owner of other heavy relation.
	 * So every data will stay in memory. Distinction is also made between security user and entity.
	 */
	@Transactional(readOnly = true)
	public List<FileEntity> findAllFromCurrentUser() {
		return this.fileDao.findAll(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Transactional(readOnly = true)
	public List<FileEntity> findAll() {
		return this.fileDao.findAll(null);
	}

	
	@Transactional
	public void deleteFile(Long id) {
		FileEntity fileToDelete = this.fileDao.findById(id);
		this.fileDao.delete(fileToDelete);
	}
	
	@Transactional(readOnly=true)
	public Long getLastVersionFromCurrentUser(String originalFilename) {
		return this.fileDao.getLastVersion(SecurityContextHolder.getContext().getAuthentication().getName(),originalFilename);
	}
	
	@Transactional(readOnly=true)
	public FileEntity findById(long id){
		return this.fileDao.findById(id); 
	}
	
	@Transactional(readOnly=true)
	public File findStoredOnServerById(long id) {
		UserEntity currentUser = this.userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		FileEntity fileEntity = this.fileDao.findById(id); 
		if(fileEntity != null && !StringUtils.isEmpty(fileEntity.getStoredFilename())){
			return new File(this.appConfig.getUploadDirPath() + File.separator + currentUser.getId() + File.separator + fileEntity.getStoredFilename());
		}

		return null;
	}
}
