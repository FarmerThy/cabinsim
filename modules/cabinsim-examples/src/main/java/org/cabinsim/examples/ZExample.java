package org.cabinsim.examples;

import org.apache.commons.lang3.tuple.Pair;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.cabinsim.*;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.text.DecimalFormat;
import java.util.*;

/**
 * A simple example showing how to create a data center with one host and run one cloudlet on it.
 */
public class ZExample {
    public static DatacenterBroker broker;

    /** The cloudlet list. */
    private static List<Cloudlet> cloudletList;

    /**
     * Creates main() to run this example.
     *
     * @param args the args
     */
    public static void main(String[] args) {
        Log.println("Starting ZExample...");

        try {
            // First step: Initialize the CloudSim package. It should be called before creating any entities.
            int num_user = 1; // number of cloud users
            Calendar calendar = Calendar.getInstance(); // Calendar whose fields have been initialized with the current date and time.
            boolean trace_flag = false; // trace events

            /* Comment Start - Dinesh Bhagwat
             * Initialize the CloudSim library.
             * init() invokes initCommonVariable() which in turn calls initialize() (all these 3 methods are defined in CloudSim.java).
             * initialize() creates two collections - an ArrayList of SimEntity Objects (named entities which denote the simulation entities) and
             * a LinkedHashMap (named entitiesByName which denote the LinkedHashMap of the same simulation entities), with name of every SimEntity as the key.
             * initialize() creates two queues - a Queue of SimEvents (future) and another Queue of SimEvents (deferred).
             * initialize() creates a HashMap of of Predicates (with integers as keys) - these predicates are used to select a particular event from the deferred queue.
             * initialize() sets the simulation clock to 0 and running (a boolean flag) to false.
             * Once initialize() returns (note that we are in method initCommonVariable() now), a CloudSimShutDown (which is derived from SimEntity) instance is created
             * (with numuser as 1, its name as CloudSimShutDown, id as -1, and state as RUNNABLE). Then this new entity is added to the simulation
             * While being added to the simulation, its id changes to 0 (from the earlier -1). The two collections - entities and entitiesByName are updated with this SimEntity.
             * the shutdownId (whose default value was -1) is 0
             * Once initCommonVariable() returns (note that we are in method init() now), a CloudInformationService (which is also derived from SimEntity) instance is created
             * (with its name as CloudInformatinService, id as -1, and state as RUNNABLE). Then this new entity is also added to the simulation.
             * While being added to the simulation, the id of the SimEntitiy is changed to 1 (which is the next id) from its earlier value of -1.
             * The two collections - entities and entitiesByName are updated with this SimEntity.
             * the cisId(whose default value is -1) is 1
             * Comment End - Dinesh Bhagwat
             */
            CloudSim.init(num_user, calendar, trace_flag);

            // Second step: Create Datacenters
            // Datacenters are the resource providers in CloudSim. We need at
            // list one of them to run a CloudSim simulation
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            // Third step: Create Broker
            broker = new DatacenterBroker("Broker");;
            int brokerId = broker.getId();

            // Fourth step: Create one virtual machine
            List<Vm> vmlist = new ArrayList<>();

            // VM description
            int vmid = 0;
            int mips = 1000;
            long size = 10000; // image size (MB)
            int ram = 512; // vm memory (MB)
            long bw = 1000;
            int pesNumber = 1; // number of cpus
            String vmm = "Xen"; // VMM name

            // create VM
            Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerDynamicWorkload(mips, pesNumber));

            // add the VM to the vmList
            vmlist.add(vm);

            // submit vm list to the broker
            broker.submitGuestList(vmlist);

            // Fifth step: Create one Cloudlet
            List<Cloudlet> cloudletList = new ArrayList<>();

            // Cloudlet properties
            int id = 0;
            long length = 400000;
            long fileSize = 300;
            long outputSize = 300;
            UtilizationModel fullUtilizationModel = new UtilizationModelFull();

            Map<Pair<Algorithm, Data>, Double> utilizationMap = new HashMap<>();
            Algorithm alg1 = new Algorithm("Transformer");
            Algorithm alg2 = new Algorithm("Generative Adversarial Network");
            Data dat1 = new Data("MNIST");
            Data dat2 = new Data("CIFAR-10");
            utilizationMap.put(Pair.of(alg1, dat1), 0.5);
            utilizationMap.put(Pair.of(alg1, dat2), 0.6);
            utilizationMap.put(Pair.of(alg2, dat1), 0.7);
            utilizationMap.put(Pair.of(alg2, dat2), 0.4);
//            System.out.println("the utilization map is: ");
//            for(Map.Entry<Pair<Algorithm, Data>, Double> entry: utilizationMap.entrySet()) {
//                System.out.println(entry.getKey().getLeft().getName() + ',' + entry.getKey().getRight().getName() + ' ' + entry.getValue().toString());
//            }

            Algorithm alg = new Algorithm("Transformer");
            Data dat = new Data("MNIST");

            //System.out.println(utilizationMap.get(Pair.of(alg, dat)));
            ZUtilizationModel utilizationModel = new ZUtilizationModel(utilizationMap.get(Pair.of(alg, dat)));

            Cloudlet cloudlet = new Cloudlet(id, length, pesNumber, fileSize,
                    outputSize, utilizationModel, fullUtilizationModel,
                    fullUtilizationModel, alg, dat);
            cloudlet.setUserId(brokerId);
            cloudlet.setGuestId(vmid);

            // add the cloudlet to the list
            cloudletList.add(cloudlet);

            // submit cloudlet list to the broker
            broker.submitCloudletList(cloudletList);

            // Sixth step: Starts the simulation
            CloudSim.startSimulation();

            CloudSim.stopSimulation();

            //Final step: Print results when simulation is over
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);

            Log.println("ZExample finished!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.println("Unwanted errors happen");
        }
    }

    /**
     * Creates the datacenter.
     *
     * @param name the name
     *
     * @return the datacenter
     */
    private static Datacenter createDatacenter(String name) {

        // Here are the steps needed to create a PowerDatacenter:
        // 1. We need to create a list to store
        // our machine
        List<Host> hostList = new ArrayList<>();

        // 2. A Machine contains one or more PEs or CPUs/Cores.
        // In this example, it will have only one core.
        List<Pe> peList = new ArrayList<>();

        int mips = 1000;

        // 3. Create PEs and add these into a list.
        peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

        // 4. Create Host with its id and list of PEs and add them to the list
        // of machines
        int hostId = 0;
        int ram = 2048; // host memory (MB)
        long storage = 1000000; // host storage
        int bw = 10000;

        hostList.add(
                new Host(
                        hostId,
                        new RamProvisionerSimple(ram),
                        new BwProvisionerSimple(bw),
                        storage,
                        peList,
                        new VmSchedulerTimeShared(peList)
                )
        ); // This is our machine

        // 5. Create a DatacenterCharacteristics object that stores the
        // properties of a data center: architecture, OS, list of
        // Machines, allocation policy: time- or space-shared, time zone
        // and its price (G$/Pe time unit).
        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen";
        double time_zone = 10.0; // time zone this resource located
        double cost = 3.0; // the cost of using processing in this resource
        double costPerMem = 0.05; // the cost of using memory in this resource
        double costPerStorage = 0.001; // the cost of using storage in this
        // resource
        double costPerBw = 0.0; // the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<>(); // we are not adding SAN
        // devices by now

        List<Algorithm> algorithmList = new ArrayList<>();
        List<Data> dataList = new ArrayList<>();
        Algorithm alg1 = new Algorithm("Transformer");
        Algorithm alg2 = new Algorithm("Generative Adversarial Network");
        algorithmList.add(alg1);
        algorithmList.add(alg2);
        Data dat1 = new Data("MNIST");
        Data dat2 = new Data("CIFAR-10");
        dataList.add(dat1);
        dataList.add(dat2);
        ZDatacenterCharacteristics characteristics = new ZDatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw,
                algorithmList, dataList
        );
//        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
//                arch, os, vmm, hostList, time_zone, cost, costPerMem,
//                costPerStorage, costPerBw);

        // 6. Finally, we need to create a PowerDatacenter object.
        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }

    /**
     * Prints the Cloudlet objects.
     *
     * @param list list of Cloudlets
     */
    private static void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        Log.println();
        Log.println("========== OUTPUT ==========");
        Log.println("Cloudlet ID" + indent + "STATUS" + indent
                + "Data center ID" + indent + "VM ID" + indent + "Time" + indent
                + "Start Time" + indent + "Finish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (Cloudlet value : list) {
            cloudlet = value;
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getStatus() == Cloudlet.CloudletStatus.SUCCESS) {
                Log.print("SUCCESS");

                Log.println(indent + indent + cloudlet.getResourceId()
                        + indent + indent + indent + cloudlet.getGuestId()
                        + indent + indent
                        + dft.format(cloudlet.getActualCPUTime()) + indent
                        + indent + dft.format(cloudlet.getExecStartTime())
                        + indent + indent
                        + dft.format(cloudlet.getExecFinishTime()));
            }
        }
    }
}