package com.upa.codigorupestre.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upa.codigorupestre.service.MailService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/user")
public class UserAPI {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> creteUser(@RequestBody UserEntity user) {
        log.info("Create USER - POST {} ", user);
		repository.save(user);
		
		String fullName = user.getName() + " " + user.getLastName();
		mailService.sendMailConfirmAccount(user.getEmail(), fullName, user.getPlanet());
		return ResponseEntity.ok("User created : " + user.getUserName());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{userName}")
	public ResponseEntity<Object> getUser(@PathVariable("userName") String userName) {
		log.info("fin user " + userName + " GET" );
		
		List<UserEntity> user = repository.findByUserName(userName);
		
		if(!user.isEmpty()) {
			return ResponseEntity.ok(user.get(0));
		}else {
			return ResponseEntity.ok("No existe usuario: " + userName);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable("id") Integer id, @RequestBody UserEntity userUpdate) {
		log.info("Update User " + userUpdate + " UPDATE");
		
		 Optional<UserEntity> updateUser = repository.findById(id);
		 
		 if(!updateUser.isEmpty()) {
			 UserEntity user = updateUser.get();
			 user.setName(userUpdate.getName());
			 user.setLastName(userUpdate.getLastName());
			 user.setEmail(userUpdate.getEmail());
			 user.setPassword(userUpdate.getPassword());
			 
			 repository.save(user);
			 
			 return ResponseEntity.ok("User actualizado de forma exitosa");
		 } else {
			 return ResponseEntity.ok("No existe el usuario que se quiere actualizar");
		 }
	}

	@RequestMapping(method = RequestMethod.PATCH, path = "/{id}/{newpassword}")
	public ResponseEntity<Object> updatePasswordUser(@PathVariable("id") Integer id, @PathVariable("newpassword") String newpassword) {
		log.info("Update password PATCH");
		
		Optional<UserEntity> updateUser = repository.findById(id);
		
		if(!updateUser.isEmpty()) {
			UserEntity user = updateUser.get();
			user.setPassword(newpassword);
			repository.save(user);
			
			return ResponseEntity.ok("Contraseña actualizada de forma exitosa");
		} else {
			return ResponseEntity.ok("No existe el usuario que se quiere actualizar su contraseña");
		}
	}


	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Integer id) {
		log.info("Delete User DELETE");
		Optional<UserEntity> deleteUser = repository.findById(id);
		if(!deleteUser.isEmpty()) {
			repository.delete(deleteUser.get());
			return ResponseEntity.ok("Usuario eliminado de forma exitosa");
		} else {
			return ResponseEntity.ok("No existe el usuario que se quiere eliminar");
		}
	}

	
	@RequestMapping(method = RequestMethod.HEAD, path = "/{id}")
	public ResponseEntity<Object> validateUser(@PathVariable("id") Integer id) {
		log.info("Validate user HEAD");
		Optional<UserEntity> user = repository.findById(id);
		
		if(!user.isEmpty()) {
			return ResponseEntity.ok("El usuario si existe" + user.get());
		} else {
			return ResponseEntity.noContent().build();
		}

		
	}

	@RequestMapping(method = RequestMethod.OPTIONS)
	public void validateOptions() {
		log.info("method OPTIONS");

	}

}
