package org.cloudbus.cloudsim.cabinsim;

import java.util.HashMap;

public class AccessControlPolicy {
    HashMap<Integer, Boolean> whiteList;
    boolean isPublic;

    public AccessControlPolicy() {
        whiteList = new HashMap<>();
        isPublic = true;
    }
    public AccessControlPolicy(HashMap<Integer, Boolean> whiteList, boolean isPublic) {
        setWhiteList(whiteList);
        this.isPublic = isPublic;
    }
    public void addToWhiteList(Integer EntityId) {
        if(!whiteList.containsKey(EntityId)) {
            whiteList.put(EntityId, true);
        }
    }
    public void deleteFromWhiteList(Integer EntityId) {
        whiteList.remove(EntityId);
    }
    public boolean accessPermit(Integer EntityId) {
        if(isPublic) {
            return true;
        } else {
            return whiteList.containsKey(EntityId);
        }
    }
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    public boolean isPublic() {
        return isPublic;
    }
    public void setWhiteList(HashMap<Integer, Boolean> whiteList) {
        if(!this.isPublic) {
            this.isPublic = true;
        }
        this.whiteList = whiteList;
    }
    public HashMap<Integer, Boolean> getWhiteList() {
        return whiteList;
    }
}
