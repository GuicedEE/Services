module java.transaction {
	requires java.rmi;
	requires java.transaction.xa;

	exports javax.transaction;
}
