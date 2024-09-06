package br.com.desafio.rule;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.desafio.dao.ClientDAO;
import br.com.desafio.dao.PaymentDAO;
import br.com.desafio.domain.model.PaymentModel;

/**
 * RuleClient
 * @author Diego
 */
@Component
public class RuleClient implements Rules<PaymentModel, ClientDAO, PaymentDAO, Exception> {

	@Override
	public void validate(PaymentModel paymentModel, ClientDAO clientDAO, PaymentDAO paymentDAO ) throws Exception {
		Assert.isTrue(clientDAO.getClientById(paymentModel.getClientId()) != null, "Client not found!");
	}
}
