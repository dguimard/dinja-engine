/*
 * Copyright © 2011 Ejwa Software. All rights reserved.
 *
 * This file is part of Dinja Engine. Dinja Engine is a OpenGLES2
 * 3D engine with physics support developed for the Android platform.
 *
 * Dinja Engine is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Dinja Engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Dinja Engine. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.ejwa.dinja.engine.model.file.x3d;

import com.ejwa.dinja.engine.util.StringConverter;
import javax.vecmath.Color3f;
import org.simpleframework.xml.Attribute;

public class Background extends BaseDef {
	@Attribute
	private String groundColor;

	@Attribute
	private String skyColor;

	public String getGroundColor() {
		return groundColor;
	}

	public Color3f getGroundColorVector() {
		return new Color3f(StringConverter.getVector3FromString(groundColor, " "));
	}

	public void setGroundColor(String groundColor) {
		this.groundColor = groundColor;
	}

	public String getSkyColor() {
		return skyColor;
	}

	public Color3f getSkyColorVector() {
		return new Color3f(StringConverter.getVector3FromString(skyColor, " "));
	}

	public void setSkyColor(String skyColor) {
		this.skyColor = skyColor;
	}
}