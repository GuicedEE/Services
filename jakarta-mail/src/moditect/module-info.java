module jakarta.mail {
	exports jakarta.mail;
	exports jakarta.mail.event;
	exports jakarta.mail.internet;
	exports jakarta.mail.search;
	exports jakarta.mail.util;

	exports com.sun.mail.auth;
	exports com.sun.mail.dsn;
	exports com.sun.mail.gimap;
	exports com.sun.mail.gimap.protocol;
	exports com.sun.mail.handlers;
	exports com.sun.mail.pop3;
	exports com.sun.mail.smtp;
	exports com.sun.mail.util;
	exports com.sun.mail.util.logging;
	exports com.sun.mail.iap;
	exports com.sun.mail.imap;
	exports com.sun.mail.imap.protocol;

	uses jakarta.mail.Provider;

	provides jakarta.mail.Provider with com.sun.mail.imap.IMAPProvider,
			                             com.sun.mail.imap.IMAPSSLProvider,
			                             com.sun.mail.smtp.SMTPProvider,
			                             com.sun.mail.smtp.SMTPSSLProvider,
			                             com.sun.mail.imap.IMAPProvider,
			                             com.sun.mail.imap.IMAPSSLProvider,
			                             com.sun.mail.smtp.SMTPProvider,
			                             com.sun.mail.smtp.SMTPSSLProvider,
			                             com.sun.mail.pop3.POP3Provider,
			                             com.sun.mail.pop3.POP3SSLProvider,
			                             com.sun.mail.pop3.POP3Provider,
			                             com.sun.mail.pop3.POP3SSLProvider,
			                             com.sun.mail.gimap.GmailProvider,
			                             com.sun.mail.gimap.GmailSSLProvider;

}
