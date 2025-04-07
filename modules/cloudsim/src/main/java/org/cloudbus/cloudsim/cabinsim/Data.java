package org.cloudbus.cloudsim.cabinsim;

import java.util.Objects;

public class Data {
    public String name;
    public AccessControlPolicy accessControl;

    public Data(String name) {
        setName(name);
        accessControl = new AccessControlPolicy();
    }
    public Data(String name, AccessControlPolicy accessControl) {
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
        Data data = (Data) obj;
        return Objects.equals(name, data.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
