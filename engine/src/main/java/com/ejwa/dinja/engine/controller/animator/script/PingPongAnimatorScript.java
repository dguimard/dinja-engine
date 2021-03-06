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
package com.ejwa.dinja.engine.controller.animator.script;

import com.ejwa.dinja.engine.controller.Controllable;
import com.ejwa.dinja.engine.controller.animator.DelayAnimator;
import com.ejwa.dinja.engine.controller.animator.IAnimator;

public class PingPongAnimatorScript extends AbstractAnimatorScript implements Controllable, IAnimator {
	private boolean reversed;

	public PingPongAnimatorScript(IAnimator animator) {
		super(animator);

		if (animator instanceof DelayAnimator) {
			throw new IllegalArgumentException("Putting a DelayAnimator in a PingPongAnimatorScript would " +
			                                   "result in an unnecessary endless loop.");
		} else if (animator instanceof LoopAnimatorScript) {
			throw new IllegalArgumentException("Can't put a LoopAnimatorScript in a PingPongAnimatorScript.");
		} else if (animator instanceof PingPongAnimatorScript) {
			throw new IllegalArgumentException("Can't put a PingPongAnimatorScript in a PingPongAnimatorScript.");
		}
	}

	@Override
	public void restart() {
		if (reversed) {
			animator.reverse();
			reversed = false;
		}
	}

	@Override
	public void onFrameUpdate(long milliSecondsSinceLastFrame) {
		animator.onFrameUpdate(milliSecondsSinceLastFrame);

		if (animator.isCompleted() && !reversed) {
			animator.reverse();
			reversed = true;
		}
	}
}
