package cs4620.splines;

import java.util.ArrayList;

import egl.math.Matrix3;
import egl.math.Vector2;
/*
 * Cubic Bezier class for the splines assignment
 */

public class CubicBezier {
	private int count = 0;
	//This Bezier's control points
	public Vector2 p0, p1, p2, p3;
	
	//Control parameter for curve smoothness
	float epsilon;
	
	//The points on the curve represented by this Bezier
	private ArrayList<Vector2> curvePoints;
	
	//The normals associated with curvePoints
	private ArrayList<Vector2> curveNormals;
	
	//The tangent vectors of this bezier
	private ArrayList<Vector2> curveTangents;
	
	
	/**
	 * 
	 * Cubic Bezier Constructor
	 * 
	 * Given 2-D BSpline Control Points correctly set self.{p0, p1, p2, p3},
	 * self.uVals, self.curvePoints, and self.curveNormals
	 * 
	 * @param bs0 First Bezier Spline Control Point
	 * @param bs1 Second Bezier Spline Control Point
	 * @param bs2 Third Bezier Spline Control Point
	 * @param bs3 Fourth Bezier Spline Control Point
	 * @param eps Maximum angle between line segments
	 */
	public CubicBezier(Vector2 p0, Vector2 p1, Vector2 p2, Vector2 p3, float eps) {
		curvePoints = new ArrayList<Vector2>();
		curveTangents = new ArrayList<Vector2>();
		curveNormals = new ArrayList<Vector2>();
		epsilon = eps;
		
		this.p0 = new Vector2(p0);
		this.p1 = new Vector2(p1);
		this.p2 = new Vector2(p2);
		this.p3 = new Vector2(p3);
		count = 0;
		tessellate();
	}
	

    /**
     * Approximate a Bezier segment with a number of vertices, according to an appropriate
     * smoothness criterion for how many are needed.  The points on the curve are written into the
     * array self.curvePoints, the tangents into self.curveTangents, and the normals into self.curveNormals.
     * The final point, p3, is not included, because cubic Beziers will be "strung together".
     */
    private void tessellate() {
    	 // TODO A5
    	//SOLUTION
    	ArrayList<Vector2> controlPoints = new ArrayList<Vector2>();
    	controlPoints.add(p0);
    	controlPoints.add(p1);
    	controlPoints.add(p2);
    	controlPoints.add(p3);
    	buildPoints(controlPoints);
    	//END SOLUTION
    }
    
    private void buildPoints(ArrayList<Vector2> controlPoints) {
    	count++;
    	if(count > 30) return;
    	Vector2 p0 = controlPoints.get(0);
    	Vector2 p1 = controlPoints.get(1);
    	Vector2 p2 = controlPoints.get(2);
    	Vector2 p3 = controlPoints.get(3);
    	
    	System.out.println(p0);
    	System.out.println(p1);
    	System.out.println(p2);
    	System.out.println(p3);
    	
    	Vector2 v1 = p1.clone().sub(p0);
    	Vector2 v2 = p2.clone().sub(p1);
    	System.out.println(v1);
    	System.out.println(v2);
    	float angle1 = 0;
    	
    	//if (!v1.normalize().equals(v2.normalize())) angle1 = p1.clone().sub(p0).angle(p2.clone().sub(p1));
    	try{
    		angle1 = p1.clone().sub(p0).angle(p2.clone().sub(p1));
    	}catch(Exception e){
    		System.out.println("00000000000");
    	}
    	
    	float angle2 = 0;	
    	v1 = p2.clone().sub(p1);
    	v2 = p3.clone().sub(p2);
    	
    	//if (!v1.normalize().equals(v2.normalize())) angle2 = p2.clone().sub(p1).angle(p3.clone().sub(p2));
    	try{
    		angle2 = p1.clone().sub(p0).angle(p2.clone().sub(p1));
    	}catch(Exception e){
    		
    	}
  
    	//not sure if we need abs fuc or not
    	if (Math.max(angle1, angle2) < this.epsilon / 2) {
    		this.curvePoints.add(p0);
    		
    		Vector2 tangent = p1.clone().sub(p0);
    		tangent.normalize();
    		this.curveTangents.add(tangent);
    		
//    		Vector2 normal = new Vector2();
//    		normal.x = tangent.y;
//    		normal.y = -tangent.x;
    		Matrix3 rotateT = Matrix3.createRotation((float)-Math.PI/2);
    		Vector2 normal = new Vector2(rotateT.clone().mul(tangent.clone()));
    		normal.normalize();
    		this.curveNormals.add(normal);
    		//System.out.println("out");
    		return;
    	}
    	
    	
    	Vector2 h = p1.clone().add(p2).div(2);
    	
    	//left info
    	Vector2 l0 = new Vector2();
    	l0.set(p0);
    	Vector2 l1 = p0.clone().add(p1).div(2);
    	Vector2 l2 = l1.clone().add(h).div(2);
    	
    	//right info 
    	Vector2 r2 = p2.clone().add(p3).div(2);
    	Vector2 r1 = h.clone().add(r2).div(2);
    	Vector2 r0 = l2.clone().add(r1).div(2);
    	Vector2 r3 = new Vector2();
    	r3.set(p3);
    	Vector2 l3 = l2.clone().add(r1).div(2);
    	
    	ArrayList<Vector2> lPoints = new ArrayList<Vector2>();
    	lPoints.add(l0);
    	lPoints.add(l1);
    	lPoints.add(l2);
    	lPoints.add(l3);
    	
    	ArrayList<Vector2> rPoints = new ArrayList<Vector2>();
    	rPoints.add(r0);
    	rPoints.add(r1);
    	rPoints.add(r2);
    	rPoints.add(r3);
    //	System.out.println("next");
    	
    	buildPoints(lPoints);
    	buildPoints(rPoints);
    }
	
    
    /**
     * @return The points on this cubic bezier
     */
    public ArrayList<Vector2> getPoints() {
    	ArrayList<Vector2> returnList = new ArrayList<Vector2>();
    	for(Vector2 p : curvePoints) returnList.add(p.clone());
    	return returnList;
    }
    
    /**
     * @return The tangents on this cubic bezier
     */
    public ArrayList<Vector2> getTangents() {
    	ArrayList<Vector2> returnList = new ArrayList<Vector2>();
    	for(Vector2 p : curveTangents) returnList.add(p.clone());
    	return returnList;
    }
    
    /**
     * @return The normals on this cubic bezier
     */
    public ArrayList<Vector2> getNormals() {
    	ArrayList<Vector2> returnList = new ArrayList<Vector2>();
    	for(Vector2 p : curveNormals) returnList.add(p.clone());
    	return returnList;
    }
    
    
    /**
     * @return The references to points on this cubic bezier
     */
    public ArrayList<Vector2> getPointReferences() {
    	ArrayList<Vector2> returnList = new ArrayList<Vector2>();
    	for(Vector2 p : curvePoints) returnList.add(p);
    	return returnList;
    }
    
    /**
     * @return The references to tangents on this cubic bezier
     */
    public ArrayList<Vector2> getTangentReferences() {
    	ArrayList<Vector2> returnList = new ArrayList<Vector2>();
    	for(Vector2 p : curveTangents) returnList.add(p);
    	return returnList;
    }
    
    /**
     * @return The references to normals on this cubic bezier
     */
    public ArrayList<Vector2> getNormalReferences() {
    	ArrayList<Vector2> returnList = new ArrayList<Vector2>();
    	for(Vector2 p : curveNormals) returnList.add(p);
    	return returnList;
    }
    
    public static void main(String[] args) {
    	Vector2 p0 = new Vector2(0,0);
    	Vector2 p1 = new Vector2(1,1);
    	Vector2 p2 = new Vector2(2,2);
    	Vector2 p3 = new Vector2(4,4);
    	CubicBezier cb = new CubicBezier(p0, p1, p2, p3, (float)0.1);
    	System.out.println(cb.curvePoints);
    	System.out.println(cb.curveTangents);
    	System.out.println(cb.curveNormals);
    	
	}
    
}
