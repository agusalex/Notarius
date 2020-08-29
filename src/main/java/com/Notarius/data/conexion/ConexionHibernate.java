package com.Notarius.data.conexion;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class ConexionHibernate {
	private static Configuration configuration = new Configuration();
	private static SessionFactory sf=null;
	static boolean  debugDB=true;
	
	
	private static Configuration getConfiguration() {
		//TODO ver como inyectar esos annotated class o esta seria la forma correcta?

		if(System.getProperty("os.name").contains("Windows")) {
			System.out.println("Debug DB");
			debugDB = true;
		}

       configuration

		 		.configure()
				.setProperty(Environment.DIALECT, "org.hibernate.dialect.MariaDB53Dialect")
				.setProperty(Environment.DRIVER, "org.mariadb.jdbc.Driver")
				.setProperty(Environment.URL, "jdbc:mariadb://192.168.1.10:3306/Notarius")
				.setProperty(Environment.USER, "root")
				.setProperty(Environment.PASS, "root")
				.setProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS, "false");

       if(debugDB){
		   configuration.configure()
				   .setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
				   .setProperty(Environment.DRIVER, "org.h2.Driver")
				   .setProperty(Environment.URL, "jdbc:h2:~//Notariu;AUTO_SERVER=TRUE")
				   .setProperty(Environment.USER, "root")
				   .setProperty(Environment.PASS, "root")
				   .setProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS, "false");
	   }
		return configuration;
	}
	
	private static ServiceRegistry getServiceRegistry(Configuration configuration) {
		ServiceRegistry serviceRegistry=null;
		try {
			
			 serviceRegistry = new StandardServiceRegistryBuilder()
					 				.applySettings(configuration.getProperties())
					 				.configure()
					 				.build();
			
		} catch (Exception e) {
			System.err.println("Error al conectar: ");
			e.printStackTrace();
		}
		return serviceRegistry;
	}
	
	private static SessionFactory getSession() {
		if(sf==null) {
			try {
				sf=getConfiguration().buildSessionFactory(getServiceRegistry(getConfiguration()));
				System.out.println("Conexion exitosa");
				System.out.println("----------------");
			} catch (Exception e) {
				System.err.println("Error!!");
				e.printStackTrace();
			}
		}
		return sf;
	}

	public static Session openSession() {
		return getSession().openSession();
	}
	
	public static void close() {
		if(sf!= null) {
			sf.close();
		}
	}
	
}
