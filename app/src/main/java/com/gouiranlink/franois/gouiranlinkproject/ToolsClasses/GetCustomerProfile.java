package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses;

import android.util.Log;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;

import java.util.concurrent.ExecutionException;

public class GetCustomerProfile {

    public GetCustomerProfile() {
    }

    public Customer getCustomerProfile(String accessToken) {
        Customer customer = null;
        String resp = null;
        try {
            GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/user/", "Authorization", "Token " + accessToken);
            resp = getRequest.execute().get();
            Log.d("GETREQUESTRESP", resp);
            RetrieveCustomerInformationFromRequest retrieveCustomerInformationFromRequest = new RetrieveCustomerInformationFromRequest(resp, accessToken);
            customer = retrieveCustomerInformationFromRequest.generateCustomer();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return (customer);
    }

    public Customer getCustomerProfile(String accessToken, Customer oldCustomer) {
        Customer customer = null;
        String resp = null;
        try {
            GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/authentication/user/", "Authorization", "Token " + accessToken);
            resp = getRequest.execute().get();
            Log.d("GETREQUESTRESP", resp);
            RetrieveCustomerInformationFromRequest retrieveCustomerInformationFromRequest = new RetrieveCustomerInformationFromRequest(resp, accessToken);
            customer = retrieveCustomerInformationFromRequest.generateCustomer();
            customer.setmFacebook(oldCustomer.ismFacebook());
            customer.setmGoogle(oldCustomer.ismGoogle());
            customer.setmGouiranLink(oldCustomer.ismGouiranLink());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return (customer);
    }
}
