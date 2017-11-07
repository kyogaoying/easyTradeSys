package org.sally.entities.shipment;

import org.sally.converter.DateConverterWithoutTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="INVOICE_TAB")
public class Invoice implements Serializable{
    @Id
    @Column(length = 30)
    private String invoice_no;

    @Id
    @Column(length = 30)
    private String prod_no;

    @org.hibernate.annotations.Formula("(select i.prod_name_eng from purchase_prod_tab i where i.prod_no = prod_no)")
    private String  prod_name_eng;

    @org.hibernate.annotations.Formula("(select i.prod_name from purchase_prod_tab i where i.prod_no = prod_no)")
    private String  prod_name;

    @Convert(converter = DateConverterWithoutTime.class, disableConversion = false)
    private String invoice_date;

    private int qty=0;

    @org.hibernate.annotations.Formula("sub_total_price/qty")
    private int price=0;

    private int sub_total_price=0;

    @Column(length = 30)
    private String customer_no;

    @org.hibernate.annotations.Formula("(select i.customer_name from customer_info_tab i where i.customer_no = customer_no)")
    private String customer_name;

    @org.hibernate.annotations.Formula("(select i.address from customer_info_tab i where i.customer_no = customer_no)")
    private String address;

    @org.hibernate.annotations.Formula("(select i.tel from customer_info_tab i where i.customer_no = customer_no)")
    private String tel;

    @Column(length = 30)
    private String pi_no;

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public String getProd_no() {
        return prod_no;
    }

    public int getQty() {
        return qty;
    }

    public int getPrice() {
        return price;
    }
    public String getCustomer_no() {
        return customer_no;
    }

    public String getAddress() {
        return address;
    }

    public String getTel() {
        return tel;
    }

    public int getSub_total_price() {
        return sub_total_price;
    }

    public void setSub_total_price(int sub_total_price) {
        this.sub_total_price = sub_total_price;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public void setProd_no(String prod_no) {
        this.prod_no = prod_no;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getPi_no() {
        return pi_no;
    }

    public void setPi_no(String pi_no) {
        this.pi_no = pi_no;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }
    public String getProd_name_eng() {
        return prod_name_eng;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getProd_name() {
        return prod_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invoice invoice = (Invoice) o;

        if (!invoice_no.equals(invoice.invoice_no)) return false;
        return prod_no.equals(invoice.prod_no);
    }

    @Override
    public int hashCode() {
        int result = invoice_no.hashCode();
        result = 31 * result + prod_no.hashCode();
        return result;
    }
}
