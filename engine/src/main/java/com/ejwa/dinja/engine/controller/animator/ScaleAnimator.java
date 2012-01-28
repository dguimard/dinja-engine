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
package com.ejwa.dinja.engine.controller.animator;

import com.ejwa.dinja.engine.model.ease.IEase;
import com.ejwa.dinja.engine.model.properties.Scalable;

public class ScaleAnimator extends AbstractAnimator<Scalable, Float> {
	public ScaleAnimator(Scalable scalable, float destination, float duration, Class<? extends IEase> ease) {
		super(scalable, scalable.getScaler().get(), destination, duration, ease);
	}

	public ScaleAnimator(IAnimatorListener animatorListener, Scalable scalable,
	                           float destination, float duration, Class<? extends IEase> ease) {
		super(animatorListener, scalable, scalable.getScaler().get(), destination, duration, ease);
	}

	@Override
	public void onFrameUpdate(long milliSecondsSinceLastFrame) {
		super.onFrameUpdate(milliSecondsSinceLastFrame);

		final float scale = ease.getValue(time, origin, destination, duration);
		animatable.getScaler().set(scale);
	}
}
