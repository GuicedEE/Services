open module org.primefaces.extensions {
	requires primefaces;
	requires org.json;

	exports org.primefaces.extensions.application;
	exports org.primefaces.extensions.behavior.javascript;

	exports org.primefaces.extensions.component.badge;
	exports org.primefaces.extensions.component.base;
	exports org.primefaces.extensions.component.blockui;
	exports org.primefaces.extensions.component.calculator;
	exports org.primefaces.extensions.component.ckeditor;
	exports org.primefaces.extensions.component.clipboard;
	exports org.primefaces.extensions.component.codemirror;
	exports org.primefaces.extensions.component.codescanner;
	exports org.primefaces.extensions.component.counter;
	exports org.primefaces.extensions.component.creditcard;
	exports org.primefaces.extensions.component.documentviewer;
	exports org.primefaces.extensions.component.dynaform;
	exports org.primefaces.extensions.component.exporter;
	exports org.primefaces.extensions.component.fab;
	exports org.primefaces.extensions.component.fluidgrid;
	exports org.primefaces.extensions.component.fuzzysearch;
	exports org.primefaces.extensions.component.gchart;
	exports org.primefaces.extensions.component.gravatar;
	exports org.primefaces.extensions.component.head;
	exports org.primefaces.extensions.component.imageareaselect;
	exports org.primefaces.extensions.component.imagerotateandresize;
	exports org.primefaces.extensions.component.inputphone;
	exports org.primefaces.extensions.component.layout;
	exports org.primefaces.extensions.component.legend;
	exports org.primefaces.extensions.component.letteravatar;
	exports org.primefaces.extensions.component.masterdetail;
	exports org.primefaces.extensions.component.orgchart;
	exports org.primefaces.extensions.component.parameters;
	exports org.primefaces.extensions.component.qrcode;
	exports org.primefaces.extensions.component.remotecommand;
	exports org.primefaces.extensions.component.sheet;
	exports org.primefaces.extensions.component.slideout;
	exports org.primefaces.extensions.component.social;
	exports org.primefaces.extensions.component.speedtest;
	exports org.primefaces.extensions.component.switchcase;
	exports org.primefaces.extensions.component.timeago;
	exports org.primefaces.extensions.component.timepicker;
	exports org.primefaces.extensions.component.timer;
	exports org.primefaces.extensions.component.tooltip;
	exports org.primefaces.extensions.component.tristatemanycheckbox;
	exports org.primefaces.extensions.component.waypoint;

	exports org.primefaces.extensions.config;
	exports org.primefaces.extensions.converter;
	exports org.primefaces.extensions.event;

	exports org.primefaces.extensions.model.codescanner;
	exports org.primefaces.extensions.model.common;
	exports org.primefaces.extensions.model.dynaform;
	exports org.primefaces.extensions.model.fluidgrid;
	exports org.primefaces.extensions.model.inputphone;
	exports org.primefaces.extensions.model.layout;
	exports org.primefaces.extensions.model.sheet;


	exports org.primefaces.extensions.component.gchart.model;


	exports org.primefaces.extensions.renderer;
//	exports org.primefaces.extensions.renderkit.layout;

	exports org.primefaces.extensions.util;
	exports org.primefaces.extensions.util.json;
	exports org.primefaces.extensions.util.visitcallback;

	uses org.primefaces.extensions.component.exporter.ExporterFactory;

	requires com.google.gson;

	provides com.guicedee.guicedinjection.interfaces.IGuiceScanModuleInclusions with org.primefaces.extensions.implementations.PrimeFacesExtensionsModuleInclusion;
}