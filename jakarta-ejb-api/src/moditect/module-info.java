module jakarta.ejb {
	requires java.rmi;

	requires java.transaction;

	requires java.xml;
	requires java.naming;

	exports jakarta.ejb;
	exports jakarta.ejb.embeddable;
	exports jakarta.ejb.spi;

	uses jakarta.ejb.spi.EJBContainerProvider;
	uses jakarta.ejb.EJBContext;
}
