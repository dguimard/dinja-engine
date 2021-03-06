/*
 * Copyright © 2011-2012 Ejwa Software. All rights reserved.
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
package com.ejwa.dinja.demo.activity;

import android.os.Bundle;
import com.ejwa.dinja.engine.activity.DinjaActivity;
import com.ejwa.dinja.engine.controller.Controllable;
import com.ejwa.dinja.engine.controller.input.IFingerMovementInputListener;
import com.ejwa.dinja.engine.model.Camera;
import com.ejwa.dinja.engine.model.node.Scene;
import com.ejwa.dinja.engine.model.node.mesh.Mesh;
import com.ejwa.dinja.engine.model.node.mesh.geometry.Sphere;
import com.ejwa.dinja.engine.util.TextureLoader;
import com.ejwa.dinja.engine.view.DebugView;
import com.ejwa.dinja.engine.view.SceneView;
import com.ejwa.dinja.opengles.display.IFrameUpdateListener;
import org.openmali.vecmath2.Point2f;

public class DragWorldActivity extends DinjaActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Sphere sphere = new Sphere("world", 1, 12, 24);
		sphere.getMaterial().setTexture(TextureLoader.load(getAssets(), "world_map.jpg"));

		final Scene scene = new Scene(new Camera(), sphere);

		registerView(new SceneView(scene));
		registerView(new DebugView(0.25f, this, scene, sphere));
		registerController(new WorldMeshController(sphere));
	}

	private static class WorldMeshController implements Controllable, IFingerMovementInputListener, IFrameUpdateListener {
		private final Mesh mesh;
		private float rotationFromX;
		private boolean fingerDown;

		public WorldMeshController(Mesh mesh) {
			this.mesh = mesh;
		}

		@Override
		public void onFingerMovementInput(Point2f startPosition, Point2f endPosition, float angle) {
			rotationFromX = (endPosition.getX() - startPosition.getX()) / 100;
			fingerDown = true;
		}

		@Override
		public void onFingerMovementEndInput() {
			fingerDown = false;
		}

		@Override
		public void onFrameUpdate(long milliSecondsSinceLastFrame) {
			mesh.getRotator().rotateY(rotationFromX * milliSecondsSinceLastFrame);

			if (!fingerDown) {
				rotationFromX *= 0.95f;
			}
		}
	}
}
