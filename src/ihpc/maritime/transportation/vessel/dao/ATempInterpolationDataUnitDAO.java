package ihpc.maritime.transportation.vessel.dao;

import ihpc.maritime.transportation.util.HibernateUtil;
import ihpc.maritime.transportation.vessel.entity.ATempInterpolationDataUnit;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class ATempInterpolationDataUnitDAO {
	
	public void saveToDB(ArrayList<ATempInterpolationDataUnit> aisData) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			final Transaction transaction = session.beginTransaction();
			try {
				for (ATempInterpolationDataUnit ais : aisData) {
					session.save(ais);
				}
				transaction.commit();
			} catch (Exception ex) {
				transaction.rollback();
				throw ex;
			}
		} finally {
			session.close();
		}
	}
	
	public List<ATempInterpolationDataUnit> queryAll() {

		String hql = "from ATempInterpolationDataUnit";
		List<ATempInterpolationDataUnit> list = exeQuery(hql);
		return list;

	}
	
	public List<ATempInterpolationDataUnit> queryDataById(String vslId) {

		String hql = "FROM ATempInterpolationDataUnit AS t where t.vslId=" + "'" + vslId + "'"+" ORDER BY t.timestamp ASC";
		List<ATempInterpolationDataUnit> list = exeQuery(hql);
		return list;

	}
	
	
	public List<ATempInterpolationDataUnit> queryDataByIdTimestamp(String vslId, long timestamp) {

		String hql = "FROM ATempInterpolationDataUnit AS t where t.vslId=" + "'" + vslId + "'"+" and t.timestamp="+ "'" + timestamp + "'";
		List<ATempInterpolationDataUnit> list = exeQuery(hql);
		return list;

	}
	
	public List<ATempInterpolationDataUnit> queryDataByTimestamp(long timestamp) {

		String hql = "FROM ATempInterpolationDataUnit AS t where t.timestamp="+ "'" + timestamp + "'";
		List<ATempInterpolationDataUnit> list = exeQuery(hql);
		return list;

	}
	
	
	
	public List<ATempInterpolationDataUnit> queryDataByIdNum(String vslId, int num,
			long timestamp) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ATempInterpolationDataUnit> list = null;
		try {
			final Transaction transaction = session.beginTransaction();
			try {
				list = session
						.createQuery(
								"FROM ATempInterpolationDataUnit AS t WHERE t.vslId = :vslId and t.timestamp >= :timestamp ORDER BY t.timestamp ASC")
						.setParameter("vslId", vslId)
						.setParameter("timestamp", timestamp)
						.setMaxResults(num).list();
				transaction.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return list;
	}
		
	
	public List<ATempInterpolationDataUnit> queryDataByIdTimePeriod(String vslId,
			long startTimestamp, long endTimestamp) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ATempInterpolationDataUnit> list = null;
		try {
			final Transaction transaction = session.beginTransaction();
			try {
				list = session
						.createQuery(
								"FROM ATempInterpolationDataUnit AS t WHERE t.vslId = :vslId and t.timestamp >= :start and t.timestamp <= :end order by t.timestamp asc")
						.setParameter("vslId", vslId)
						.setParameter("start", startTimestamp)
						.setParameter("end", endTimestamp).list();
				transaction.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<ATempInterpolationDataUnit> queryDataByTimePeriod(
			long startTimestamp, long endTimestamp) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ATempInterpolationDataUnit> list = null;
		try {
			final Transaction transaction = session.beginTransaction();
			try {
				list = session
						.createQuery(
								"FROM ATempInterpolationDataUnit AS t WHERE t.timestamp >= :start and t.timestamp <= :end")
						.setParameter("start", startTimestamp)
						.setParameter("end", endTimestamp).list();
				transaction.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<String> queryDistinctVslId() {

		String hql = "select distinct m.vslId from ATempInterpolationDataUnit m";
		List<String> list = exeQuery(hql);
		return list;
	}
	
	private List exeQuery(String hql) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		List list = null;
		try {
			final Transaction transaction = session.beginTransaction();
			try {
				list = session.createQuery(hql).list();
				transaction.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return list;

	}


}
