package services.entities;

public class CreditCard {
    public long cardNumber;
    public int clientID;
    public int creditLimit;
    public int balance;
    public String expiryDate;

    public CreditCard() {
    }

    public CreditCard(long cardNumber, int clientID, int creditLimit, int balance, String expiryDate) {
        this.cardNumber = cardNumber;
        this.clientID = clientID;
        this.creditLimit = creditLimit;
        this.balance = balance;
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditCard that = (CreditCard) o;

        if (cardNumber != that.cardNumber) return false;
        if (clientID != that.clientID) return false;
        if (creditLimit != that.creditLimit) return false;
        if (balance != that.balance) return false;
        return expiryDate.equals(that.expiryDate);
    }

    @Override
    public int hashCode() {
        int result = (int) (cardNumber ^ (cardNumber >>> 32));
        result = 31 * result + clientID;
        result = 31 * result + creditLimit;
        result = 31 * result + balance;
        result = 31 * result + expiryDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CreditCard [number = " + cardNumber + ", client = " + clientID +
                ", limit = " + creditLimit + ", balance = " + balance +
                ", expiry date = " + expiryDate + "]";
    }
}
