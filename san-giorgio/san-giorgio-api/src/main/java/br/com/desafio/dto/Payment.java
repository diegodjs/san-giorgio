package br.com.desafio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	
	@NotNull(message = "client_id cannot be null")
	@NotEmpty(message = "client_id cannot be empty")
    @JsonProperty("client_id")
    private String clientId;
	
	@NotNull(message = "paymentItems cannot be null")
	@NotEmpty(message = "paymentItems cannot be empty")
    @JsonProperty("payment_items")
    private List<PaymentItem> paymentItems;
}
