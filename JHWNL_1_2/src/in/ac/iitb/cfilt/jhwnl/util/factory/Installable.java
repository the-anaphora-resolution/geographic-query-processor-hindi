/**
@author: Manish Sinha
@department: Indian Institute of Technology Bombay, Mumbai, India
*/

/*An Installable is an object that defines a framework for allowing subclasses to define an instance of themselves as the single static instance of the superclass. It is required that subclasses implement the install() method which creates an instance of the class from property file parameters (Params) and installs it.
A class that implements this interface must also define a no-arg constructor.*/


package in.ac.iitb.cfilt.jhwnl.util.factory;

import java.util.Map;

public interface Installable {  //Interface

public void install(Map params) throws Exception; 

}

