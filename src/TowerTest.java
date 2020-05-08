import static org.junit.Assert.*;
import org.junit.Test;


public class TowerTest {

    @Test
    public void testsell() {
        Tower tower = new Tower(new Point(40,30));
        tower.sell();
        assertEquals(tower.active,false);

    }

    @Test
    public void testsetActive(){
        Tower tower = new Tower(new Point(40,30));
        tower.setActive();
        assertEquals(tower.active,true);
    }
    @Test
    public void testUpgrade(){
        Tower tower = new Tower(new Point(40,30));
        tower.upgrade();
        assertEquals(tower.getLevel(),1);
    }

    }