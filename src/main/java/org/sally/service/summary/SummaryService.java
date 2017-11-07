package org.sally.service.summary;

import org.sally.dao.summary.SummaryDao;
import org.sally.entities.summary.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryService {
	@Autowired
	private SummaryDao summaryDao;

	public List<Summary> findAll()
	{
		List<Object[]> objs = summaryDao.findAll();
		List<Summary> summaries = new ArrayList<Summary>();
		for(Object[] o : objs)
		{
			Summary summary = new Summary();
			summary.setInt_order_no(String.valueOf(o[0]));
			summary.setProd_no(String.valueOf(o[1]));
			summary.setProd_name(String.valueOf(o[2]));
			summary.setSales_amount(((BigDecimal)o[3]).doubleValue());
			summary.setPur_amount((Double)o[4]);
			summary.setResult((Double)o[5]);

			summaries.add(summary);
		}
		return summaries;
	}

}
