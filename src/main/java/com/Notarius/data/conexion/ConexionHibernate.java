package com.Notarius.data.conexion;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;


public class ConexionHibernate {
	private static Configuration configuration = new Configuration();
	private static SessionFactory sf=null;
	static boolean  debugDB= false;
	static String URL= "127.0.0.1:3306/Notarius";
	static String user= "root";
	static String pass= "root";
	
	
	private static Configuration getConfiguration() {
		//TODO ver como inyectar esos annotated class o esta seria la forma correcta?

		try {
			if (System.getenv("DEBUG") != null) {
				debugDB = System.getenv("DEBUG").equals("true");
			}
			if (System.getenv("USER") != null && System.getenv("PASS") != null) {
				user = System.getenv("USER");
				pass = System.getenv("PASS");
			}
			if (System.getenv("URL") != null) {
				URL = System.getenv("URL");
			}
		}
		catch (Exception ignored) {

		}

       configuration.configure()
				.setProperty(Environment.DIALECT, "org.hibernate.dialect.MariaDB53Dialect")
				.setProperty(Environment.DRIVER, "org.mariadb.jdbc.Driver")
				.setProperty(Environment.URL, "jdbc:mariadb://"+URL)
				.setProperty(Environment.USER, user)
				.setProperty(Environment.PASS, pass)
				.setProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS, "false");

       if(debugDB){
		   configuration.configure()
				   .setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
				   .setProperty(Environment.DRIVER, "org.h2.Driver")
				   .setProperty(Environment.URL, "jdbc:h2:~//Notarius;AUTO_SERVER=TRUE")
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
				System.err.println("Error de conexion!!");

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
