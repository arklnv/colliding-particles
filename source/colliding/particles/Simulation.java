

package colliding.particles;


class Simulation {
    private static final int DEFAULT_BOX_SIZE_X = 600;
    private static final int DEFAULT_BOX_SIZE_Y = 400;
    private static final int DEFAULT_NUMBER_OFPARTICLES = 4;
    private int boxSizeX, boxSizeY;
    private int numberOfParticles;
    public Particle[] particle;
    
    public Simulation() {        
        generateNewSimulation(DEFAULT_NUMBER_OFPARTICLES, DEFAULT_BOX_SIZE_X, DEFAULT_BOX_SIZE_Y);
    }
    
    public Simulation(int numberOfParticles, int boxSizeX, int boxSizeY) {
        generateNewSimulation(numberOfParticles, boxSizeX, boxSizeY);
    }
    
    private void generateNewSimulation(int numberOfParticles, int boxSizeX, int boxSizeY) {
        this.boxSizeX = boxSizeX;
        this.boxSizeY = boxSizeY;
        this.numberOfParticles = numberOfParticles;
        
        particle = new Particle[getNumberOfParticles()];
        
        for (int i = 0; i < getNumberOfParticles(); i++) {
            particle[i] = Particle.generateRandomParticle(boxSizeX, boxSizeY);
        }        
    }          
    
    public void advanceTime() {        
        for (int i = 0; i < getNumberOfParticles(); i++) {
            checkCollisionWithBoundary(particle[i]);            
        }
        
        for (int i = 0; i < getNumberOfParticles(); i++) {
            for (int j = 0; j <= i; j++) {
                if ( !(i==j) ) {
                    checkCollisionBetweenParticles(particle[i], particle[j]);
                }
            }
        }
        
        for (int i = 0; i < getNumberOfParticles(); i++) {
            moveParticle(particle[i]);            
        }     
    }
    
    private void moveParticle (Particle p) {
        p.setCrdX(p.getCrdX() + p.getVelX());
        p.setCrdY(p.getCrdY() + p.getVelY());
    }
    
    private void checkCollisionWithBoundary(Particle p) {
        if (p.getCrdRectX() < 0) {
            p.setVelX(Math.abs(p.getVelX()));
            if (p.getVelX() == 0) { p.setVelX(Particle.MAX_VELOCITY); }
        }
        if (p.getCrdRectX() + p.getDiam() > boxSizeX) {
            p.setVelX(-Math.abs(p.getVelX()));
            if (p.getVelX() == 0) { p.setVelX( -Particle.MAX_VELOCITY ); }
        }
        if (p.getCrdRectY() < 0) {
            p.setVelY(Math.abs(p.getVelY()));
            if (p.getVelY() == 0) { p.setVelY(Particle.MAX_VELOCITY); }
        }
        if (p.getCrdRectY() + p.getDiam() > boxSizeY) {
            p.setVelY(-Math.abs(p.getVelY()));
            if (p.getVelY() == 0) { p.setVelY( -Particle.MAX_VELOCITY ); }
        }       
    }
    
    private void checkCollisionBetweenParticles(Particle p1, Particle p2) {
        
        // First we calculate the distance between two particles at a given moment: dis_t1. 
        // If dis_t1 is smaller then the sum of the radii of the particles, then we consider that particles are "interacting" 
        // with each other. Then we calculate the distance dis_t2 between particles at the next moment in time. If the particles 
        // are approaching each other (dis_t2 < dis_t1) the particles are "in the process of collision", so we calculate their new 
        // velocities. But if dis_t2 > dis_t1 then we consider particles to be moving away from each other - this way we escape 
        // the "trapping" of the particles in the interaction region.
        
        int dis_t1 = distanceBetweenPoints(p1.getCrdX(), p1.getCrdY(), p2.getCrdX(), p2.getCrdY());        

        if ( dis_t1 < p1.getRad() + p2.getRad() ) {
            int dis_t2 = distanceBetweenPoints(
                    p1.getCrdX() + p1.getVelX(),
                    p1.getCrdY() + p1.getVelY(),
                    p2.getCrdX() + p2.getVelX(),
                    p2.getCrdY() + p2.getVelY()
            );                        
            
            if ( dis_t2 <= dis_t1 ) {
                int vX = p1.getVelX();
                int vY = p1.getVelY();
                p1.setVelX(p2.getVelX());
                p1.setVelY(p2.getVelY());
                p2.setVelX(vX);
                p2.setVelY(vY);
            }
        }
    }
    
    // Not currently used anywhere.
    private int distanceBetweenParticles(Particle p1, Particle p2) {
        double disDbl = Math.sqrt(
                Math.pow(p1.getCrdX() - p2.getCrdX(), 2) + Math.pow(p1.getCrdY() - p2.getCrdY(), 2)
        );
        return (int) disDbl;
    }
    
    private int distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        double disDbl = Math.sqrt(
            Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)
        );        
        return (int) disDbl;
    }
    
    public final int getNumberOfParticles() {
        return numberOfParticles;
    }    
}
