package com.demo.steel.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.demo.steel.domain.SteelHeatNo;
import com.demo.steel.domain.SteelOrder;

@Repository
public class SteelOrderDao extends GenericDao<SteelOrder, String> {

	@Override
	public Class<SteelOrder> getClazz() {
		return SteelOrder.class;
	}
	
	public List<SteelOrder> getMinimalOrder(){
		String[] columns = new String[]{"id", "supplier", "date", "status","poNumber"};
		return getMinimalPresentation(columns);
	}

	public List<SteelOrder> getSteelOrderForStatus(SteelOrder.Status status){
		List<SteelOrder> orders = getAllEqualTo(new String[]{"status"}, new Object[]{status});
		for(SteelOrder order : orders){
			order.getPartManifacturingDetails().size();
			order.getVerificationCheck().size();
		}
		return orders;
	}
	
	public float getSumOfTotalSteelTonageForHeatNo(SteelHeatNo no){
		
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SteelOrder.class);
		criteria
		.setProjection(Projections.sum("steelTonage"))
		.add(Restrictions.eq("steelHeatNumber",no.getHeatNo()));
		
		float result = (float)((double)criteria.uniqueResult());
		return result;
	}
	
}
