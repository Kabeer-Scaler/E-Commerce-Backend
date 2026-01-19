package com.Ecommerce.demo.webhook;

import com.Ecommerce.demo.model.Order;
import com.Ecommerce.demo.model.Payment;
import com.Ecommerce.demo.repository.OrderRepository;
import com.Ecommerce.demo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @PostMapping("/payment")
    public String simulateWebhook(@RequestBody String payload) {

        JSONObject json = new JSONObject(payload);

        String event = json.getString("event");

        if ("payment.captured".equals(event)) {

            String razorpayOrderId = json
                    .getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity")
                    .getString("order_id");

            Optional<Payment> optionalPayment = paymentRepository.findAll().stream()
                    .filter(p -> p.getPaymentId().equals(razorpayOrderId))
                    .findFirst();

            if (optionalPayment.isEmpty()) {
                throw new RuntimeException("Payment not found");
            }

            Payment payment = optionalPayment.get();
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);

            Order order = orderRepository.findById(payment.getOrderId()).get();
            order.setStatus("PAID");
            orderRepository.save(order);
        }

        return "Webhook processed successfully";
    }
}