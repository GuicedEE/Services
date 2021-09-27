module org.hibernate.orm.core.shade {
	requires jakarta.persistence;
	requires com.guicedee.guicedinjection;
	
	requires static org.hibernate.orm.core;
	requires org.jboss.logging;
}
