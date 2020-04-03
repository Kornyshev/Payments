# Payments

This is just a learning project to learning basics of JDBC, Unit testing using TestNG and Mockito. Also I hope that this project was made accordingly with common Code Conventions.

About project: I've simulated bank's process - Database with clients, credit cards and payments. There is DAO layer with connectivity to DB and with particular methods to handle different queries; Business Services - layer with simple business methods, which use DAO to represent information, sort it or calculate something, those methods are rather simple and very similar with DAO methods, because it's simple project without a planty scenarios in bank sphere. Also there is simple View layer - console interaction.

DAO layer:
https://github.com/Kornyshev/Payments/blob/master/src/main/resources/screenshots/dao_layer.JPG

Entities and services:
https://github.com/Kornyshev/Payments/blob/master/src/main/resources/screenshots/entities_and_services.JPG

Tests:
https://github.com/Kornyshev/Payments/blob/master/src/main/resources/screenshots/tests.JPG

TestNG.xml:
https://github.com/Kornyshev/Payments/blob/master/src/main/resources/screenshots/common_test_suite.JPG

EmailableReporter from TestNG:
https://github.com/Kornyshev/Payments/blob/master/src/main/resources/screenshots/report_testng.JPG