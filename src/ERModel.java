import java.util.*;

public class ERModel extends Observable {


    int entityWidth = 200;
    int entityHeight = 100;
    private int entityIdCounter;
    private List<Entity> entities = new ArrayList<Entity>();
    private List<Arrow> arrows = new ArrayList<Arrow>();
    private int zoomLevel = 100; //Out of 100, the percentage of zoom

    public ERModel() {
        this.entityIdCounter = 0;
        setChanged();
    }

    public void select(){
        setChanged();
        notifyObservers();
    }

    public int[] getChosenEntity(){
        int counter = 0;
        for (int i=0; i<entities.size(); i++){
            if (entities.get(i).isSelected()){
                counter ++;
            }
        }
        int[] output = new int[counter];
        counter =0;
        for (int i=0; i<entities.size(); i++){
            if (entities.get(i).isSelected()){
                output[counter] = entities.get(i).getId();
                counter ++;
            }
        }
        if (output == null){
            return null;
        }
        return output;
    }

    public void deleteSelected(){
        int[] toDelete = getChosenEntity();
        // for (int i=0; i<toDelete.size(); i++){
        //     entities
        // }
        if (getChosenEntity().length != 0){
            int goodId = getChosenEntity()[0];
            int countergood = 0;
            for (int i=0; i<getArrowList().size(); i++){
                if (getArrowList().get(i).getSource().getId() == goodId || getArrowList().get(i).getTarget().getId() == goodId){
                    countergood++;
                }
            }
            int[] goodArrows = new int[countergood];
            int countergood2 = 0;
            for (int i=0; i<getArrowList().size(); i++){
                if (getArrowList().get(i).getSource().getId() == goodId || getArrowList().get(i).getTarget().getId() == goodId){
                    goodArrows[countergood2] = i;
                    countergood2++;
                }
            }
            for(int i=0; i<goodArrows.length; i++){
                arrows.remove(goodArrows[i]);
            }
        }
        for(int i=0; i<toDelete.length;i++){
            entities.remove(toDelete[i]);
        }
        for(int i=0; i<entities.size();i++){
            entities.get(i).setId(i);
        }
        notifyObservers();
    }

    //Zooms
    public void zoom(int a){
        zoomLevel += a;
        setChanged();
        notifyObservers();
    }

    public void specificZoom(int widthZoom,int heightZoom){
        this.entityWidth = widthZoom;
        this.entityHeight = heightZoom;
        notifyObservers();
    }

    public void zoomOut(int a){
        zoomLevel -= a;
        setChanged();
        notifyObservers();
    }

    public int getZoomLevel(){
        return zoomLevel;
    }

    //Returns ID of the Entity once it's created
    public int addEntity(String name){
        Entity newEntity = new Entity(name, entities.size());
        entities.add(newEntity);
        this.entityIdCounter++;
        setChanged();
        notifyObservers();
        return this.entityIdCounter-1;
    }

    //Add entitiy with coordinates
    public int addEntity(String name, int x, int y){
        Entity newEntity = new Entity(name, entities.size(), x, y);
        entities.add(newEntity);
        this.entityIdCounter++;
        setChanged();
        notifyObservers();
        return this.entityIdCounter-1;
    }

    //Given the source entity ID and target entity ID, creates
    //an arrow
    public void addArrow(int sourceId, int targetId){
        Entity source = getEntity(sourceId);
        Entity target = getEntity(targetId);
        Arrow arrow = new Arrow(source, target);
        source.addArrow(arrow);
        arrows.add(arrow);
        setChanged();
        notifyObservers();
    }

    public boolean moveEntity(Entity e, int x, int y){
        boolean accepted = true;
        for (int i=0; i<this.getEntityCount(); i++){

            if (e.getId() != entities.get(i).getId()){
                if (x >= entities.get(i).getX() && x <= entities.get(i).getX()+entityWidth){
                    if (y >= entities.get(i).getY() && y <= entities.get(i).getY()+entityHeight){
                        accepted = false;
                    }
                }
            }
        }
        if (accepted == true){
            e.move(x,y);
            setChanged();
            notifyObservers();
        }
        return accepted;
    }

    //Returns the number of entities
    public int getEntityCount(){
        return entities.size();
    }

    //Returns the number of arrows
    public int getArrowCount(){
        return arrows.size();
    }

    //Returns a list of entities
    public List<Entity> getEntityList(){
        return entities;
    }

    //Returns a list of arrows
    public List<Arrow> getArrowList(){
        return arrows;
    }


    //Returns the entity object given the id
    public Entity getEntity(int id){
        Entity output = null;
        for (int i=0; i<this.getEntityCount(); i++){
            if (entities.get(i).getId() == id){
                output = entities.get(i);
            }
        }
        return output;
    }

    //Returns the entity name given the entity id
    public int getEntityIdByName(String name){
        int output = 0;
        for (int i=0; i<this.getEntityCount(); i++){
            if (entities.get(i).getName() == name){
                output = entities.get(i).getId();
            }
        }
        return output;
    }

    //Returns the entity name given the entity id
    public String getEntityName(int id){
        String output = "";
        for (int i=0; i<this.getEntityCount(); i++){
            if (entities.get(i).getId() == id){
                output = entities.get(i).getName();
            }
        }
        return output;
    }

}