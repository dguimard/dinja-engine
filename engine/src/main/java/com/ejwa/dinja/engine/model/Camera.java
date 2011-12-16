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
package com.ejwa.dinja.engine.model;

import android.opengl.Matrix;
import javax.vecmath.Matrix4f;

public class Camera {
	private static final float DEFAULT_ASPECT_RATIO = 16 / 9.6f;
	private static final float DEFAULT_FIELD_OF_VIEW = 45;

	private final Matrix4f projectionMatrix = new Matrix4f();
	private boolean recalculateProjectionMatrix = true;

	private float aspectRatio = DEFAULT_ASPECT_RATIO;
	private float fieldOfView = DEFAULT_FIELD_OF_VIEW;
	private float farPlane = 100;
	private float nearPlane = 0.001f;

	public Matrix4f getProjectionMatrix() {
		if (recalculateProjectionMatrix) {
			final float size = nearPlane * (float) Math.tan((Math.toRadians(fieldOfView)) / 2.0);
			final float projectionMatrixValues[] = new float[16];

			synchronized (this) {
				Matrix.frustumM(projectionMatrixValues, 0, -size, size, -size / aspectRatio, size / aspectRatio, nearPlane, farPlane);
			}

			projectionMatrix.set(projectionMatrixValues);
			recalculateProjectionMatrix = false;
		}

		return projectionMatrix;
	}

	public void setAspectRatio(float aspectRatio) {
		synchronized (this) {
			this.aspectRatio = aspectRatio;
		}

		recalculateProjectionMatrix = true;
	}

	public synchronized float getAspectRatio() {
		return aspectRatio;
	}

	public void setFieldOfView(float fieldOfView) {
		this.fieldOfView = fieldOfView;
		recalculateProjectionMatrix = true;
	}

	public float getFieldOfView() {
		return fieldOfView;
	}

	public float getFarPlane() {
		return farPlane;
	}

	public void setFarPlane(float farPlane) {
		this.farPlane = farPlane;
		recalculateProjectionMatrix = true;
	}

	public float getNearPlane() {
		return nearPlane;
	}

	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
		recalculateProjectionMatrix = true;
	}
}