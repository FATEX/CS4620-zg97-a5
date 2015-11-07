/**
 * @author Jimmy, Andrew 
 */

package cs4620.splines;
import java.util.ArrayList;

import egl.math.Matrix4;
import egl.math.Vector2;
import egl.math.Vector4;

public class CatmullRom extends SplineCurve {

	public CatmullRom(ArrayList<Vector2> controlPoints, boolean isClosed,
			float epsilon) throws IllegalArgumentException {
		super(controlPoints, isClosed, epsilon);
	}

	@Override
	public CubicBezier toBezier(Vector2 p0, Vector2 p1, Vector2 p2, Vector2 p3,
			float eps) {
		//TODO A5
		//SOLUTION
		Matrix4 m = new Matrix4(
				0, 1, 0, 0,
				(float)- 1 / 6, 1, (float)1 / 6, 0,
				0, (float)1 / 6, 1, (float)- 1 / 6,
				0, 0, 1, 0
				);
		Vector4 x = m.mul(new Vector4 (p0.x, p1.x, p2.x, p3.x));
		Vector4 y = m.mul(new Vector4 (p0.y, p1.y, p2.y, p3.y));
		return new CubicBezier(new Vector2(x.x, y.x), new Vector2(x.y, y.y), new Vector2(x.z, y.z), new Vector2(x.w, y.w), eps);
		//END SOLUTION
	}
//	 public static void main(String[] args) {
//	    	Vector2 p0 = new Vector2(0,0);
//	    	Vector2 p1 = new Vector2(1,2);
//	    	Vector2 p2 = new Vector2(3,2);
//	    	Vector2 p3 = new Vector2(4,0);
//	    	ArrayList<Vector2> controlPoints = new ArrayList<Vector2>();
//	    	controlPoints.add(p0);controlPoints.add(p1);controlPoints.add(p2);controlPoints.add(p3);
//	    	CubicBezier cb = new CatmullRom(controlPoints,false,(float)0.1).toBezier(p0, p1, p2, p3,(float)0.1);
//	    	System.out.println();
//	    	
//		}
}
