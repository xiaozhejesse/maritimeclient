package ihpc.maritime.transportation.util;

import ihpc.maritime.transportation.vessel.entity.ATempInterpolationDataUnit;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * 
 * To create singleton sessionFactory
 * 
 * @author Xiao Zhe
 *
 */
public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {

			Configuration cf = new Configuration()
					.configure("hibernate.cfg.xml");
			cf.addAnnotatedClass(ATempInterpolationDataUnit.class);
			StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder();
			srb.applySettings(cf.getProperties());
			ServiceRegistry sr = srb.build();
			SessionFactory sf = cf.buildSessionFactory(sr);
			return sf;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
