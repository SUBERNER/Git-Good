package io.github.froggers_revenge;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;

//due to the need of this SINGLE file, i hate libGDX
//stores an object collision for projectile and frogger to interact with
public class Collision {
        private List<Rectangle> vehicleHitboxs = new ArrayList<>(); //stores hitboxes for all vehicles
        private List<Rectangle> projectileHitboxs = new ArrayList<>(); //stores hitboxes for all projectiles
        private Rectangle frogHitbox; //stores hitboxes for all projectiles

        //test is what object you want to see is colliding with anything
        //targets is what you want to test what objects are colliding with the test
        public boolean testTargets(Rectangle test, List<Rectangle> targets)
        {
            
            if (!targets.isEmpty() && test != null)
            {
                
                for (Rectangle target: targets) { 
                    if (test.overlaps(target)) {
                        return true; 
                    } 
                }
            }
            return false;
        }

        public void setFrogHitbox(Rectangle frogHitbox) {
            this.frogHitbox = frogHitbox;
        }

        public Rectangle getFrogHitbox() {
            return frogHitbox;
        }

        public void setProjectileHitboxs(List<Rectangle> projectileHitbox) {
            this.projectileHitboxs = projectileHitbox;
        }

        public List<Rectangle> getProjectileHitboxs() {
            return projectileHitboxs;
        }

        public void addProjectileHitboxs(Rectangle projectileHitbox) {
            projectileHitboxs.add(projectileHitbox);
        }

        public void setVehicleHitboxs(List<Rectangle> vehicleHitbox) {
            this.vehicleHitboxs = vehicleHitbox;
        }

        public List<Rectangle> getVehicleHitboxs() {
            return vehicleHitboxs;
        }

        public void addVehicleHitboxs(Rectangle vehicleHitbox) {
            vehicleHitboxs.add(vehicleHitbox);
        }
}