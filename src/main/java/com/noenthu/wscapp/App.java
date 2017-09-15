package com.noenthu.wscapp;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.*;
import com.sforce.ws.*;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            ConnectorConfig config = new ConnectorConfig();

            config.setUsername("yourusernamehere");
            config.setPassword("yourpasswordhere");

            PartnerConnection connection = Connector.newConnection(config);
            SObject account = new SObject();
            account.setType("Account");
            account.setField("Name", "My Test Account from Java");
            connection.create(new SObject[]{account});

        } catch (ConnectionException el) {
            el.printStackTrace();
        }


    }
}
