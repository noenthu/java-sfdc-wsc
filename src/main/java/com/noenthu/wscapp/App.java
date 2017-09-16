package com.noenthu.wscapp;

import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.*;
import com.sforce.ws.*;


/**
 * Hello world!
 *
 */
public class App
{
    static final String USERNAME = "YourUserNameHere";
    static final String PASSWORD = "YourPassWordAndTokenHere";
    static PartnerConnection connection;

    public static void main( String[] args )
    {
        try {
            ConnectorConfig config = new ConnectorConfig();

            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);

            // set proxy if required
            //config.setProxy("10.10.5.18", 8080);


            // additional proxy params if required
            //config.setProxyUsername("");
            //config.setProxyPassword("");

            connection = Connector.newConnection(config);
            //SObject account = new SObject();
            //account.setType("Account");
            //account.setField("Name", "My Test Account from Java");
            //connection.create(new SObject[]{account});

            //sample methods
            createAccounts();
            createContacts();
            queryContacts();
            deleteContacts();
            deleteAccounts();

        } catch (ConnectionException el) {
            el.printStackTrace();
        }

    }

    // query contacts and display the 5 newest contacts
    private static void queryContacts() {

        try {


            String queryString = "Select Id, FirstName, LastName, " +
                "Account.Name " +
                "FROM Contact WHERE AccountId != NULL " +
                "ORDER BY CreatedDate DESC LIMIT 5";

            QueryResult queryResults = connection.query(queryString);

            if (queryResults.getSize() > 0) {
                for (SObject s: queryResults.getRecords()) {
                    System.out.println("Id: " + s.getId() + s.getField("FirstName") +
                            " " + s.getField("LastName") + " - " + s.getChild("Account").getField("Name"));
                }
            } else {
                System.out.println("No Records Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void createContacts() {

        System.out.println("Creating 5 new Contacts");

        try {

            String accountQuery = "Select Id, Name FROM Account LIMIT 1";
            String accountId;
            SObject[] records = new SObject[5];

            QueryResult accountQueryResults = connection.query(accountQuery);

            if (accountQueryResults.getSize() > 0) {
                SObject s = accountQueryResults.getRecords()[0];
                accountId = s.getId();
                System.out.println("The accound ID is " + accountId);

                for (int i=0;i<5;i++) {
                    SObject sc = new SObject();
                    sc.setType("Contact");
                    sc.setField("FirstName", "Test Contact" + i);
                    sc.setField("LastName", "Test Last" +i);
                    sc.setField("AccountId", accountId);
                    records[i] = sc;
                }

                SaveResult[] saveContacts = connection.create(records);

            } else {
                System.out.println("No account found to create contact under");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteContacts () {

        System.out.println("Deleting the 5 latest Contacts");
        String[] ids = new String[5];

        try {

            String deleteContactsString = "SELECT Id, Name FROM Contact " +
                "ORDER BY CreatedDate DESC LIMIT 5";

            QueryResult deleteContactQueryResults = connection.query(deleteContactsString);

            if (deleteContactQueryResults.getSize() > 0) {
                for (int i=0; i<deleteContactQueryResults.getRecords().length; i++) {
                    SObject sdc = deleteContactQueryResults.getRecords()[i];
                    ids[i] = sdc.getId();
                    System.out.println("Deleting Contact Id: " + sdc.getId());
                }
                DeleteResult[] deleteResults = connection.delete(ids);

                for (int i=0;i<deleteResults.length; i++) {
                    if (deleteResults[i].isSuccess()) {
                        System.out.println(i+ " Successfully Deleted Record with Id: "+
                                deleteResults[i].getId());

                    } else {
                        Error[] errors = deleteResults[i].getErrors();
                        for (int j=0; j<errors.length; j++) {
                            System.out.println("ERROR deleting record: " + errors[j].getMessage());
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAccounts() {

        System.out.println("Creating 5 Accounts");
        SObject[] records = new SObject[5];

        try {

            for (int i=0;i<5;i++) {
                SObject s = new SObject();
                s.setType("Account");
                s.setField("Name", "Test Account" + i);
                records[i] = s;
            }

            SaveResult[] saveAccounts = connection.create(records);

        } catch (Exception e ) {
            e.printStackTrace();
        }

    }

    private static void updateAccounts() {

    }

    private static void deleteAccounts() {

        System.out.println("Deleting the 5 latest Accounts");
        String[] ids = new String[5];

        try {

            String deleteAccountString = "SELECT Id, Name FROM Account " +
                "ORDER BY CreatedDate DESC LIMIT 5";

            QueryResult deleteAccountQueryResult = connection.query(deleteAccountString);

            if (deleteAccountQueryResult.getSize() > 0) {
                for (int i=0; i<deleteAccountQueryResult.getRecords().length; i++) {
                    SObject sda = deleteAccountQueryResult.getRecords()[i];
                    ids[i] = sda.getId();
                    System.out.println("Deleting Contact Id: " + sda.getId());
                }

                DeleteResult[] deleteResults = connection.delete(ids);

                for (int i=0;i<deleteResults.length; i++) {
                    if (deleteResults[i].isSuccess()) {
                        System.out.println(i+ " Successfully Deleted Record with Id: "+
                                deleteResults[i].getId());

                    } else {
                        Error[] errors = deleteResults[i].getErrors();
                        for (int j=0; j<errors.length; j++) {
                            System.out.println("ERROR deleting record: " + errors[j].getMessage());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
