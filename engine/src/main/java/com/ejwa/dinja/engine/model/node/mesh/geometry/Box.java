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
package com.ejwa.dinja.engine.model.node.mesh.geometry;

import com.ejwa.dinja.engine.model.node.mesh.Mesh;
import com.ejwa.dinja.engine.model.node.mesh.Vertex;
import com.ejwa.dinja.opengles.primitive.PrimitiveType;
import org.openmali.vecmath2.Vector2f;
import org.openmali.vecmath2.Vector3f;


public class Box extends Mesh {
	private final Vector3f dimensions = new Vector3f();

	private static final Vector3f[] NORMALS = {
		new Vector3f(0, 0, 1),  // front (outwards)
		new Vector3f(0, 1, 0),  // top (up)
		new Vector3f(-1, 0, 0), // left
		new Vector3f(0, -1, 0), // bottom (down)
		new Vector3f(1, 0, 0),  // right
		new Vector3f(0, 0, -1)  // back (inwards)
	};

	private final Vector3f[] coordinates = {
		new Vector3f(1, 1, 1), new Vector3f(-1, 1, 1),     // top right->left front points
		new Vector3f(-1, -1, 1), new Vector3f(1, -1, 1),   // bottom left-right front points
		new Vector3f(1, 1, -1), new Vector3f(-1, 1, -1),   // top right->left back points
		new Vector3f(-1, -1, -1), new Vector3f(1, -1, -1), // bottom left-right back points
	};

	private final Vertex[] vertices = {
		new Vertex(coordinates[2]), new Vertex(coordinates[3]), new Vertex(coordinates[1]), new Vertex(coordinates[0]), // front
		new Vertex(coordinates[0]), new Vertex(coordinates[4]), new Vertex(coordinates[1]), new Vertex(coordinates[5]), // top
		new Vertex(coordinates[5]), new Vertex(coordinates[6]), new Vertex(coordinates[1]), new Vertex(coordinates[2]), // left
		new Vertex(coordinates[2]), new Vertex(coordinates[6]), new Vertex(coordinates[3]), new Vertex(coordinates[7]), // bottom
		new Vertex(coordinates[3]), new Vertex(coordinates[7]), new Vertex(coordinates[0]), new Vertex(coordinates[4]), // right
		new Vertex(coordinates[4]), new Vertex(coordinates[7]), new Vertex(coordinates[5]), new Vertex(coordinates[6])  // back

	};

	private static final Vector2f[] TEXTURE_COORDINATES = {
		new Vector2f(0, 1), new Vector2f(1, 1), new Vector2f(0, 0), new Vector2f(1, 0), // front
		new Vector2f(1, 1), new Vector2f(1, 0), new Vector2f(0, 1), new Vector2f(0, 0), // top
		new Vector2f(0, 0), new Vector2f(0, 1), new Vector2f(1, 0), new Vector2f(1, 1), // left
		new Vector2f(0, 0), new Vector2f(0, 1), new Vector2f(1, 0), new Vector2f(1, 1), // bottom
		new Vector2f(0, 1), new Vector2f(1, 1), new Vector2f(0, 0), new Vector2f(1, 0), // right
		new Vector2f(1, 1), new Vector2f(1, 0), new Vector2f(0, 1), new Vector2f(0, 0)  // back
	};

	public Box(String name, Vector3f dimensions) {
		this(name, dimensions.getX(), dimensions.getY(), dimensions.getZ());
	}

	public Box(String name, float width, float height, float depth) {
		super(name, PrimitiveType.GL_TRIANGLE_STRIP);
		dimensions.set(width, height, depth);

		for (int i = 0; i < vertices.length / 4; i++) {
			vertices[i * 4].setNormal(NORMALS[i]);
			vertices[i * 4 + 1].setNormal(NORMALS[i]);
			vertices[i * 4 + 2].setNormal(NORMALS[i]);
			vertices[i * 4 + 3].setNormal(NORMALS[i]);
		}

		for (int i = 0; i < vertices.length; i++) {
			vertices[i].setTextureCoordinates(TEXTURE_COORDINATES[i]);
		}

		for (Vector3f v : coordinates) {
			v.scale(width, height, depth);
		}

		addVertices(vertices);
		addIndices(vertices);
		getMeshPrimitiveData().update();
	}

	private void setBoxFaceTextureCoordinates(int offset, Vector2f uv1, Vector2f uv2, Vector2f uv3, Vector2f uv4) {
		getVertices().get(offset).setTextureCoordinates(uv1);
		getVertices().get(offset + 1).setTextureCoordinates(uv2);
		getVertices().get(offset + 2).setTextureCoordinates(uv3);
		getVertices().get(offset + 3).setTextureCoordinates(uv4);
		getMeshPrimitiveData().update();
	}

	public void setFrontFaceTextureCoordinates(Vector2f uv1, Vector2f uv2, Vector2f uv3, Vector2f uv4) {
		 setBoxFaceTextureCoordinates(0, uv1, uv2, uv3, uv4);
	}

	public void setTopFaceTextureCoordinates(Vector2f uv1, Vector2f uv2, Vector2f uv3, Vector2f uv4) {
		setBoxFaceTextureCoordinates(4, uv1, uv2, uv3, uv4);

	}

	public void setLeftFaceTextureCoordinates(Vector2f uv1, Vector2f uv2, Vector2f uv3, Vector2f uv4) {
		setBoxFaceTextureCoordinates(8, uv1, uv2, uv3, uv4);
	}

	public void setBottomFaceTextureCoordinates(Vector2f uv1, Vector2f uv2, Vector2f uv3, Vector2f uv4) {
		setBoxFaceTextureCoordinates(12, uv1, uv2, uv3, uv4);
	}

	public void setRightFaceTextureCoordinates(Vector2f uv1, Vector2f uv2, Vector2f uv3, Vector2f uv4) {
		setBoxFaceTextureCoordinates(16, uv1, uv2, uv3, uv4);
	}

	public void setBackFaceTextureCoordinates(Vector2f uv1, Vector2f uv2, Vector2f uv3, Vector2f uv4) {
		setBoxFaceTextureCoordinates(20, uv1, uv2, uv3, uv4);
	}

	public Vector3f getDimensions() {
		return dimensions;
	}
}
