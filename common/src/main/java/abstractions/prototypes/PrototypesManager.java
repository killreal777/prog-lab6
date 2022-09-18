package abstractions.prototypes;

import java.util.HashMap;

public abstract class PrototypesManager<T extends CloneablePrototype> {
    private final HashMap<String, T> prototypesMap;

    protected PrototypesManager() {
        this.prototypesMap = new HashMap<>();
    }

    protected abstract void definePrototypes();

    protected void addPrototype(String name, T prototype) {
        prototypesMap.put(name, prototype);
    }

    public boolean contains(String prototypeName) {
        return prototypesMap.containsKey(prototypeName);
    }

    public T clonePrototype(String prototypeName) {
        return (T) prototypesMap.get(prototypeName).clone();
    }

    public String getPrototypesNames() {
        String out = "";
        for (String name : prototypesMap.keySet())
            out = out + name + '\n';
        return out;
    }
}
