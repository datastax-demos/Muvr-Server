# Muvr

[muvr](http://www.muvr.io/) is a demonstration of an application that uses _wearable devices_ (Pebble)—in combination with a mobile app—to submit physical (i.e. accelerometer, compass) and biological (i.e. heart rate) information to a CQRS/ES cluster to be analysed.

#### muvr-server
`muvr-server` is a CQRS/ES cluster implementation for handling large incoming data streams from mobile devices. t is event-driven: throughout the application, it uses message-passing to provide loose-coupling and asynchrony between components. It is elastic: the users and their exercises—the domain—is sharded across the cluster. It is resilient: its components can recover at the appropriate level, be it single actor, trees of actors or entire JVMs. It also uses event sourcing to ensure that even catastrophic failures and the inevitable bugs can be recovered from. It is responsive: it does not block, and it is capable of distributing the load across the cluster.

This part of the project is written using `scala` and its build tool `sbt`.

#### Other components of the system
- [muvr-ios](https://github.com/muvr/muvr-ios) iOS application showcasing mobile machine learning and data collection
- [muvr-pebble](https://github.com/muvr/muvr-pebble) Pebble application, example implementation of a wearable device
- [muvr-preclassification](https://github.com/muvr/muvr-preclassification) mobile data processing and classification
- [muvr-analytics](https://github.com/muvr/muvr-analytics) machine learning model generation for movement analytics

## Getting started
Basic information to get started is below. Please also have a look at the other components of the system to get a better understanding how everything fits together.

### Installation

##### 1. Running the server
Make sure your java installation is up to date (>= 1.7) and you got [sbt](http://www.scala-sbt.org/) installed .
```
git clone git@github.com:muvr/muvr-server.git

cd muvr-server

# Modify contact-points in main.conf
# Lines 96 and 100 of main/src/main/resources/main.conf, replace ${CASSANDRA_JOURNAL_CPS} 
# with a comma delimited list of your Cassandra Node IPs


# Starts a muvr-server on your local machine. Make sure cassandra is running  
# (e.g. using the provided cassandra containers or a local instance) 
sbt "project main" run
```
If you need to adapt the cassandra connection settings to your setup feel free to have a look at the [application configuration](https://github.com/muvr/muvr-server/blob/develop/main/src/main/resources/main.conf)

##### 2. Setup the app to connect to your local server
If you want to run the server locally you need to tell the mobile application (e.g. [muvr-ios](https://github.com/muvr/muvr-ios)) the new address of the server.  Go into the iOS `Settings`. You will find a `Muvr` category there. In those muvr settings change the `Server Url` field to your local instance (e.g. `192.168.0.34:12554`). Make sure the device can actually reach the server (e.g. by making sure they are using the same wireless).

##### 3. Check that everything works
After changing the url, try to register / login. If that works you are good to go and the app is able to talk to your local server instance.
### Issues

For any bugs or feature requests please:

1. Search the open and closed
   [issues list](https://github.com/muvr/muvr-server/issues) to see if we're
   already working on what you have uncovered.
2. Make sure the issue / feature gets filed in the relevant components (e.g. server, analytics, ios)
3. File a new [issue](https://github.com/muvr/muvr-server/issues) or contribute a 
  [pull request](https://github.com/muvr/muvr-server/pulls) 

## License
Please have a look at the [LICENSE](https://github.com/muvr/muvr-server/blob/develop/LICENSE) file.

