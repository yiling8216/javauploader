/*
** Copyright 2005 Huxtable.com. All rights reserved.
*/

package com.pegaa.uploader.imgfilters;

import java.awt.*;
import java.awt.image.*;

/**
 * An abstract superclass for filters which distort images in some way. The subclass only needs to override
 * two methods to provide the mapping between source and destination pixels.
 */
public abstract class TransformFilter extends WholeImageFilter {

	public final static int ZERO = 0;
	public final static int CLAMP = 1;
	public final static int WRAP = 2;

	protected int edgeAction = ZERO;

	public void setEdgeAction(int edgeAction) {
		this.edgeAction = edgeAction;
	}

	public int getEdgeAction() {
		return edgeAction;
	}
	
	protected abstract void transformInverse(int x, int y, float[] out);

        @Override
	protected void transformSpace(Rectangle rect) {
	}

	protected int[] filterPixels( int width, int height, int[] inPixels, Rectangle transformedSpace ) {
		int srcWidth = width;
		int srcHeight = height;
		int outWidth = transformedSpace.width;
		int outHeight = transformedSpace.height;
		int outX, outY, srcX, srcY;
		int index = 0;
		int[] outPixels = new int[outWidth * outHeight];

		outX = transformedSpace.x;
		outY = transformedSpace.y;
		int[] rgb = new int[4];
		float[] out = new float[2];

		for (int y = 0; y < outHeight; y++) {
			for (int x = 0; x < outWidth; x++) {
				transformInverse(outX+x, outY+y, out);
				srcX = (int)out[0];
				srcY = (int)out[1];
				// int casting rounds towards zero, so we check out[0] < 0, not srcX < 0
				if (out[0] < 0 || srcX >= srcWidth || out[1] < 0 || srcY >= srcHeight) {
					int p;
					switch (edgeAction) {
					case ZERO:
					default:
						p = 0;
						break;
					case WRAP:
						p = inPixels[(ImageMath.mod(srcY, srcHeight) * srcWidth) + ImageMath.mod(srcX, srcWidth)];
						break;
					case CLAMP:
						p = inPixels[(ImageMath.clamp(srcY, 0, srcHeight-1) * srcWidth) + ImageMath.clamp(srcX, 0, srcWidth-1)];
						break;
					}
					outPixels[index++] = p;
				} else {
					float xWeight = out[0]-srcX;
					float yWeight = out[1]-srcY;
					int i = srcWidth*srcY + srcX;
					int dx = srcX == srcWidth-1 ? 0 : 1;
					int dy = srcY == srcHeight-1 ? 0 : srcWidth;
					rgb[0] = inPixels[i];
					rgb[1] = inPixels[i+dx];
					rgb[2] = inPixels[i+dy];
					rgb[3] = inPixels[i+dx+dy];
					outPixels[index++] = ImageMath.bilinearInterpolate(xWeight, yWeight, rgb);
				}
			}
		}
		return outPixels;
	}

}

