package br.com.desafio.domain.usecase;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;

import br.com.desafio.domain.model.PaymentModel;
import br.com.desafio.dto.PaymentItem;
import br.com.desafio.valid.StatusEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConfirmPaymentUseCaseImpl implements ConfirmPaymentUseCase{
	
	private static final String QUEUE_PARCIAL = "testQueueParcial" + new Date().getTime();
	private static final String QUEUE_TOTAL = "testQueueTotal" + new Date().getTime();
	private static final String QUEUE_EXCEDENTE = "testQueueExcedente" + new Date().getTime();
	
	@Override
	public PaymentModel confirm(PaymentModel paymentModel) {
		
		try { 
			AmazonSQS sqs = returnSqs();
			
			Gson gson = new Gson();
			
			List<PaymentItem> paymentItems = paymentModel.getPaymentItems();
	
			for (PaymentItem paymentItem : paymentItems) {
				
				switch (StatusEnum.valueOf(paymentItem.getPaymentStatus())) {
					case PARCIAL : {
						
						log.info("ConfirmPaymentUseCaseImpl - confirm - PARCIAL - PaymentId: " + paymentItem.getPaymentId());
						String queueUrl = sqs.getQueueUrl(QUEUE_PARCIAL).getQueueUrl();
						SendMessageRequest send_msg_request = new SendMessageRequest()
				                .withQueueUrl(queueUrl)
				                .withMessageBody(gson.toJson(paymentItem))
				                .withDelaySeconds(5);
				        sqs.sendMessage(send_msg_request);
						
				        break;
					}	
					case TOTAL : {
						log.info("ConfirmPaymentUseCaseImpl - confirm - TOTAL - PaymentId: " + paymentItem.getPaymentId());
						String queueUrl = sqs.getQueueUrl(QUEUE_TOTAL).getQueueUrl();
						SendMessageRequest send_msg_request = new SendMessageRequest()
				                .withQueueUrl(queueUrl)
				                .withMessageBody(gson.toJson(paymentItem))
				                .withDelaySeconds(5);
				        sqs.sendMessage(send_msg_request);
						break;
					}
					case EXCEDENTE : {
						log.info("ConfirmPaymentUseCaseImpl - confirm - EXCEDENTE - PaymentId: " + paymentItem.getPaymentId());
						String queueUrl = sqs.getQueueUrl(QUEUE_EXCEDENTE).getQueueUrl();
						SendMessageRequest send_msg_request = new SendMessageRequest()
				                .withQueueUrl(queueUrl)
				                .withMessageBody(gson.toJson(paymentItem))
				                .withDelaySeconds(5);
				        sqs.sendMessage(send_msg_request);
						break;
					}
				}
			}
		}
		catch (Exception e) {
			log.info("ConfirmPaymentUseCaseImpl - confirm: " + e.getMessage());
		}
		
		return paymentModel;
	}
	
	
	private AmazonSQS returnSqs() {
		
		 BasicAWSCredentials awsCredentials = new BasicAWSCredentials("accessKey", "secretKey");
		 
		 AmazonSQS sqs = null;
		 
		 try {
			 sqs = AmazonSQSClientBuilder.standard()
						.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
			            .withRegion(Regions.EU_CENTRAL_1)
			            .build();
	        } catch (Exception e) {
	           log.info("ConfirmPaymentUseCaseImpl - returnSqs: " + e.getMessage());
	        }
		
		
		try {
            sqs.createQueue(QUEUE_PARCIAL);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
            	log.info("ConfirmPaymentUseCaseImpl - returnSqs: " + e.getMessage());
            }
        }
		
		try {
            sqs.createQueue(QUEUE_TOTAL);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
            	log.info("ConfirmPaymentUseCaseImpl - returnSqs: " + e.getMessage());
            }
        }
		
		try {
            sqs.createQueue(QUEUE_EXCEDENTE);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
            	log.info("ConfirmPaymentUseCaseImpl - returnSqs: " + e.getMessage());
            }
        }
		
		return sqs;
	}

}
