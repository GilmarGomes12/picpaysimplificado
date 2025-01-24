package com.picpaysimplificado.TransactionDTO;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {

	public Object getAmount() {
		// TODO Auto-generated method stub
		return null;
	}

}
