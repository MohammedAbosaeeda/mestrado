package keso.editor.configurationloader;

import java.util.Iterator;
import java.util.Vector;

import keso.compiler.BuilderOptions;
import keso.compiler.CompileException;
import keso.compiler.config.*;
import keso.compiler.config.parser.ConfigReader;
import keso.editor.data.*;
import keso.editor.property.*;
import keso.editor.property.arrayproperty.IntegerKesoArrayProperty;
import keso.editor.property.complexproperty.*;
import keso.editor.property.propertymanager.KesoPropertyManager;

public abstract class KesoConfigurationLoader {
	public static WorldKesoData loadFile(String filename) throws CompileException {
		WorldKesoData world = null;
	
		WorldDefinition wd = ConfigReader.parseDefinition(filename);
		world = (WorldKesoData) KesoConfigurationLoader.convert((Set) wd);
	
		return world;
	}

	private static IKesoData convert(Set sd) {
		IKesoData data = null;
		if (sd instanceof WorldDefinition) {
			data = KesoConfigurationLoader.convert((WorldDefinition) sd);
		} else if (sd instanceof SystemDefinition) {
			data = KesoConfigurationLoader.convert((SystemDefinition) sd);
		} else if (sd instanceof PublicDomain) {
			data = KesoConfigurationLoader.convert((PublicDomain) sd);
		} else if (sd instanceof DomainDefinition) {
			data = KesoConfigurationLoader.convert((DomainDefinition) sd);
		} else if (sd instanceof NetworkDefinition) {
			data = KesoConfigurationLoader.convert((NetworkDefinition) sd);
		} else if (sd instanceof CounterDefinition) {
			data = KesoConfigurationLoader.convert((CounterDefinition) sd);
		} else if (sd instanceof EventDefinition) {
			data = KesoConfigurationLoader.convert((EventDefinition) sd);
		} else if (sd instanceof AppmodeDefinition) {
			data = KesoConfigurationLoader.convert((AppmodeDefinition) sd);
		} else if (sd instanceof OSDefinition) {
			data = KesoConfigurationLoader.convert((OSDefinition) sd);
		} else if (sd instanceof TaskDefinition) {
			data = KesoConfigurationLoader.convert((TaskDefinition) sd);
		} else if (sd instanceof ResourceDefinition) {
			data = KesoConfigurationLoader.convert((ResourceDefinition) sd);
		} else if (sd instanceof AlarmDefinition) {
			data = KesoConfigurationLoader.convert((AlarmDefinition) sd);
		} else if (sd instanceof ISRDefinition) {
			data = KesoConfigurationLoader.convert((ISRDefinition) sd);
		} else if (sd instanceof ImportDefinition) {
			data = KesoConfigurationLoader.convert((ImportDefinition) sd);
		} else if (sd instanceof ServiceDefinition) {
			data = KesoConfigurationLoader.convert((ServiceDefinition) sd);
		}
		/*
		if (data != null) {
			KesoPropertyManager.fillUpData(data);
		}
		*/
		return data;
	}
	
	
	public static WorldKesoData convert(WorldDefinition oldworld) {
		WorldKesoData world = new WorldKesoData(oldworld.ident);
		Attribut [] attributes = oldworld.getAllAttributes();
		for(int i = 0; i < attributes.length; i++) {
			if (attributes[i] instanceof ComplexAttribute) {
				world.addProperty(KesoConfigurationLoader.convertProperty(world, (Set) attributes[i]));
			} else if (attributes[i] instanceof Set) {
				world.addChild(KesoConfigurationLoader.convert((Set) attributes[i]));
			} else if (attributes[i] instanceof Attribut) {
				world.addProperty(KesoConfigurationLoader.convertProperty(world, (Attribut) attributes[i]));
			}
		}
		return world;
	}
	
	public static SystemKesoData convert(SystemDefinition oldsystem) {
		SystemKesoData system = new SystemKesoData(oldsystem.ident);		
		Vector children = new Vector();
		
		if (oldsystem.getAppmodes() != null) {
			children.addAll(oldsystem.getAppmodes());
		} 
		if (oldsystem.getCounters() != null) {
			children.addAll(oldsystem.getCounters());
		} 
		if (oldsystem.getDomains() != null) {
			children.addAll(oldsystem.getDomains());
		} 
		if (oldsystem.getEvents() != null) {
			children.addAll(oldsystem.getEvents());
		} 
		if (oldsystem.getOSDef() != null) {
			children.add(oldsystem.getOSDef());
		}
		for (Iterator i = children.iterator(); i.hasNext(); ) {
			Set child = (Set) i.next();
			system.addChild(KesoConfigurationLoader.convert(child));
		}
		
		Attribut [] attributes = oldsystem.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			system.addProperty(KesoConfigurationLoader.convertProperty(system, attributes[i]));
		}
		
		return system;
	}
	
	public static DomainKesoData convert(DomainDefinition olddomain) {
		DomainKesoData domain = new DomainKesoData(olddomain.ident);
		
		Vector children = new Vector();
		if (olddomain.getImports() != null) {
			children.addAll(olddomain.getImports());
		} 
		if (olddomain.getISRs() != null) {
			children.addAll(olddomain.getISRs());
		} 
		if (olddomain.getResources() != null) {
			children.addAll(olddomain.getResources());
		} 
		if (olddomain.getServices() != null) {
			children.addAll(olddomain.getServices());
		}
		if (olddomain.getTasks() != null) {
			children.addAll(olddomain.getTasks());
		}
		if (olddomain.getAlarms() != null) {
			children.addAll(olddomain.getAlarms());
		}
		for (Iterator i = children.iterator(); i.hasNext(); ) {
			Set child = (Set) i.next();
			domain.addChild(KesoConfigurationLoader.convert(child));
		}
		Attribut [] attributes = olddomain.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			domain.addProperty(KesoConfigurationLoader.convertProperty(domain, attributes[i]));
		}
		if (olddomain.getHeap() != null) {
			domain.addProperty(KesoConfigurationLoader.convertPropertyHeap(domain, olddomain.getHeap()));
		}
		
		return domain;
	}
	
	public static PublicDomainKesoData convert(PublicDomain oldpublic) {
		PublicDomainKesoData publicdomain = new PublicDomainKesoData(oldpublic.ident);
		
		Vector children = new Vector();
		if (oldpublic.getImports() != null) {
			children.addAll(oldpublic.getImports());
		} 
		if (oldpublic.getISRs() != null) {
			children.addAll(oldpublic.getISRs());
		} 
		if (oldpublic.getResources() != null) {
			children.addAll(oldpublic.getResources());
		} 
		if (oldpublic.getServices() != null) {
			children.addAll(oldpublic.getServices());
		}
		if (oldpublic.getTasks() != null) {
			children.addAll(oldpublic.getTasks());
		}
		if (oldpublic.getAlarms() != null) {
			children.addAll(oldpublic.getAlarms());
		}
		for (Iterator i = children.iterator(); i.hasNext(); ) {
			Set child = (Set) i.next();
			publicdomain.addChild(KesoConfigurationLoader.convert(child));
		}
		Attribut [] attributes = oldpublic.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			publicdomain.addProperty(KesoConfigurationLoader.convertProperty(publicdomain, attributes[i]));
		}
		if (oldpublic.getHeap() != null) {
			publicdomain.addProperty(KesoConfigurationLoader.convertPropertyHeap(publicdomain, oldpublic.getHeap()));
		}
		
		return publicdomain;
	}
	
	public static NetworkKesoData convert(NetworkDefinition oldnetwork) {
		NetworkKesoData network = new NetworkKesoData(oldnetwork.ident);
		Vector children = new Vector();
		if (oldnetwork.getServices() != null) {
			children.addAll(oldnetwork.getServices());
		} 
		for (Iterator i = children.iterator(); i.hasNext(); ) {
			Set child = (Set) i.next();
			network.addChild(KesoConfigurationLoader.convert(child));
		}
		Attribut [] attributes = oldnetwork.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			network.addProperty(KesoConfigurationLoader.convertProperty(network, attributes[i]));
		}
		return network;
	}
	
	public static CounterKesoData convert(CounterDefinition oldcounter) {
		CounterKesoData counter = new CounterKesoData(oldcounter.ident);
		Attribut [] attributes = oldcounter.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			counter.addProperty(KesoConfigurationLoader.convertProperty(counter, attributes[i]));
		}
		return counter;
	}
	
	public static EventKesoData convert(EventDefinition oldoevent) {
		EventKesoData event = new EventKesoData(oldoevent.ident);
		Attribut [] attributes = oldoevent.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			event.addProperty(KesoConfigurationLoader.convertProperty(event, attributes[i]));
		}
		return event;
	}
	
	public static AppmodeKesoData convert(AppmodeDefinition oldmode) {
		AppmodeKesoData mode = new AppmodeKesoData(oldmode.ident);
		Attribut [] attributes = oldmode.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			mode.addProperty(KesoConfigurationLoader.convertProperty(mode, attributes[i]));
		}
		return mode;
	}
	
	public static OsekOsKesoData convert(OSDefinition oldos) {
		OsekOsKesoData osekos = new OsekOsKesoData(oldos.ident);
		Attribut [] attributes = oldos.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			osekos.addProperty(KesoConfigurationLoader.convertProperty(osekos, attributes[i]));
		}
		attributes = (Attribut []) oldos.getAllHooks()[0];
		for (int i = 0; i < attributes.length; i++) {
			osekos.addProperty(KesoConfigurationLoader.convertProperty(osekos, attributes[i]));
		}
		return osekos;
	}
	
	public static TaskKesoData convert(TaskDefinition oldtask) {
		TaskKesoData task = new TaskKesoData(oldtask.ident);
		Vector properties = new Vector();
		if (oldtask.getResources() != null) {
			properties.addAll(oldtask.getResources());
		} 
		if (oldtask.getMessages() != null) {
			properties.addAll(oldtask.getMessages());
		} 
		if (oldtask.getEvents() != null) {
			properties.addAll(oldtask.getEvents());
		}
		if (oldtask.getAutoStart() != null) {
			properties.add(oldtask.getAutoStart());
		}
		for (Iterator i = properties.iterator(); i.hasNext(); ) {
			Attribut property = (Attribut) i.next();
			task.addProperty(KesoConfigurationLoader.convertProperty(task, property));
		}
		Attribut [] attributes = oldtask.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			task.addProperty(KesoConfigurationLoader.convertProperty(task, attributes[i]));
		}
		return task;
	}
	
	public static ResourceKesoData convert(ResourceDefinition oldresource) {
		ResourceKesoData resource = new ResourceKesoData(oldresource.ident);
		Attribut [] attributes = oldresource.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			resource.addProperty(KesoConfigurationLoader.convertProperty(resource, attributes[i]));
		}
		return resource;
	}
	
	public static AlarmKesoData convert(AlarmDefinition oldalarm) {
		AlarmKesoData alarm = new AlarmKesoData(oldalarm.ident);
		if (oldalarm.getAction() != null) {
			alarm.addProperty(KesoConfigurationLoader.convertProperty(alarm, (Attribut) oldalarm.getAction()));
		}
		if (oldalarm.getAutoStart() != null) {
			alarm.addProperty(KesoConfigurationLoader.convertProperty(alarm, (Attribut) oldalarm.getAutoStart()));
		}
		Attribut [] attributes = oldalarm.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			alarm.addProperty(KesoConfigurationLoader.convertProperty(alarm, attributes[i]));
		}
		return alarm;
	}
	
	public static IsrKesoData convert(ISRDefinition oldisr) {
		IsrKesoData isr = new IsrKesoData(oldisr.ident);
		Vector properties = new Vector();
		if (oldisr.getUsedMessages() != null) {
			properties.addAll(oldisr.getUsedMessages());
		} 
		if (oldisr.getUsedResources() != null) {
			properties.addAll(oldisr.getUsedResources());
		} 
		for (Iterator i = properties.iterator(); i.hasNext(); ) {
			Attribut property = (Attribut) i.next();
			isr.addProperty(KesoConfigurationLoader.convertProperty(isr, property));
		}
		Attribut [] attributes = oldisr.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			isr.addProperty(KesoConfigurationLoader.convertProperty(isr, attributes[i]));
		}
		return isr;
	}
	
	public static ImportKesoData convert(ImportDefinition oldimport) {
		ImportKesoData importdata = new ImportKesoData(oldimport.ident);
		Attribut [] attributes = oldimport.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			importdata.addProperty(KesoConfigurationLoader.convertProperty(importdata, attributes[i]));
		}
		return importdata;
	}
	
	public static ServiceKesoData convert(ServiceDefinition oldservice) {
		ServiceKesoData service = new ServiceKesoData(oldservice.ident);
		Attribut [] attributes = oldservice.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			service.addProperty(KesoConfigurationLoader.convertProperty(service, attributes[i]));
		}
		return service;
	}
	
	
	
	private static IKesoProperty convertProperty(IKesoData parent, Attribut at) {
		if (at instanceof IntegerAttr) {
			return KesoConfigurationLoader.convertProperty(parent, (IntegerAttr) at);
		} else if (at instanceof StringAttr) {
			return KesoConfigurationLoader.convertProperty(parent, (StringAttr) at);
		} else if (at instanceof ArrayAttr) {
			return KesoConfigurationLoader.convertProperty(parent, (ArrayAttr) at);
		} else if (at instanceof AlarmActionAttribute) {
			return KesoConfigurationLoader.convertProperty(parent, (AlarmActionAttribute) at);
		} else if (at instanceof ComplexBoolAttribute) {
			return KesoConfigurationLoader.convertProperty(parent, (ComplexBoolAttribute) at);
		} else if (at instanceof ComplexAttribute) {
			return KesoConfigurationLoader.convertProperty(parent, (ComplexAttribute) at);
		} 
		return null;
	}
	
	private static IKesoProperty convertProperty(IKesoComplexProperty property, Attribut at) {
		if (at instanceof IntegerAttr) {
			return KesoConfigurationLoader.convertProperty(property, (IntegerAttr) at);
		} else if (at instanceof StringAttr) {
			return KesoConfigurationLoader.convertProperty(property, (StringAttr) at);
		} else if (at instanceof ArrayAttr) {
			return KesoConfigurationLoader.convertProperty(property, (ArrayAttr) at);
		} else if (at instanceof AlarmActionAttribute) {
			return KesoConfigurationLoader.convertProperty(property, (AlarmActionAttribute) at);
		} else if (at instanceof ComplexBoolAttribute) {
			return KesoConfigurationLoader.convertProperty(property, (ComplexBoolAttribute) at);
		} else if (at instanceof ComplexAttribute) {
			return KesoConfigurationLoader.convertProperty(property, (ComplexAttribute) at);
		} 
		return null;
	}
	
	private static IntegerKesoProperty convertProperty(IKesoData parent, IntegerAttr oldinteger) {
		IntegerKesoProperty integer = new IntegerKesoProperty(oldinteger.ident, oldinteger.valueInt());
		integer.setOwner(parent);
		return integer;
	}
	
	private static StringKesoProperty convertProperty(IKesoData parent, StringAttr oldstring) {
		StringKesoProperty string = new StringKesoProperty(oldstring.ident, oldstring.valueString());
		string.setOwner(parent);
		return string;
	}
	
	private static IntegerKesoArrayProperty convertProperty(IKesoData parent, ArrayAttr oldintarray) {
		IntegerKesoArrayProperty intarray = new IntegerKesoArrayProperty(oldintarray.ident);
		intarray.setOwner(parent);
		int [] values = oldintarray.values();
		for (int i = 0; i < values.length; i++) {
			intarray.addItem(values[i]);
		}
		return intarray;
	}
	
	private static StringKesoComplexProperty convertProperty(IKesoData parent, ComplexAttribute oldstring) {
		StringKesoComplexProperty string = new StringKesoComplexProperty(oldstring.ident, oldstring.value);
		string.setOwner(parent);
		Attribut [] attributes = oldstring.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			string.add(KesoConfigurationLoader.convertProperty(string, attributes[i]));
		}
		return string;
	}
	
	private static BooleanKesoComplexProperty convertProperty(IKesoData parent, ComplexBoolAttribute oldboolean) {
		BooleanKesoComplexProperty newboolean = new BooleanKesoComplexProperty(oldboolean.ident, oldboolean.setting);
		newboolean.setOwner(parent);
		Attribut [] attributes = oldboolean.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			newboolean.add(KesoConfigurationLoader.convertProperty(newboolean, attributes[i]));
		}
		return newboolean;
	}
	
	private static UnquotedStringKesoComplexProperty convertProperty(IKesoData parent, AlarmActionAttribute oldaction) {
		UnquotedStringKesoComplexProperty string = new UnquotedStringKesoComplexProperty(oldaction.ident, oldaction.value);
		string.setOwner(parent);
		Attribut [] attributes = oldaction.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			string.add(KesoConfigurationLoader.convertProperty(string, attributes[i]));
		}
		return string;
	}
	
	private static UnquotedStringKesoComplexProperty convertPropertyHeap(IKesoData parent, ComplexAttribute oldheap) {
		UnquotedStringKesoComplexProperty string = new UnquotedStringKesoComplexProperty(oldheap.ident, oldheap.value);
		string.setOwner(parent);
		Attribut [] attributes = oldheap.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			string.add(KesoConfigurationLoader.convertProperty(string, attributes[i]));
		}
		return string;
	}
	
	
	
	
	
	private static IntegerKesoProperty convertProperty(IKesoComplexProperty parent, IntegerAttr oldinteger) {
		IntegerKesoProperty integer = new IntegerKesoProperty(parent, oldinteger.ident, oldinteger.valueInt());
		return integer;
	}
	
	private static StringKesoProperty convertProperty(IKesoComplexProperty parent, StringAttr oldstring) {
		StringKesoProperty string = new StringKesoProperty(parent, oldstring.ident, oldstring.valueString());
		return string;
	}
	
	private static IntegerKesoArrayProperty convertProperty(IKesoComplexProperty parent, ArrayAttr oldintarray) {
		IntegerKesoArrayProperty intarray = new IntegerKesoArrayProperty(parent, oldintarray.ident);
		int [] values = oldintarray.values();
		for (int i = 0; i < values.length; i++) {
			intarray.addItem(values[i]);
		}
		return intarray;
	}
	
	private static StringKesoComplexProperty convertProperty(IKesoComplexProperty parent, ComplexAttribute oldstring) {
		StringKesoComplexProperty string = new StringKesoComplexProperty(parent, oldstring.ident, oldstring.value);
		Attribut [] attributes = oldstring.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			string.add(KesoConfigurationLoader.convertProperty(string, attributes[i]));
		}
		return string;
	}
	
	private static BooleanKesoComplexProperty convertProperty(IKesoComplexProperty parent, ComplexBoolAttribute oldboolean) {
		BooleanKesoComplexProperty newboolean = new BooleanKesoComplexProperty(oldboolean.ident, oldboolean.setting);
		Attribut [] attributes = oldboolean.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			newboolean.add(KesoConfigurationLoader.convertProperty(newboolean, attributes[i]));
		}
		return newboolean;
	}
	
	private static UnquotedStringKesoComplexProperty convertProperty(IKesoComplexProperty parent, AlarmActionAttribute oldaction) {
		UnquotedStringKesoComplexProperty string = new UnquotedStringKesoComplexProperty(oldaction.ident, oldaction.value);
		Attribut [] attributes = oldaction.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			string.add(KesoConfigurationLoader.convertProperty(string, attributes[i]));
		}
		return string;
	}
	
	private static UnquotedStringKesoComplexProperty convertPropertyHeap(IKesoComplexProperty parent, ComplexAttribute oldheap) {
		UnquotedStringKesoComplexProperty string = new UnquotedStringKesoComplexProperty(parent, oldheap.ident, oldheap.value);
		Attribut [] attributes = oldheap.getAllAttributes();
		for (int i = 0; i < attributes.length; i++) {
			string.add(KesoConfigurationLoader.convertProperty(string, attributes[i]));
		}
		return string;
	}

}
