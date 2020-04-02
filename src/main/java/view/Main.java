package view;

import dao.impl.*;
import entities.Client;
import entities.Card;
import entities.Payment;
import entities.PaymentType;
import org.apache.log4j.Logger;
import services.actions.CloseCardByNumber;
import services.actions.MakePaymentByCard;
import services.actions.OpenNewCardToClient;
import services.actions.UpdateLimitOnCard;
import services.reports.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws SQLException, LoginToMySQLException, ClassNotFoundException {
        hintsForCLI();
        String query = sc.nextLine();
        while (!query.equals("exit")) {
            executeQuery(query);
            hintsForCLI();
            query = sc.nextLine();
        }
        sc.close();
    }

    private static void hintsForCLI() {
        System.out.println("Be accurate in your queries!");
        System.out.println("Options for the main menu: 'report', 'action', 'exit'");
        System.out.println("Options for the report menu: 'all tables', 'cards by name', 'cards with max debt', " +
                "'payments by card', 'payments by name', 'expired cards', 'soon expire cards'");
        System.out.println("Options for the action menu: 'close card by number', " +
                "'make payment', 'open card', 'update card limit'");
    }

    private static void executeQuery(String query) throws SQLException, LoginToMySQLException, ClassNotFoundException {
        if (query.equals("report")) {
            handlingReportQuery(sc.nextLine());
        } else if (query.equals("action")) {
            handlingActionQuery(sc.nextLine());
        } else {
            System.err.println("Unknown command!");
        }
    }

    private static void handlingReportQuery(String query) {
        TablePrinter<Card> cardTablePrinter = new TablePrinter<>();
        TablePrinter<Payment> paymentTablePrinter = new TablePrinter<>();
        ReportCardsByClientName cardsByClientNameReport = new ReportCardsByClientName();
        ReportCardsWithMaxDebt cardsWithMaxDebtReport = new ReportCardsWithMaxDebt();
        ReportPaymentsByCard paymentsByCardReport = new ReportPaymentsByCard();
        ReportPaymentsByClientName paymentsByNameReport = new ReportPaymentsByClientName();
        ReportsAboutExpiryDate expiryDateReport = new ReportsAboutExpiryDate();
        switch (query) {

            case "all tables":
                System.out.println("Options: 'clients', 'cards', 'payments'");
                System.out.println("Type the entity what you want to see:");
                String type = sc.nextLine();
                if (type.equals("payments")) {
                    System.out.println("List of payments is too long. I'll print just 100 for testing View layer.");
                }
                try {
                    printTable(type);
                    logger.info("Printing of '" + type + "'.");
                } catch (SQLException | LoginToMySQLException | ClassNotFoundException e) {
                    logger.error("Something wrong with printing " + type + " table", e);
                    e.printStackTrace();
                }
                break;

            case "cards by name":
                System.out.println("Type the client's name:");
                String name = sc.nextLine();
                List<Card> cardsOfClient = cardsByClientNameReport.getReport(name);
                if (cardsOfClient.isEmpty()) {
                    System.err.println("List of credit cards is empty! Something wrong!");
                    logger.error("Something wrong with printing cards of client = " + name);
                } else {
                    cardTablePrinter.print(cardsOfClient);
                    logger.info("All cards of client " + name + " are printed");
                }
                break;

            case "cards with max debt":
                System.out.println("Ok, one second...");
                List<Card> cardsMaxDebt = cardsWithMaxDebtReport.getReport();
                if (cardsMaxDebt.isEmpty()) {
                    System.err.println("List of credit cards is empty! Something wrong!");
                    logger.error("Something wrong with printing cards with max debt");
                } else {
                    cardTablePrinter.print(cardsMaxDebt);
                    logger.info("Cards with max debt are printed");
                }
                break;

            case "payments by card":
                System.out.println("Type the card's number (it must be Long variable):");
                String number = sc.nextLine();
                long num;
                try {
                    num = Long.parseLong(number);
                } catch (NumberFormatException e) {
                    System.err.println("You've made a mistake. Let start from the beginning");
                    e.printStackTrace();
                    break;
                }
                List<Payment> paymentsByNumber = paymentsByCardReport.getReport(num);
                if (paymentsByNumber.isEmpty()) {
                    System.err.println("List of credit cards is empty! Something wrong!");
                    logger.error("Something wrong with printing payments by the card with number " + number);
                } else {
                    paymentTablePrinter.print(paymentsByNumber);
                    logger.info("Payments by the card with number " + number + " are printed");
                }
                break;

            case "payments by name":
                System.out.println("Type the client's name:");
                String nameForPayments = sc.nextLine();
                List<Payment> paymentsByName = paymentsByNameReport.getReport(nameForPayments);
                if (paymentsByName.isEmpty()) {
                    System.err.println("List of credit cards is empty! Something wrong!");
                    logger.error("Something wrong with printing payments " +
                            "by the client's name " + nameForPayments);
                } else {
                    paymentTablePrinter.print(paymentsByName);
                    logger.info("Payments of client with name " + nameForPayments + " are printed");
                }
                break;

            case "expired cards":
                System.out.println("Ok, one second...");
                List<Card> expiredCards = expiryDateReport.getAlreadyExpiredCards();
                if (expiredCards.isEmpty()) {
                    System.out.println("List of expired cards is empty. Maybe all cards are ok.");
                    logger.info("List of expired cards is empty");
                } else {
                    cardTablePrinter.print(expiredCards);
                    logger.info("Table of expired cards is printed");
                }
                break;

            case "soon expire cards":
                System.out.println("Type the number of months. " +
                        "This report will give a table of cards that will expire during these months.");
                int month;
                try {
                    month = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.err.println("You've made a mistake. Let start from the beginning");
                    e.printStackTrace();
                    break;
                }
                List<Card> soonExpireCards = expiryDateReport.getSoonExpiredCards(month);
                if (soonExpireCards.isEmpty()) {
                    System.out.println("List of soon expire cards is empty. Maybe all cards are ok.");
                    logger.info("List of soon expire cards (month = " + month + ") is empty");
                } else {
                    cardTablePrinter.print(soonExpireCards);
                    logger.info("Table of soon expire cards (month = " + month + ") is printed");
                }
                break;
            default:
                System.err.println("Unknown command!");
        }
    }

    private static void handlingActionQuery(String query) throws SQLException, LoginToMySQLException, ClassNotFoundException {
        CloseCardByNumber closeCardByNumberActor = new CloseCardByNumber();
        MakePaymentByCard makePaymentByCardActor = new MakePaymentByCard();
        OpenNewCardToClient openNewCardActor = new OpenNewCardToClient();
        UpdateLimitOnCard updateLimitActor = new UpdateLimitOnCard();
        switch (query) {

            case "close card by number":
                long num1 = typingLong();
                int res = closeCardByNumberActor.closeCard(num1);
                if (res == -1) {
                    System.err.println("Something wrong with closing this card, check balance or number...");
                    logger.error("Something wrong with closing card with number = " + num1);
                } else {
                    System.out.println("Card was closed successfully. You can get " + res + "$ in cash!");
                    logger.info("Card with number " + num1 + " was closed successfully");
                }
                break;

            case "make payment":
                long newNumber = typingLong();
                System.out.println("Type the payment type (STORE, ENTERTAINMENT, GAS_STATION, TRANSFER):");
                PaymentType newType = PaymentType.valueOf(sc.nextLine());
                System.out.println("Type payment amount (it could be negative and positive):");
                int newAmount = typingInt();
                long newDestination = typingLong();
                if (makePaymentByCardActor.makePayment(newNumber, newType, newAmount, newDestination) > 0) {
                    System.out.println("Payment was created successfully.");
                    logger.info("Payment was created. Card num = " + newNumber + ", amount = " + newAmount);
                } else {
                    System.out.println("Something wrong with payment, check numbers of balances...");
                    logger.error("Something wrong with payment, check numbers of balances. " +
                            "Number = " + newNumber + ", amount = " + newAmount + ", " +
                            "destination = " + newDestination);
                }
                break;

            case "open card":
                System.out.println("Let type few parameters: int - client ID, int - limit, int - balance");
                int clientId = typingInt();
                int limit = typingInt();
                int balance = typingInt();
                if (openNewCardActor.openNewCreditCard(clientId, limit, balance) > 0) {
                    System.out.println("New card for the client with ID = " + clientId + " created");
                    logger.info("New card for the client with ID = \" + clientId + \" created");
                } else {
                    System.out.println("Something wrong with the card creating, check ID -> " + clientId);
                    logger.error("Something wrong with the card creating, check ID -> " + clientId);
                }
                break;

            case "update card limit":
                long num2 = typingLong();
                int lim = typingInt();
                if (lim == 0) {
                    System.err.println("Incorrect value of new limit. Let start from the beginning.");
                    break;
                }
                if (updateLimitActor.updateLimit(num2, lim) == 1) {
                    System.out.println("New credit limit was set up successfully!");
                    logger.info("New credit limit was set up successfully on the card with num = " + num2);
                } else {
                    System.err.println("Something wrong with the limit updating, check number...");
                    logger.error("Something wrong with the limit updating on the card with num = " + num2);
                }
                break;
            default:
                System.err.println("Unknown command!");
        }
    }

    private static long typingLong() {
        System.out.println("Type new long value:");
        String number = sc.nextLine();
        long num = 0L;
        try {
            num = Long.parseLong(number);
        } catch (NumberFormatException e) {
            System.err.println("You've made a mistake. Let start from the beginning");
            e.printStackTrace();
        }
        return num;
    }

    private static int typingInt() {
        System.out.println("Type new integer value:");
        String integer = sc.nextLine();
        int i = 0;
        try {
            i = Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            System.err.println("You've made a mistake. Let start from the beginning");
            e.printStackTrace();
        }
        return i;
    }

    private static void printTable(String type)
            throws SQLException, LoginToMySQLException, ClassNotFoundException {
        ReportsByTables reportsByTables = new ReportsByTables();
        switch (type) {
            case "clients":
                TablePrinter<Client> printer1 = new TablePrinter<>();
                printer1.print(reportsByTables.getAllClients());
                break;
            case "cards":
                TablePrinter<Card> printer2 = new TablePrinter<>();
                printer2.print(reportsByTables.getAllCards());
                break;
            case "payments":
                TablePrinter<Payment> printer3 = new TablePrinter<>();
                printer3.print(reportsByTables
                        .getAllPayments()
                        .stream()
                        .limit(100)
                        .collect(Collectors.toList()));
                break;
            default:
                System.err.println("Unknown command!");
        }
    }
}