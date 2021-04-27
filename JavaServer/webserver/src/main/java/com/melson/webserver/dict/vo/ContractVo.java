package com.melson.webserver.dict.vo;

import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.DeliverAddress;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.TaxRate;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public class ContractVo {
    private Customer customer;
    private List<TaxRate> taxRates;
    private List<DeliverAddress> deliverAddresses;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<TaxRate> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(List<TaxRate> taxRates) {
        this.taxRates = taxRates;
    }

    public List<DeliverAddress> getDeliverAddresses() {
        return deliverAddresses;
    }

    public void setDeliverAddresses(List<DeliverAddress> deliverAddresses) {
        this.deliverAddresses = deliverAddresses;
    }
}
