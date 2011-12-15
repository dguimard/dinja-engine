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
package com.ejwa.dinja.opengles.primitive;

import com.ejwa.dinja.opengles.GLException;
import com.ejwa.dinja.opengles.library.NativeMemory;
import com.ejwa.dinja.opengles.shader.argument.AbstractVertexAttributeArray;
import com.ejwa.dinja.opengles.shader.argument.Tuple3fVertexAttributeArray;
import com.googlecode.javacpp.BytePointer;
import com.googlecode.javacpp.Pointer;
import com.googlecode.javacpp.ShortPointer;
import java.util.HashMap;
import java.util.Map;
import javax.vecmath.Vector3f;

public class PrimitiveData {
	private final PrimitiveType primitiveType;
	private Pointer indices;
	private final Tuple3fVertexAttributeArray vertices;
	private final Map<String, AbstractVertexAttributeArray> vertexAttributeArrays = new HashMap<String, AbstractVertexAttributeArray>();

	public PrimitiveData(PrimitiveType primitiveType, String vertexCoordVariableName) {
		this.primitiveType = primitiveType;
		vertices = new Tuple3fVertexAttributeArray(vertexCoordVariableName);
	}

	public PrimitiveData(PrimitiveType primitiveType, String vertexCoordVariableName, Vector3f[] vertices, Integer ...indices) {
		this(primitiveType, vertexCoordVariableName);
		setVerticesData(vertices);
		setIndices(indices);
	}

	public PrimitiveType getPrimitiveType() {
		return primitiveType;
	}

	public Pointer getIndices() {
		return indices;
	}

	public final void setIndices(Integer ...indices) {
		if (indices.length <= 256) {
			this.indices = NativeMemory.getBytePointer(this.indices, indices.length);
		} else {
			this.indices = NativeMemory.getShortPointer(this.indices, indices.length);
		}

		for (int i = 0; i < indices.length; i++) {
			if (indices[i] >= this.vertices.getData().capacity() / 3) {
				throw new GLException(String.format("Indices must point within bounds of vertices (got %d, but " +
				                                     "maximum is %d)", indices[i], this.vertices.getData().capacity() / 3));
			}

			if (this.indices instanceof BytePointer) {
				((BytePointer) this.indices).put(i, indices[i].byteValue());
			} else {
				((ShortPointer) this.indices).put(i, indices[i].shortValue());
			}
		}
	}

	public Tuple3fVertexAttributeArray getVertices() {
		return vertices;
	}

	public final void setVerticesData(Vector3f  ...vertices) {
		this.vertices.setData(vertices);
	}

	public void addVertexAttributeArray(AbstractVertexAttributeArray vertexAttributeArray) {
		vertexAttributeArrays.put(vertexAttributeArray.getVariableName(), vertexAttributeArray);
	}

	public void removeVertexAttributeArray(String variableName) {
		final AbstractVertexAttributeArray vertexAttributeArray = vertexAttributeArrays.remove(variableName);

		if (vertexAttributeArray != null) {
			vertexAttributeArray.getData().deallocate();
		}
	}

	public Map<String, AbstractVertexAttributeArray> getVertexAttributeArrays() {
		return vertexAttributeArrays;
	}

	@Override
	protected void finalize() throws Throwable {
		if (indices != null) { indices.deallocate(); }
		if (vertices != null) { vertices.getData().deallocate(); }

		for (AbstractVertexAttributeArray a : vertexAttributeArrays.values()) {
			a.getData().deallocate();
		}

		super.finalize();
	}
}
