package services.entities;

public class Client {
    public int id;
    public String name;
    public String birthDay;
    public int cardsQuantity = 0;

    public Client() {
    }

    public Client(String name, String birthDay) {
        this.name = name;
        this.birthDay = birthDay;
    }

    public Client(int id, String name, String birthDay) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
    }

    public Client(int id, String name, String birthDay, int cardsQuantity) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.cardsQuantity = cardsQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (cardsQuantity != client.cardsQuantity) return false;
        if (!name.equals(client.name)) return false;
        return birthDay.equals(client.birthDay);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + birthDay.hashCode();
        result = 31 * result + cardsQuantity;
        return result;
    }

    @Override
    public String toString() {
        return "Client [id = " + id + ", name = " + name +
                ", birthday = " + birthDay +
                ", cards quantity = " + cardsQuantity + "]";
    }
}