package cs4620.gl;

import cs4620.common.SceneCamera;
import cs4620.common.SceneObject;
import egl.math.Matrix4;
import egl.math.Vector2;

public class RenderCamera extends RenderObject {
	/**
	 * Reference to Scene counterpart of this camera
	 */
	public final SceneCamera sceneCamera;
	
	/**
	 * The view transformation matrix
	 */
	public final Matrix4 mView = new Matrix4();
	
	/**
	 * The projection matrix
	 */
	public final Matrix4 mProj = new Matrix4();
	
	/**
	 * The viewing/projection matrix (The product of the view and projection matrices)
	 */
	public final Matrix4 mViewProjection = new Matrix4();
	
	/**
	 * The size of the viewport, in pixels.
	 */
	public final Vector2 viewportSize = new Vector2();
	
	public RenderCamera(SceneObject o, Vector2 viewSize) {
		super(o);
		sceneCamera = (SceneCamera)o;
		viewportSize.set(viewSize);
	}

	/**
	 * Update the camera's viewing/projection matrix.
	 * 
	 * Update the camera's viewing/projection matrix in response to any changes in the camera's transformation
	 * or viewing parameters.  The viewing and projection matrices are computed separately and multiplied to 
	 * form the combined viewing/projection matrix.  When computing the projection matrix, the size of the view
	 * is adjusted to match the aspect ratio (the ratio of width to height) of the viewport, so that objects do 
	 * not appear distorted.  This is done by increasing either the height or the width of the camera's view,
	 * so that more of the scene is visible than with the original size, rather than less.
	 *  
	 * @param viewportSize
	 */
	public void updateCameraMatrix(Vector2 viewportSize) {
		this.viewportSize.set(viewportSize);
		
		// The camera's transformation matrix is found in this.mWorldTransform (inherited from RenderObject).
		// The other camera parameters are found in the scene camera (this.sceneCamera).
		// Look through the methods in Matrix4 before you type in any matrices from the book or the OpenGL specification.
		double f = this.sceneCamera.zPlanes.y;
		double n = this.sceneCamera.zPlanes.x;
		
		float nX = viewportSize.x;
		float nY = viewportSize.y;
		
		if (this.sceneCamera.isPerspective) {
			Matrix4.createPerspective(
					(float) this.sceneCamera.imageSize.x * (nX/nY), 
					(float) this.sceneCamera.imageSize.y, 
					(float) n, (float) f, this.mProj);
		} else {
			Matrix4.createOrthographic(
					(float) this.sceneCamera.imageSize.x * (nX/nX), 
					(float) this.sceneCamera.imageSize.y, 
					(float) n, (float) f, this.mProj);
		}
		
		this.mView.set(this.mWorldTransform);
		this.mView.invert();
		
		
		
		this.mViewProjection.set(this.mProj);
		this.mViewProjection.mulBefore(this.mView);
		// TODO#A3 SOLUTION START

		// Create viewing matrix
		
		// Correct Image Aspect Ratio By Enlarging Image
		

		// Create Projection
		
		
		// Set the view projection matrix using the view and projection matrices
	
		// SOLUTION END
	}	
}