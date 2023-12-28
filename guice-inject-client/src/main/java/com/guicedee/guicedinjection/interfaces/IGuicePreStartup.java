/*
 * Copyright (C) 2017 GedMarc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.guicedee.guicedinjection.interfaces;

import com.guicedee.guicedinjection.interfaces.annotations.INotInjectable;

/**
 * Initializes before Guice has been injected
 *
 * @author GedMarc
 * @since 15 May 2017
 */
@INotInjectable
public interface IGuicePreStartup<J extends IGuicePreStartup<J>>
		extends IDefaultService<J> {

	/**
	 * Runs on startup
	 */
	void onStartup();

	/**
	 * Sort order for startup, Default 100.
	 *
	 * @return the sort order never null
	 */
	@Override
	default Integer sortOrder() {
		return 100;
	}


}
