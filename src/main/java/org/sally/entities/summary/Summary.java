package org.sally.entities.summary;


public class Summary {
    private String int_order_no;
    private String prod_no;
    private String prod_name;
    private double pur_amount = 0;
    private double sales_amount = 0;
    private double result;

    public String getInt_order_no() {
        return int_order_no;
    }

    public void setInt_order_no(String int_order_no) {
        this.int_order_no = int_order_no;
    }

    public String getProd_no() {
        return prod_no;
    }

    public void setProd_no(String prod_no) {
        this.prod_no = prod_no;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public double getPur_amount() {
        return pur_amount;
    }

    public void setPur_amount(double pur_amount) {
        this.pur_amount = pur_amount;
    }

    public double getSales_amount() {
        return sales_amount;
    }

    public void setSales_amount(double sales_amount) {
        this.sales_amount = sales_amount;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
