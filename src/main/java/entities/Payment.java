package entities;

public class Payment {
    public int id;
    public long cardNumber;
    public PaymentType paymentType;
    public int amount;
    public long destination;

    public Payment() {
        this.id = 0;
        this.destination = 0L;
    }

    public Payment(long cardNumber, PaymentType paymentType, int amount, int destination) {
        this.cardNumber = cardNumber;
        this.paymentType = paymentType;
        this.amount = amount;
        this.destination = destination;
    }

    public Payment(int id, long cardNumber, PaymentType paymentType, int amount, long destination) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.paymentType = paymentType;
        this.amount = amount;
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (id != payment.id) return false;
        if (cardNumber != payment.cardNumber) return false;
        if (amount != payment.amount) return false;
        if (destination != payment.destination) return false;
        return paymentType == payment.paymentType;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (cardNumber ^ (cardNumber >>> 32));
        result = 31 * result + paymentType.hashCode();
        result = 31 * result + amount;
        result = 31 * result + (int) (destination ^ (destination >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Payment [" +
                "paymentId=" + id +
                ", cardNumber=" + cardNumber +
                ", paymentType=" + paymentType +
                ", amount=" + amount +
                ", destination=" + destination +
                ']';
    }
}