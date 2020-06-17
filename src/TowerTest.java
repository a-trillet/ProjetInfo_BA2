import org.junit.Test;
import static org.junit.Assert.*;


public class TowerTest {


    @Test
    public void testsell() {
        Tower tower = new RajTower(new Point(40,30));
        tower.setActive();
        Game.getDrawing().drawTower(tower);
        tower.sell();
        assertEquals(tower.active,false);

    }

    @Test
    public void testsetActive(){
        Tower tower = new RajTower(new Point(40,30));
        tower.setActive();
        assertEquals(tower.active,true);
    }

    @Test
    public void testUpgrade(){
        Tower tower = new RajTower(new Point(40,30));
        tower.setActive();
        tower.upgrade();
        assertEquals(tower.getLevel(),2);
    }

    }