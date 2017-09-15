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
    static final String USERNAME = "YourUserNameHere";
    static final String PASSWORD = "YourPassWordAndTokenHere";

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
