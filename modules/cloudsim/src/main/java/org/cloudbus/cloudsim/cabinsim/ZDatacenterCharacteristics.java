package org.cloudbus.cloudsim.cabinsim;

import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.core.HostEntity;

import java.util.ArrayList;
import java.util.List;

public class ZDatacenterCharacteristics extends DatacenterCharacteristics{
    List<Algorithm> algorithmList = new ArrayList<>();
    List<Data> dataList = new ArrayList<>();
    /**
     * Creates a new DatacenterCharacteristics object. If the time zone is invalid, then by
     * default, it will be GMT+0.
     *
     * @param architecture   the architecture of the datacenter
     * @param os             the operating system used on the datacenter's PMs
     * @param vmm            the virtual machine monitor used
     * @param hostList       list of machines in the datacenter
     * @param timeZone       local time zone of a user that owns this reservation. Time zone should be of
     *                       range [GMT-12 ... GMT+13]
     * @param costPerSec     the cost per sec of CPU use in the datacenter
     * @param costPerMem     the cost to use memory in the datacenter
     * @param costPerStorage the cost to use storage in the datacenter
     * @param costPerBw      the cost of each byte of bandwidth (bw) consumed
     * @pre architecture != null
     * @pre OS != null
     * @pre VMM != null
     * @pre machineList != null
     * @pre timeZone >= -12 && timeZone <= 13
     * @pre costPerSec >= 0.0
     * @pre costPerMem >= 0
     * @pre costPerStorage >= 0
     * @post $none
     */
    public ZDatacenterCharacteristics(
            String architecture,
            String os,
            String vmm,
            List<? extends HostEntity> hostList,
            double timeZone,
            double costPerSec,
            double costPerMem,
            double costPerStorage,
            double costPerBw) {
        super(architecture, os, vmm, hostList, timeZone, costPerSec, costPerMem, costPerStorage, costPerBw);
    }
    public ZDatacenterCharacteristics(
            String architecture,
            String os,
            String vmm,
            List<? extends HostEntity> hostList,
            double timeZone,
            double costPerSec,
            double costPerMem,
            double costPerStorage,
            double costPerBw,
            List<Algorithm> algorithmList,
            List<Data> dataList
    ) {
        super(architecture, os, vmm, hostList, timeZone, costPerSec, costPerMem, costPerStorage, costPerBw);
        setAlgorithmList(algorithmList);
        setDataList(dataList);
    }
    public List<Algorithm> getAlgorithmList() {
        return algorithmList;
    }
    public List<Data> getDataList() {
        return dataList;
    }
    public void setAlgorithmList(List<Algorithm> algorithmList) {
        this.algorithmList = algorithmList;
    }
    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }
}
