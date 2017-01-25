import java.util.*;

public class Entity {

    int x;
    int y;
    double xR;
    double yR;
    boolean selected;

    private String name;
    //Arrows that have this entity as source
    private List<Arrow> arrows = new ArrayList<Arrow>();
    int id;

    public Entity(String n, int id) {
        this.name = n;
        this.id = id;
        this.selected = false;
    }

    public Entity(String n, int id, int x, int y) {
        this.name = n;
        this.id = id;
        this.x = x;
        this.y = y;
        this.xR = (double) x;
        this.yR = (double) y;
    }

    public void zoomInSpecific(int mX, int mY, double c1, double c2){
        if (this.xR < mX){
            c1 = -1*c1;
        }
        if (this.yR < mY){
            c2 = -1*c2;
        }
        this.xR = this.xR + ((this.xR - mX) * 1.01)+c1;
        this.yR = this.yR + ((this.yR - mY) * 1.01)+c2;
        this.x = (int)this.xR;
        this.y = (int)this.yR;
    }

    public void zoomOutSpecific(int mX, int mY, double c1, double c2){
        if (this.xR < mX){
            c1 = -1*c1;
        }
        if (this.yR < mY){
            c2 = -1*c2;
        }
        this.xR = this.xR - ((this.xR - mX) * 1.01)+c1;
        this.yR = this.yR - ((this.yR - mY) * 1.01)+c2;
        this.x = (int)this.xR;
        this.y = (int)this.yR;
    }

    public boolean isSelected(){
        return selected;
    }

    public void select(int x, int y, int boxWidth, int boxHeight){
        if (x > this.x && x < this.x+boxWidth){
            if (y > this.y && y < this.y+boxHeight){
                selected = true;
            }
            else {
                selected = false;
            }
        }
        else{
            selected = false;
        }
    }

    public void selectVIP(){
        this.selected = true;
    }

    public void disselect(){
        this.selected = false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void move(int x, int y){
        this.x = x;
        this.xR = (double)x;
        this.y = y;
        this.yR = (double)y;
    }

    public void moveBy(int x, int y){
        this.x += x;
        this.xR += (double)x;
        this.y += y;
        this.yR += (double)y;
    }

    public void addArrow(Arrow a){
        arrows.add(a);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}