# CabinSim: A Simulator For Modeling The Scheduling Strategies In Computing Network #

CabinSim is a simulator designed for modeling and evaluating resource scheduling and orchestration strategies in computing power networks. It is built upon [CloudSim 7G](https://github.com/Cloudslab/cloudsim), leveraging inheritance and class extension to preserve the core logic and extensibility of CloudSim 7G with altering its original codebase sightly.

By maintaining compatibility with CloudSim 7G, CabinSim allows for seamless integration of existing or future extensions developed for CloudSim 7G, providing a robust and adaptable simulation platform.

# Main Features #
* Support for the representation of data and algorithms as independent resources
* Support for access control policy to the resources within data centers
* Support for describing the task efficiency based on the coordination of computing power, data and algorithm
* Support for customizable scheduling architectures
* Support for data exchange via JSON such as network topology input

# Scenario #

**The Computing Power Network** is an emerging network infrastructure that interconnects resource providers such as supercomputing centers and data centers. In such a system, efficient resource scheduling must take into account not only computing power but also algorithms and data—the three core elements that collectively determine resource efficiency and service-level agreement (SLA) fulfillment.

To represent these resources cohesively, we introduce the concept of **Cabin**: a virtualized environment that encapsulates specific combinations of computing power, algorithms, and data. In a computing power network, resource providers construct diverse Cabins by assembling available resources, which are then allocated to process user-submitted tasks.

In real-world scenarios, the computing power, algorithm, and data resources are diverse and exhibit various levels of coupling. The compatibility between these elements significantly affects task execution performance. However, most existing research in resource scheduling focuses solely on computing resources, with a few studies considering data placement and routing. These works rarely treat computing power, algorithms, and data as independent yet interrelated resources.

In reality, different combinations of these three elements exhibit distinct coupling characteristics and degrees of task suitability, leading to varying levels of execution efficiency and SLA compliance for the same task. For example, a handwritten digit recognition task trained on the MNIST dataset may yield different execution times when using a Transformer model versus a GAN model, even if executed on the same computing resource.

# File Structure #
All the source code are contained in directory `\modules`. We put the code from CloudSim 7G in `\modules\cloudsim` and `\modules\cloudsim-examples`, which are only slightly different from the source code of CloudSim 7G to ensure the scalability. 

We implemented the extra functionality of cabinsim in directory `\modules\cabinsim` and give some simple examples in `\modules\cabinsim-examples` to demonstrate how to use it.

Also, the code of cabinsim and cloudsim is mutually dependent. Therefore, to avoid the circular dependency in maven construction, we add a directory `\cabinsim` in the CloudSim original directory, which contains all the source code of cabinsim.

# Modification in CloudSim #
Despite that we want to maximize the elasticity and on-demand scalability capabilities of CloudSim 7G, to enable the functionality of cabinsim, we have to modify the source code in CloudSim 7G. We list the modification here.
* In class `Cloudlet`, we add two variables `requiredAlgorithm` and `requiredData` to denote the algorithm and data that this cloud needs respectively. We also add the method to access and modify the value of these two variables,  along with a new constructor function supporting data and algorithm resource.

# Notice #
This project is still under building. However, we have already finished the simulation of `data` and `algorithm` along with their `access control policy`. Users can now use cabinsim to include computing power, data and algorithm resources in their scheduling strategies. A simple example is given in `ZExample` in directory `\modules\cabinsim-examples`