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
import javax.vecmath.Vector3f;
import org.simpleframework.xml.Attribute;

public class PointLight extends BaseDef implements Transformable {
	@Attribute
	private float ambientIntensity;

	@Attribute
	private String color;

	@Attribute
	private float intensity;

	@Attribute
	private String location;

	@Attribute
	private float radius;

	public float getAmbientIntensity() {
		return ambientIntensity;
	}

	public void setAmbientIntensity(float ambientIntensity) {
		this.ambientIntensity = ambientIntensity;
	}

	public String getColor() {
		return color;
	}

	public Color3f getColorVector() {
		return new Color3f(StringConverter.getVector3FromString(color, " "));
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public String getLocation() {
		return location;
	}

	public Vector3f getLocationVector() {
		return StringConverter.getVector3FromString(location, " ");
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}