package org.sally.dao;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.sally.constant.EasyTradeConstants;
import org.sally.entities.Condition;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BaseDao extends HibernateDaoSupport{
	
	@Resource
    public void setMySessionFactory(SessionFactory sessionFacotry) {  
        super.setSessionFactory(sessionFacotry);  
    } 
	/**
	 * 根据页面传过来的条件拼接HQL
	 * 
	 * @param hql 初始hql
	 * @param conditions 条件集合
	 * @return 拼接后的hql
	 */
	public String conditionalHQL(String hql,List<Condition> conditions)
	{
		String result = hql;
		// 如果条件集合不为空
		if(!conditions.isEmpty() && conditions != null)
        {
			for(Condition condition:conditions)
			{
				// 如果条件集合中,条件的值不为空,则说明此条件有效,那么就根据对应的数据进行拼接
				if(!StringUtils.isBlank(condition.getCondition_value()))
				{
					if(condition.getCondition_type().equals("string"))
					{
						result += " and "+condition.getCondition_key()+" like :"+condition.getCondition_key();
					}
					else if(condition.getCondition_type().equals("dateFrom"))
					{
						String key = condition.getCondition_key();
						String condition_column = key.substring(0,key.length()-5);
						result += " and "+condition_column+" >= str_to_date(:"+condition.getCondition_key()+",'%Y-%m-%d')";
					}
					else if(condition.getCondition_type().equals("dateTo"))
					{
						String key = condition.getCondition_key();
						String condition_column = key.substring(0,key.length()-3);
						result += " and "+condition_column+" <= str_to_date(:"+condition.getCondition_key()+",'%Y-%m-%d')+1-second(str_to_date(:"+condition.getCondition_key()+",'%Y-%m-%d'))";
					}
				}
			}
        }
		
		return result;
	}
	
	/**
	 * 根据页面传过来的条件生成Query对象
	 * 
	 * @param hql 初始hql
	 * @param conditions 条件集合
	 * @param classType 要返回的值类型class
	 * @return Query对象
	 */
	@SuppressWarnings("rawtypes")
	public Query createConditionalQuery(Session session,String hql,List<Condition> conditions,Class<?> classType)
	{
		// 根据页面传过来的条件拼接HQL
		String v_hql = conditionalHQL(hql, conditions);
		// 生成Query
		Query query = session.createQuery(v_hql, classType);
		
		if(!conditions.isEmpty() && conditions != null)
        {
    			for (Condition condition: conditions)
    			{
    				// 如果条件集合中,条件的值不为空,则说明此条件有效,那么就根据对应的数据对Query设置参数
    				if(!StringUtils.isBlank(condition.getCondition_value()))
        			{
						if(condition.getCondition_type().equals("string"))
						{
							query.setParameter(condition.getCondition_key(), "%"+condition.getCondition_value()+"%");
						}
						else if(condition.getCondition_type().equals("dateFrom") || condition.getCondition_type().equals("dateTo"))
						{
							query.setParameter(condition.getCondition_key(), condition.getCondition_value());
						}
        			}
    			}
        }
		
		return query;
	}
	
	/**
	 * 生成前缀+[系统日期年月日]+流水ID格式的单号
	 * 
	 * @param prefix 前缀
	 * @param id_column_name ID列在表中的列名
	 * @param table_name 数据库表名
	 * @param flow_length 流水ID的长度
	 * @param isNeedDate 是否需要在生成的ID中加入系统日期年月日
	 * @return 前缀+[系统日期年月日]+流水号
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String generateFlowId(Session session,String prefix,String id_column_name,String table_name,int flow_length,boolean isNeedDate)
	{
		String prefix_t = prefix.toUpperCase();
		int sysdate_length = 8;
		// 设置日期转换格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 获取系统当前日期的字符串
		String sysdate = sdf.format(new Date());
		String condition = prefix_t + sysdate;
		if(!isNeedDate)
		{
			sysdate_length = 0;
			condition = prefix_t;
		}
		//查询出表的最大ID流水号的值,例如单号是IN20171021003,则会返回3
		String sql="SELECT IFNULL(MAX(convert(substr("+id_column_name+", length('"+prefix_t+"')+1+"
		           +sysdate_length+",20),SIGNED)),0) as maxNo" + 
				   " FROM "+EasyTradeConstants.DATABASE_NAME+"."+table_name+ 
				   " WHERE  "+id_column_name+" LIKE concat('"+condition+"','%')";
		// 创建本地查询对象
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		// 设置查询列的别名和类型
		query.addScalar("maxNo", new org.hibernate.type.IntegerType());
		// 返回结果并加1
		Integer maxNo = query.uniqueResult() + 1;
		// 将最大ID流水号的值加入前导零
		String flowNo = String.format("%0"+flow_length+"d", maxNo);
		String result = "";
		// 如果需要拼接系统日期,则结果就是前缀+系统日期年月日+流水号
		if(isNeedDate)
		{
			// 拼接结果
			result = prefix_t + sysdate + flowNo;
		}
		// 否则就是前缀+流水号
		else
		{
			// 拼接结果
			result = prefix_t + flowNo;
		}
		
		return result;
		
	}
	
}
