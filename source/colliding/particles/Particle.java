
package colliding.particles;


class Particle {    
    private int crdX, crdY, rad;
    private int crdRectX, crdRectY, diam;
    private int velX, velY;
    
    public static final int MIN_RADIUS = 8;
    public static final int MAX_RADIUS = 20;
    public static final int MAX_VELOCITY = 5;
    
    public Particle (int x, int y, int r, int vx, int vy) {        
        setCrdX(x);
        setCrdY(y);
        setRad(r);
        setVelX(vx);
        setVelY(vy);
    }
    
    public static Particle generateRandomParticle(int maxX, int maxY) {    	
        return new Particle(
                MAX_RADIUS + 1 + randomInteger(maxX - 2*MAX_RADIUS - 1),   
                MAX_RADIUS + 1 + randomInteger(maxY - 2*MAX_RADIUS - 1), 
                MIN_RADIUS + randomInteger(MAX_RADIUS - MIN_RADIUS + 1),
                1 + randomInteger(MAX_VELOCITY),
                1 + randomInteger(MAX_VELOCITY)  
        );            	
    }
    
    private static int randomInteger(int upperBound) {
    	return (int)(Math.random() * upperBound);
    }
    
    public final int getCrdX() {
        return crdX;
    }
    
    public final int getCrdY() {
        return crdY;
    }
    
    public final int getRad() {
        return rad;
    }
    
    public final int getCrdRectX() {
        return crdRectX;
    }
    
    public final int getCrdRectY() {
        return crdRectY;
    }
    
    public final int getDiam() {
        return diam;
    }
    
    public final int getVelX() {
        return velX;
    }
    
    public final int getVelY() {
        return velY;
    }
    
    public final void setCrdX(int x) {
        crdX = x;
        crdRectX = crdX - rad; 
    }
    
    public final void setCrdY(int y) {
        crdY = y;
        crdRectY = crdY - rad; 
    }
    
    public final void setRad(int r) {
        rad = r;
        diam = 2*rad; 
    }    
    
    public final void setVelX(int vx) {
        velX = vx; 
    } 
    
    public final void setVelY(int vy) {
        velY = vy; 
    }  
}    