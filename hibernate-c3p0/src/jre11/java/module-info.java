module org.hibernate.orm.c3p0 {
    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.jboss.logging;

    requires static c3p0;
}