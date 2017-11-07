package org.sally.entities.shipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PACKING_LIST_TAB")
public class PackingList implements Serializable{
    //要存到表里的
    @Id
    @Column(length = 30)
    private String packing_list_no;
    @Column(length = 20)
    private String packing_list_date;
    @Column(length = 20)
    private String customer_no;
    @Id
    @Column(length = 20)
    private String prod_no;
    private double gross_weight=13.5;
    private double volumn=2.5;
    @Column(length = 20)
    private String pi_no;
    private int qty=0;


    //不需要存的
    @org.hibernate.annotations.Formula("(select i.customer_name from customer_info_tab i where i.customer_no = customer_no)")
    private String customer_name;

    @org.hibernate.annotations.Formula("(select i.address from customer_info_tab i where i.customer_no = customer_no)")
    private String address;

    @org.hibernate.annotations.Formula("(select i.tel from customer_info_tab i where i.customer_no = customer_no)")
    private String tel;

    @org.hibernate.annotations.Formula("(select i.prod_name_eng from purchase_prod_tab i where i.prod_no = prod_no)")
    private String prod_name_eng;

    @org.hibernate.annotations.Formula("(select i.prod_name from purchase_prod_tab i where i.prod_no = prod_no)")
    private String prod_name;

    @org.hibernate.annotations.Formula("gross_weight*qty")
    private double ttl_gross_weight;

    @org.hibernate.annotations.Formula("volumn*qty")
    private double ttl_volumn;


    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPacking_list_no() {
        return packing_list_no;
    }

    public void setPacking_list_no(String packing_list_no) {
        this.packing_list_no = packing_list_no;
    }

    public String getPacking_list_date() {
        return packing_list_date;
    }

    public void setPacking_list_date(String packing_list_date) {
        this.packing_list_date = packing_list_date;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getProd_no() {
        return prod_no;
    }

    public void setProd_no(String prod_no) {
        this.prod_no = prod_no;
    }

    public double getGross_weight() {
        return gross_weight;
    }

    public void setGross_weight(double gross_weight) {
        this.gross_weight = gross_weight;
    }

    public double getVolumn() {
        return volumn;
    }

    public void setVolumn(double volumn) {
        this.volumn = volumn;
    }

    public String getPi_no() {
        return pi_no;
    }

    public void setPi_no(String pi_no) {
        this.pi_no = pi_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getAddress() {
        return address;
    }

    public String getProd_name_eng() {
        return prod_name_eng;
    }

    public String getProd_name() {
        return prod_name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getTel() {
        return tel;
    }

    public double getTtl_gross_weight() {
        return ttl_gross_weight;
    }

    public double getTtl_volumn() {
        return ttl_volumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackingList that = (PackingList) o;

        if (!packing_list_no.equals(that.packing_list_no)) return false;
        return prod_no.equals(that.prod_no);
    }

    @Override
    public int hashCode() {
        int result = packing_list_no.hashCode();
        result = 31 * result + prod_no.hashCode();
        return result;
    }
}

