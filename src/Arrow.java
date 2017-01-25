
public class Arrow {

    private String name;
    Entity source;
    Entity target;

    public Arrow(Entity source, Entity target) {
        this.source = source;
        this.target = target;
    }

    public String getSourceName() {
        return this.source.getName();
    }
    public Entity getSource() {
        return this.source;
    }

    public String getTargetName() {
        return this.target.getName();
    }

    public Entity getTarget() {
        return this.target;
    }

}