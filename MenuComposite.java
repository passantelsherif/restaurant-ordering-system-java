import java.util.*;

// Composite (menu that can contain items or submenus)
public class MenuComposite implements IMenuComponent {
    private String name;
    private List<IMenuComponent> children = new ArrayList<>();

    public MenuComposite(String name) { this.name = name; }

    public void add(IMenuComponent c) { children.add(c); }
    public void remove(IMenuComponent c) { children.remove(c); }

    @Override public String getName() { return name; }
    @Override public double getPrice() { return 0; }

    @Override
    public void print(String indent) {
        System.out.println(indent + "[Menu] " + name);
        for (IMenuComponent c : children) c.print(indent + "   ");
    }
    
    public List<IMenuComponent> getChildren() { return children; }
}