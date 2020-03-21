package dao.impl;

import dao.interfaces.CreditCardDAO;
import services.entities.CreditCard;
import services.entities.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CreditCardDAOImpl implements CreditCardDAO {

    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Simple insertion one credit card into Database using PreparedStatement.
     * Also this method updates value cardQuantity in table Clients.
     * @param card
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertCreditCard(CreditCard card) throws SQLException, ClassNotFoundException {
        conn = new MySQLConnectionFactory().createConnection();

        //Card insertion
        st = conn.prepareStatement("INSERT INTO `payments_project`.`credit_cards`" +
                "(`number`,`client_id`,`limit`,`balance`,`expiry_date`) VALUES (?, ?, ?, ?, ?);");
        st.setLong(1, card.cardNumber);
        st.setInt(2, card.clientID);
        st.setInt(3, card.creditLimit);
        st.setInt(4, card.balance);
        st.setString(5, card.expiryDate);
        int res = st.executeUpdate();

        //Changing client's cards quantity
        st = conn.prepareStatement("SELECT cards_quantity FROM payments_project.clients WHERE id = ?;");
        st.setInt(1, card.clientID);
        rs = st.executeQuery();
        int quantity = 0;
        while (rs.next()) {
            quantity = rs.getInt("cards_quantity") + 1;
        }
        st = conn.prepareStatement("UPDATE `payments_project`.`clients` SET `cards_quantity` = ? WHERE `id` = ?;");
        st.setInt(1, quantity);
        st.setInt(2 , card.clientID);
        res += st.executeUpdate();

        //Closing
        st.close();
        conn.close();
        return res;
    }

    public CreditCard retrieveCardByNumber(int number) {
        return null;
    }

    public List<CreditCard> retrieveCardsByExpiryDate(String expiryDate) {
        return null;
    }

    public List<Payment> retrievePaymentsByCreditCard(int number) {
        return null;
    }

    public int updateCardAmountByNumber(int number, int amount) {
        return 0;
    }

    public int updateCreditLimitByNumber(int number, int newLimit) {
        return 0;
    }

    public int deleteCreditCardByNumber(int number) {
        return 0;
    }
}
