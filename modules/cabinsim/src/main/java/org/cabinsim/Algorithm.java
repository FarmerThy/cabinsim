package org.cabinsim;

import java.util.Objects;

public class Algorithm {
    public String name;
    public AccessControlPolicy accessControl;

    public Algorithm(String name) {
        setName(name);
        accessControl = new AccessControlPolicy();
    }
    public Algorithm(String name, AccessControlPolicy accessControl) {
        setName(name);
        setAccessControl(accessControl);
    }

    public boolean accessPermit(int EntityId) {
        return accessControl.accessPermit(EntityId);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Algorithm alg = (Algorithm) obj;
        return Objects.equals(name, alg.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); // 生成hashCode，通常与equals方法一起重写
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAccessControl(AccessControlPolicy accessControl) {
        this.accessControl = accessControl;
    }

    public AccessControlPolicy getAccessControl() {
        return accessControl;
    }
}
