package br.com.desafio.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.desafio.dao.ClientDAO;
import br.com.desafio.dao.PaymentDAO;
import br.com.desafio.domain.model.PaymentModel;
import br.com.desafio.domain.usecase.ConfirmPaymentUseCase;
import br.com.desafio.rule.RuleClient;
import br.com.desafio.rule.RulePayment;
import br.com.desafio.rule.Rules;
import br.com.desafio.valid.Validator;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class PaymentService {
	
	@Autowired
    private ConfirmPaymentUseCase confirmPaymentUseCase;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private PaymentDAO paymentDAO;
	
	@Autowired
	private Validator validator;
	
	/**
	 * Realiza o pagamento 
	 * @param paymentModel
	 * @return ResponseEntity
	 * @throws Exception
	 */
	public ResponseEntity<?> setPayment(PaymentModel paymentModel) throws Exception {
		
		//////////////
		this.inicialLoadClient();
		this.inicialLoadPayment();
		////////////////
		
		log.info("Service - setPayment - Begin");
		
		// Aplica as classes de regras
		List<Rules<PaymentModel, ClientDAO, PaymentDAO, Exception>> rules = Arrays.asList(
				new RuleClient(),
				new RulePayment()
		);
		
		// Realiza as validações das Regras
		for (Rules<PaymentModel, ClientDAO, PaymentDAO, Exception> rule : rules) {
			try {
				rule.validate(paymentModel, clientDAO, paymentDAO);
			} catch (Exception e) {
				log.info("Unauthorized rule: " + e.getMessage());
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		//Validar valor do pagamento
		PaymentModel paymentModelResult = validator.validatePaymentValue(paymentModel);
		
		// Confirma o pagamento enviando para as filas
		confirmPaymentUseCase.confirm(paymentModelResult);
		
		log.info("Service - setPayment - Sucess");

		return new ResponseEntity<PaymentModel>(paymentModelResult, HttpStatus.OK);
	}
	
	/**
	 * Cria a tabela de Client e insere dados inicias
	 * @throws SQLException
	 */
	private void inicialLoadClient() throws SQLException {
		clientDAO.createTableClient();
		
		if (clientDAO.getClientById("1") == null) {
			clientDAO.createClient("1", "Vendedor A");
		}
		
		if (clientDAO.getClientById("2") == null) {
			clientDAO.createClient("2", "Vendedor B");
		}
	}
	
	/**
	 * Cria a tabela de Payment e insere dados inicias
	 * @throws SQLException
	 */
	private void inicialLoadPayment() throws SQLException {
		paymentDAO.createTablePayment();
		
		if (paymentDAO.getPaymentById("111") == null) {
			paymentDAO.createPayment("111", new BigDecimal(100.00), "");
		}
		
		if (paymentDAO.getPaymentById("222") == null) {
			paymentDAO.createPayment("222", new BigDecimal(200.00), "");
		}
		
		if (paymentDAO.getPaymentById("333") == null) {
			paymentDAO.createPayment("333", new BigDecimal(300.00), "");
		}
	}
	
}
