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
	
	
	
	private static Configuration getConfiguration() {
		//TODO ver como inyectar esos annotated class o esta seria la forma correcta?
		//XXX Agregué un xml donde poner los mappings de las clases para solo cargar
		//XXX Por properties los datos de conexión.
		//En caso de que sea correcto, por cada dao deberiamos hacer un .addAnnotatedClass(DTO.class)
		//Existe un metodo .setProperties para Configuration
		//La ultima property es para que no crashee el add lo saque de aca:
		// https://stackoverflow.com/questions/32968527/hibernate-sequence-doesnt-exist
		//PD Misael estaria orgulloso :)






		 configuration
		 		.configure()
				.setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
				.setProperty(Environment.DRIVER, "org.h2.Driver")
				.setProperty(Environment.URL, "jdbc:h2:~//Notariu;AUTO_SERVER=TRUE")
				.setProperty(Environment.USER, "root")
				.setProperty(Environment.PASS, "root")
				.setProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS, "false");
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
