module microprofile.jwt.auth.api {
	requires transitive jakarta.json;
	requires transitive jakarta.inject;

	exports org.eclipse.microprofile.auth;
	exports org.eclipse.microprofile.jwt;
	exports org.eclipse.microprofile.jwt.config;
}

