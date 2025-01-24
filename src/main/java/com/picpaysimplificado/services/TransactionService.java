package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpaysimplificado.TransactionDTO.TransactionDTO;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.repositories.TransactionRepository;

import jakarta.transaction.Transaction;

@Service
public class TransactionService {
	
	@Autowired
	private ExecutorService userService;
	
	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private NotificationService notificationService;

	private String url;
	
	public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
		User sender = this.userService.findUserById(transactionDTO.senderId());
		User receiver = this.userService.findUserById(transactionDTO.receiverId());
		
		userService.validateTransaction(sender, transactionDTO.value());
		
		HttpStatus transaction;
		boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
		if(!isAuthorized) {
			throw new Exception("Transação não autorizada");
		}
		
		Transaction newtransaction = new Transaction();
		newtransaction.setAmount(transaction.value());
		newtransaction.setSender(sender);
		newtransaction.setReceiver(receiver);
		newtransaction.setTimestamp(LocalDateTime.now());
		sender.setBalance(sender.getBalance().subtract(transaction.value()));
		receiver.setBalance(receiver.getBalance().add(transaction.value()));
		
		Transaction newTransaction;
		repository.save(newTransaction);
		userService.saveUser(sender);	  
		userService.saveUser(receiver);
		
		this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
		this.notificationService.sendNotification(receiver, "Transação realizada com sucesso");
		
		return newTransaction;
	}
	
	private boolean authorizeTransaction(User sender, int value) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean authorizeTransaction(User sender, BigDecimal value) {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity( "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
		
		if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
			String message = (String) authorizationResponse.getBody().get("message");
			return "Autorizado".equalsIgnoreCase(message);
		}else return false;
	}

	public TransactionRepository getRepository() {
		return repository;
	}

	public void setRepository(TransactionRepository repository) {
		this.repository = repository;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
