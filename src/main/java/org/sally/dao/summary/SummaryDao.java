package org.sally.dao.summary;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.sally.dao.BaseDao;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
 * 用户管理功能的Dao层
 */
@Repository
public class SummaryDao extends BaseDao
{

	public List<Object[]> findAll()
	{
		final String sql = "select a.int_order_no,a.prod_no,a.prod_name,sales_amount * 6 sales_amount,pur_amount,(sales_amount - pur_amount) result from"+
					 "(select i.int_order_no,p.prod_no,(select pp.prod_name from purchase_prod_tab pp where pp.prod_no = p.prod_no) prod_name,"+
					 "(select sum(i.sub_total_price) from invoice_tab i where i.prod_no = p.prod_no) sales_amount,"+
				     "(select sum(pp.pur_price) from purchase_prod_tab pp where pp.prod_no = p.prod_no) pur_amount "+
					 "from purchase_order_tab p "+
					 "join int_order_tab i on p.int_order_no = i.int_order_no) a";


		List<Object[]> result = getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			public List<Object[]> doInHibernate(Session session) throws HibernateException {
				return session.createSQLQuery(sql).list();
			}
		});

		return result;
	}

}

