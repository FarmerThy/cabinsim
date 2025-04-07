package org.cabinsim;

import org.cloudbus.cloudsim.UtilizationModel;

public class ZUtilizationModel implements UtilizationModel {

    private double utilization;

    public ZUtilizationModel(double utilization) {
        this.utilization = utilization;
    }

    //    private Map<Pair<Algorithm, Data>, Double> utilizationMap;
//
//    public ZUtilizationModel(Map<Pair<Algorithm, Data>, Double> utilization) {
//        this.utilizationMap = utilization;
//    }
//
//    public double getUtilization(Algorithm alg, Data data) {
//        Pair<Algorithm, Data> algDataPair = Pair.of(alg, data);
//        if(utilizationMap.containsKey(algDataPair)) {
//            return utilizationMap.get(algDataPair);
//        } else {
//            System.out.println("No such utilization rate of this algorithm and data, use full utilization by default");
//            return 1;
//        }
//    }
//
//    public Map<Pair<Algorithm, Data>, Double> getUtilizationMap() {
//        return utilizationMap;
//    }
//
//    public void setUtilizationMap(Map<Pair<Algorithm, Data>, Double> utilizationMap) {
//        this.utilizationMap = utilizationMap;
//    }

    @Override
    public double getUtilization(double time) {
        return utilization;
    }

    public void setUtilization(double utilization) {
        this.utilization = utilization;
    }
}
