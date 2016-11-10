package com.demo.steel.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.demo.steel.domain.SteelHeatNo;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelOrder.State;
import com.demo.steel.domain.Supplier;

@Repository
public class SteelOrderDao extends GenericDao<SteelOrder, String> {

	private final String[] PROXY_PRESENTATION = new String[] { "id", "supplier", "date", "state", "poNumber" };
	
	private static final Logger logger = LoggerFactory.getLogger(SteelOrderDao.class);
	@Override
	public SteelOrder get(String key) {
		SteelOrder order = super.get(key);
		int size = order.getPartManifacturingDetails().size();
		logger.debug(size + " part manifacturing details found.");
		size = order.getVerificationChecks().size();
		logger.debug(size + " verifiction checks found.");
		order.getDeviation().getId();
		return order;
	}

	@Override
	public Class<SteelOrder> getClazz() {
		return SteelOrder.class;
	}

	public List<SteelOrder> getOrderProxies() {
		return getMinimalPresentation(PROXY_PRESENTATION);
	}

	public List<SteelOrder> filterBy(State status) {
		List<SteelOrder> orders = getAllEqualTo(new String[]{"state"},new Object[]{status},PROXY_PRESENTATION);
		return orders;
	}

	public List<SteelOrder> filterBy(Supplier supplier) {
		List<SteelOrder> orders= getAllEqualTo(new String[]{"supplier"}, new Object[]{supplier},PROXY_PRESENTATION);
		return orders;
	}

	public float getSumOfTotalSteelTonageForHeatNo(SteelHeatNo no) {

		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SteelOrder.class);
		criteria.setProjection(Projections.sum("steelTonage"))
		.add(Restrictions.eq("steelHeatNumber", no.getHeatNo()));
		float result = (float) ((double) criteria.uniqueResult());
		return result;
	}

	public List<SteelOrder> filterBy(Supplier supplier, State state) {
		List<SteelOrder> orders= getAllEqualTo(new String[]{"supplier","state"}, new Object[]{supplier, state},PROXY_PRESENTATION);
		return orders;
	}

}
