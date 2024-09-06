package br.com.desafio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentItem {
	
	@NotNull(message = "payment_id cannot be null")
	@NotEmpty(message = "payment_id cannot be empty")
    @JsonProperty("payment_id")
    private String paymentId;
    
	@NotNull(message = "payment_value cannot be null")
	@NotEmpty(message = "payment_value cannot be empty")
    @JsonProperty("payment_value")
    private BigDecimal paymentValue;
    
	@NotNull(message = "payment_status cannot be null")
	@NotEmpty(message = "payment_status cannot be empty")
    @JsonProperty("payment_status")
    private String paymentStatus;
}
