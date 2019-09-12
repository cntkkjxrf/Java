public class ToDo {
    public String name;
    public String description;
    public boolean done;

    public ToDo(String nname, String ndescr, boolean flag) {
        this.name = nname;
        this.description = ndescr;
        this.done = flag;
    }

    public void showName() {
        System.out.println("Name: " + this.name);
    }
    public void show() {
        this.showName();
        System.out.println("Description: " + this.description);
        System.out.println("Done: " + this.done);
    }
    public void makeDone() {
        this.done = true;
    }
}
