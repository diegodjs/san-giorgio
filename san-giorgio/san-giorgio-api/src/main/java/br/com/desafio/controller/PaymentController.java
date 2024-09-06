package br.com.desafio.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.domain.model.PaymentModel;
import br.com.desafio.dto.Payment;
import br.com.desafio.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PaymentController {

	@Autowired
    private PaymentService paymentService;

    @PutMapping(path = "/api/payment")
    public ResponseEntity<?> setPayment(@Valid @RequestBody Payment request) {

    	try {
			return paymentService.setPayment(PaymentModel.builder()
			    	.clientId(request.getClientId())
			    	.paymentItems(request.getPaymentItems())
			    	.build());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error Message = " + e.getMessage());
		}
    }
}
