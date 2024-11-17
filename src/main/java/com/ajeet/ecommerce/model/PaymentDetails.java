package com.ajeet.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
	
	private String paymentMethod;
	private String status;
	private String paymentId;
	private String razorPayPaymentLinkId;
	private String razorPayPaymentLinkReferenceId;
	private String razorPayPaymentLinkStatus;
	private String razorPayPaymentId;
}
