package br.com.desafio.rule;

import br.com.desafio.dao.ClientDAO;
import br.com.desafio.dao.PaymentDAO;
import br.com.desafio.domain.model.PaymentModel;

public interface Rules<M, C, P, E extends Exception> {

	void validate(PaymentModel paymentModel, ClientDAO clientDAO, PaymentDAO paymentDAO) throws E;
}