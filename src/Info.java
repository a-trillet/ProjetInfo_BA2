public abstract class Info {
    private MapClickable object;




    public Info(MapClickable object){
        this.object = object;

    }
    public abstract String listString();

    public MapClickable getObject() {
        return object;
    }


}
