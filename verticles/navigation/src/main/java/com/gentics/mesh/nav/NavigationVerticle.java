package com.gentics.mesh.nav;

import static io.vertx.core.http.HttpMethod.GET;

import org.jacpfx.vertx.spring.SpringVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gentics.mesh.core.AbstractProjectRestVerticle;
import com.gentics.mesh.nav.model.NavigationRequestHandler;

@Component
@Scope("singleton")
@SpringVerticle
public class NavigationVerticle extends AbstractProjectRestVerticle {

	@Autowired
	private NavigationRequestHandler handler;

	public NavigationVerticle() {
		super("navigation");
	}

	@Override
	public void registerEndPoints() throws Exception {
		addNavigationHandler();
	}

	private void addNavigationHandler() {
		route().method(GET).handler(handler);
	}

}
