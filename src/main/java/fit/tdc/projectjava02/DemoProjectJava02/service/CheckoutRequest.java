package fit.tdc.projectjava02.DemoProjectJava02.service;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String fullName;
    private String email;
    private String address;
    private String city;
    private String paymentMethod;
    private String cardNumber;
    private String expiry;
    private String cvc;
}
