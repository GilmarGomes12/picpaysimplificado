package com.picpaysimplificado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpaysimplificado.TransactionDTO.TransactionDTO;
import com.picpaysimplificado.domain.transaction.Transaction;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	private Transaction transactionService;
	private Object newtransaction;
	
	@PostMapping
	public ResponseEntity<Transaction> crateTransaction(@RequestBody TransactionDTO transaction) throws Exception{
		Transaction newTransaction = this.transactionService.createTransaction(transaction);
		return new ResponseEntity<>(newtransaction, HttpStatus.OK);
	}

}
