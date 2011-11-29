/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trm.environment.impl;

import trm.problem.spec.prov.IManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trm.environment.spec.prov.IEnvironment;

/**
 *
 * @author ttmo
 */
public class Manager implements IManager {

    //Variáveis de controle do componente
    private Map<String, Object> requiredInterfaces;
    private Map<String, Object> providedInterfaces;

    protected Manager() {
        requiredInterfaces = new HashMap<String, Object>();
        providedInterfaces = new HashMap<String, Object>();

        this.providedInterfaces.put(IEnvironment.class.getName(), new FacadeIEnvironment());


    }
    //Controle singleton
    private static boolean controlSingleton = false;
    private static Manager instance;

    //Singleton do Manager
    protected static Manager getManager() {

        if (!controlSingleton) {
            instance = new Manager();
        }

        return instance;

    }

    public <E> E getProvidedInterface(Class<E> inter) {

        if (providedInterfaces.containsKey(inter.getName())) {
            return (E)providedInterfaces.get(inter.getName());
        } else {//Não contém a chave solicitada
            return null;
        }

    }

    public Object getRequiredInterface(String inter) {
        if (requiredInterfaces.containsKey(inter)) {
            return requiredInterfaces.get(inter);
        } else {//Não contém a chave solicitada
            return null;
        }

    }

    public void setRequiredInterface(String inter, Object object) {

        if (!(inter == null || object == null)) {
            this.requiredInterfaces.put(inter, object);

        }

    }

    public List<String> getProvidedInterfaces() {

        List<String> list = new ArrayList<String>(this.providedInterfaces.keySet());

        return list;

    }
}