module org.mongodb.bson.record.codec {
	// RecordCodecProvider's public API returns org.bson.codecs.Codec, so bson is part of
	// this module's exported surface and must be transitive.
	requires transitive org.mongodb.bson;

	exports org.bson.codecs.record;
}

