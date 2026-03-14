import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrderProcessorTest {
    private StandardOrderProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new StandardOrderProcessor();
    }

    // --- Positive Tests ---

    @Test
    void shouldProcessOrderWithDiscount() {
        // 5 items * 5000 = 25000 (should get 10% discount)
        OrderItem[] items = { new OrderItem("Laptop", 5) };
        Order order = new Order("ORD-001", items);

        processor.process(order, new CardPayment());

        assertEquals(22500.0, order.getPrice(), "Should apply 10% discount for orders >= 20000");
        assertEquals("PAID", order.getStatus());
    }

    @Test
    void shouldFindOrderByIdUsingOptional() {
        Optional<Order> result = processor.findById("123");
        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    void shouldAllowStateTransitionFromDeliveredToReturned() {
        Order order = new Order("ORD-002", new OrderItem[]{new OrderItem("Book", 1)});
        order.setStatus("DELIVERED");

        order.markAsReturned();

        assertEquals("RETURNED", order.getStatus());
    }

    // --- Negative Tests ---

    @Test
    void shouldThrowExceptionWhenFraudDetected() {
        // V09: Specific negative scenario for FraudCheck
        OrderItem[] items = { new OrderItem("Phone", 1) };
        Order fraudOrder = new Order("FRAUD_DETECTED_001", items);

        assertThrows(FraudCheckFailedException.class, () -> {
            processor.process(fraudOrder, new CardPayment());
        });
    }

    @Test
    void shouldThrowExceptionForInvalidQuantity() {
        // R2: Validation 1..5 items
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem("Water", 6);
        });
    }

    @Test
    void shouldThrowExceptionForCardPaymentAboveLimit() {
        // P3: CardPayment allows up to 30,000
        CardPayment card = new CardPayment();
        assertThrows(IllegalArgumentException.class, () -> card.pay(30001));
    }

    @Test
    void shouldThrowExceptionWhenOrderIsEmpty() {
        Order emptyOrder = new Order("EMPTY", new OrderItem[0]);
        assertThrows(AppException.class, () -> {
            processor.process(emptyOrder, new CardPayment());
        });
    }

    @Test
    void shouldThrowExceptionWhenReturningFromNewStatus() {
        Order order = new Order("NEW_ORDER", new OrderItem[]{new OrderItem("Pen", 1)});
        // Initial status is NEW, not DELIVERED
        assertThrows(IllegalStateException.class, order::markAsReturned);
    }

    // --- Parameterized Tests (Requirement: Minimum 1) ---

    @ParameterizedTest
    @ValueSource(doubles = {100.0, 200.0, 299.99})
    void shouldRejectSmallPayPalPayments(double amount) {
        // P3: PayPal minimum is 300
        PayPalPayment paypal = new PayPalPayment();
        assertThrows(IllegalArgumentException.class, () -> paypal.pay(amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    void shouldAcceptValidQuantities(int quantity) {
        // R2: Validation 1..5 items
        assertDoesNotThrow(() -> new OrderItem("TestItem", quantity));
    }
}
