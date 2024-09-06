package br.com.desafio.domain.model;

import java.util.List;

import br.com.desafio.dto.PaymentItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
	
	    private String clientId;
	    private List<PaymentItem> paymentItems;
}
