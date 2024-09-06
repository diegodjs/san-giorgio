package br.com.desafio.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.desafio.dao.ClientDAO;
import br.com.desafio.dao.PaymentDAO;
import br.com.desafio.domain.model.PaymentModel;
import br.com.desafio.dto.PaymentItem;

/**
 * RuleClient
 * @author Diego
 */
@Component
public class RulePayment implements Rules<PaymentModel, ClientDAO, PaymentDAO, Exception> {

	@Override
	public void validate(PaymentModel paymentModel, ClientDAO clientDAO, PaymentDAO paymentDAO ) throws Exception {
		
		List<String> paymentNotExistsList = new ArrayList<String>();
		List<PaymentItem> paymentItems = paymentModel.getPaymentItems(); 
		
		for (PaymentItem paymentItem : paymentItems) {
			if (paymentDAO.getPaymentById(paymentItem.getPaymentId()) == null ) {
				paymentNotExistsList.add(paymentItem.getPaymentId());
			}
		}
		
		String paymentNotExistsMessage = "Payment not found: \n";
		if (!paymentNotExistsList.isEmpty()) {
			for (String paymentNotExists : paymentNotExistsList) {
				paymentNotExistsMessage = paymentNotExistsMessage.concat("Id : " + paymentNotExists);
				paymentNotExistsMessage = paymentNotExistsMessage.concat("\n");
			}
		}
		
		Assert.isTrue(paymentNotExistsList.isEmpty(), paymentNotExistsMessage);
	}
}
