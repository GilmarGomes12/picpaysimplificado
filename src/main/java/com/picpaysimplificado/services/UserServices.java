package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.TransactionDTO.UserDTO;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.repositories.UserRepository;

@Service
public class UserServices {
	
	@Autowired
	private UserRepository repository;
	private UserDTO data;
	
	public void validateTransaction(User sender, BigDecimal amount) throws Exception {
		if(sender.getUserType() == UserType.MERCHANT) {
			throw new Exception("Usuário do tipo logista não está autorizado a realizar transação");
		}
		
		if(sender.getBalance().compareTo(amount) < 0) {
			throw new Exception("Saldo insuficiente!");
		}
	}
	
	public User findUserById(Long id) throws Exception {
		return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usárionãoencontrado"));
	}
	
	public User creatUser(UserDTO user) {
		User newUser = new User(data);
		this.saveUser(newUser);
		return newUser;
	}
	
	public List<User> getAllUsers(){
		return this.repository.findAll();
	}
	
	public void saveUser(User user) {
		this.repository.save(user);
	}
}
