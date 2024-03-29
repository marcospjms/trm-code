package component.problem.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import component.problem.spec.prov.IManager;
import component.problem.spec.prov.IProblem;

public class Manager implements IManager {

    //Vari�veis de controle do componente
    private Map<String, Object> requiredInterfaces;
    private Map<String, Object> providedInterfaces;
    private static boolean controlSingleton = false;
    private static Manager instance;

    private Manager() {
        requiredInterfaces = new HashMap<String, Object>();
        providedInterfaces = new HashMap<String, Object>();

        providedInterfaces.put(IProblem.class.getName(), new FacadeIProblem());

    }

    public static synchronized Manager getInstance() {
        if (!controlSingleton) {
            instance = new Manager();
        }

        return instance;
    }

    public <E> E getProvidedInterface(Class<E> inter) {

        if (providedInterfaces.containsKey(inter.getName())) {
            return (E) providedInterfaces.get(inter.getName());
        } else {//Não contém a chave solicitada
            return null;
        }

    }

    public <E> E getRequiredInterface(Class<E> inter) {

        if (requiredInterfaces.containsKey(inter.getName())) {
            return (E) requiredInterfaces.get(inter.getName());
        } else {//Não contém a chave solicitada
            return null;
        }

    }

    public <E> void setRequiredInterface(Class<E> classType, Object object) {

        if (!(classType == null || object == null)) {
            this.requiredInterfaces.put(classType.getName(), object);
        }
    }

    public List<String> getProvidedInterfaces() {

        List<String> list = new ArrayList<String>(this.providedInterfaces.keySet());

        return list;

    }
}
