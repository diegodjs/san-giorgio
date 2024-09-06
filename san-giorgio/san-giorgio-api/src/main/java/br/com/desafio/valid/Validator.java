package br.com.desafio.valid;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.desafio.dao.PaymentDAO;
import br.com.desafio.domain.model.PaymentModel;
import br.com.desafio.dto.PaymentItem;

@Component
public class Validator {

	@Autowired
	private PaymentDAO paymentDAO;

	/**
	 * Valida o valor do pagamento
	 * @param paymentModel
	 * @return PaymentModel
	 * @throws SQLException
	 */
	public PaymentModel validatePaymentValue(PaymentModel paymentModel) throws SQLException {

		List<PaymentItem> paymentItems = paymentModel.getPaymentItems();

		for (PaymentItem paymentItem : paymentItems) {

			PaymentItem itemOriginal = paymentDAO.getPaymentById(paymentItem.getPaymentId());

			if (itemOriginal.getPaymentValue().compareTo(paymentItem.getPaymentValue()) == 1) {
				paymentItem.setPaymentStatus(StatusEnum.PARCIAL.toString());
			} else if (itemOriginal.getPaymentValue().compareTo(paymentItem.getPaymentValue()) == 0) {
				paymentItem.setPaymentStatus(StatusEnum.TOTAL.toString());
			} else if (itemOriginal.getPaymentValue().compareTo(paymentItem.getPaymentValue()) == -1) {
				paymentItem.setPaymentStatus(StatusEnum.EXCEDENTE.toString());
			}

		}
		return paymentModel;
	}

}
