import org.junit.Test;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Created by bwbecker on 2016-10-10.
 */
public class ERModelTest {
    //Tests whether entity creation works and returns a valid entity id
    @Test
    public void testEntityCreation() throws Exception {
        //First Entity
        ERModel m = new ERModel();
        int entityId;
        String output;
        entityId = m.addEntity("Model");
        output = m.getEntityName(entityId);
        assertEquals("Model", output);

        //Second Entity
        entityId = m.addEntity("Controller");
        output = m.getEntityName(entityId);
        assertEquals("Controller", output);
    }

    //Tests arrow creation
    @Test
    public void testArrowCreation() throws Exception {
        //First Arrow
        ERModel m = new ERModel();
        int entityId;
        String output;
        int entityId1;
        int entityId2;

        entityId1 = m.addEntity("Model");
        //Second Entity
        entityId2 = m.addEntity("Controller");
        assertEquals(m.getArrowCount(), 0);
        m.addArrow(entityId1, entityId2);
        m.addArrow(entityId2, entityId1);
        assertEquals(m.getArrowCount(), 2);
    }

    //Tests whether entity count is working properly
    @Test
    public void testEntityCount() throws Exception {
        ERModel m = new ERModel();
        int size = m.getEntityCount();
        m.addEntity("Model");
        int size2 = m.getEntityCount();
        assertEquals(1, size2-size);
    }

    //Tests if list of entities is correct
    @Test
    public void testEntityList() throws Exception {
        ERModel m = new ERModel();
        int entityId;
        int entityId2;
        entityId = m.addEntity("Model");
        entityId2 = m.addEntity("Controller");
        List<Entity> entities;
        entities = m.getEntityList();
        Entity entity1;
        Entity entity2;
        entity1 = entities.get(0);
        entity2 = entities.get(1);
        assertEquals(entity1.getName(), "Model");
        assertEquals(entity2.getName(), "Controller");
    }

    //Tests if list of arrows is correct
    @Test
    public void testArrowList() throws Exception {
        ERModel m = new ERModel();
        int entityId;
        int entityId2;
        entityId = m.addEntity("Model");
        entityId2 = m.addEntity("Controller");
        m.addArrow(entityId,entityId2);
        m.addArrow(entityId2,entityId);
        List<Arrow> arrows;
        arrows = m.getArrowList();
        Arrow arrow1;
        Arrow arrow2;
        arrow1 = arrows.get(0);
        arrow2 = arrows.get(1);
        assertEquals(arrow1.getSourceName(), "Model");
        assertEquals(arrow1.getTargetName(), "Controller");
        assertEquals(arrow2.getSourceName(), "Controller");
        assertEquals(arrow2.getTargetName(), "Model");
    }

    //Tests zooming
    @Test
    public void testZoomIn() throws Exception {
        ERModel m = new ERModel();
        assertEquals(m.getZoomLevel(), 100);
        m.zoom(15);
        assertEquals(m.getZoomLevel(),115);
    }

    //Tests zooming out
    @Test
    public void testZoomOut() throws Exception {
        ERModel m = new ERModel();
        assertEquals(m.getZoomLevel(), 100);
        m.zoomOut(15);
        assertEquals(m.getZoomLevel(),85);
    }

    //Test Coordinate moving of entities
    @Test
    public void moveTest() throws Exception {
        ERModel m = new ERModel();
        int entityId = m.addEntity("Model", 100, 100);
        Entity e = m.getEntity(entityId);
        boolean result;
        result = m.moveEntity(e, 101,101);
        assertEquals(result, true);
    }

    //Test Coordinate moving of entities (duplicate) should decline
    @Test
    public void moveTestDuplicate() throws Exception {
        ERModel m = new ERModel();
        int entityId = m.addEntity("Model", 100, 100);
        int entityId2 = m.addEntity("Controller", 300, 300);
        Entity e = m.getEntity(entityId);
        boolean result;
        result = m.moveEntity(e, 301 ,301);
        assertEquals(result, false);
    }


}