/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;

import keso.compiler.config.Attribut;
import keso.compiler.config.DomainDefinition;

import java.util.TreeMap;
import java.util.Map;
import java.util.Vector;
import java.util.Set;
import java.util.Iterator;

import java.lang.Integer;

public abstract class KesoIndexedResource extends Weavelet {

	protected IMInvoke node;
	protected IMClass clazz;
	protected IMMethod caller;
	protected IMMethod callee;
	protected IMNode args[];
	protected Coder coder;

	/** Contains all unique names and an
	 * index to which they map.
	 * Only names which are used locally in
	 * some domain will be included.
	 */
	private Map local_res_indices;
	private Map global_resource_index;


	public KesoIndexedResource(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
	}

	/**
	 * each Vector in localResources belongs to the domain whose domainid is
	 * the corresponding index into localResources
	 */
	protected void getIndexedResourceByName(Vector[] localResources, Vector globalResources,
			String resCategory, String invalidEntry) throws CompileException {

		/* We will construct a matrix with indices into the
		 * keso_{task,resource,...}_index array. The array will have one row
		 * for each used (maybe not unique!) {task,...}name and one column
		 * for each domain. If a {task,...} with the specified name
		 * can be found within the domain, the matrix will hold
		 * the index into the keso_{task,...}_index to the {task,...}'s
		 * reference, if not it will point to the {INVALID_<RESOURCE>, null}.
		 *
		 * The matrix will be put to global.c and may be referenced
		 * from all modules. Calls to get{Task,...}ByName will be resolved
		 * to a lookup into the matrix.
		 *
		 * We optimize for a few cases:
		 *  - (the single domain case was removed since we always have at least
		 *     DomainZero and one userdomain)
		 *  - if the lookedup resource is only known as a global resource,
		 *    we can resolve the lookup at compile time.
		 *  - if the getXByName service is not used this function is not called
		 *    and the index is not generated at all
		 */
		Vector domains = opts.getSysDef().getDomains();

		/* if null, this is the first call and the indices need to be created */
		if(local_res_indices==null) {
			local_res_indices = new TreeMap();

			// global index only used when global resources applicable for this resCategory
			if(globalResources!=null) global_resource_index = new TreeMap();

			// Fetch all domains, find locally used unique names
			int uniqueNames=0;
			String[] lnames;
			
			{
			int lcount=0;
			for (int i=0; i<domains.size(); i++)
				if(localResources[i].size() > lcount)
					lcount = localResources[i].size();
			lnames = new String[lcount];
			}

			for (int i=0; i<domains.size(); i++) {
				Vector local = localResources[i];

				for (int j=0; j<local.size(); j++) {
					Attribut attr = (Attribut) local.elementAt(j);

					if(local_res_indices.containsKey(attr.ident)) {
						// Same name used in other domain
						continue;
					}
					// new name discovered, assign row
					local_res_indices.put(attr.ident, new Integer(uniqueNames));
					lnames[uniqueNames] = attr.ident;
					uniqueNames++;
				}
			}

			// stuff the global_resource_index with names solely used globally
			if(globalResources!=null) {
				for (int i=0; i<globalResources.size(); i++) {
					Attribut attr = (Attribut) globalResources.elementAt(i);
					if(local_res_indices.containsKey(attr.ident)) {
						// name is locally used. Access must be resolved at runtime
						continue;
					}
					global_resource_index.put(attr.ident, attr.getIdentifier());
				}
			}

			// We have domains.size() domains and uniqueNames unique names
			// only generate the matrix in multidomain case
			coder.global_add("\n/* Matrix to map ");
			coder.global_add( resCategory );
			coder.global_add(" names and domain_ids to ");
			coder.global_add( resCategory );
			coder.global_add(" references */\n");

			coder.global_add("const unsigned char keso_dom_");
			coder.global_add(resCategory);
			coder.global_add("_index[");
			coder.global_add(uniqueNames);
			coder.global_add("]");
			if (!opts.isSingleDomainSystem()) {
				coder.global_add("[");
				coder.global_add(domains.size());
				coder.global_add("]");
			}
			coder.global_add(" = {\n");

			// domain names as headlines for the columns
			coder.global_add("/* domains\t\t");
			for (int i=0; i<domains.size(); i++) {
				if(i>0) coder.global_add(",");
				coder.global_add("\t\t");
				coder.global_add(((DomainDefinition) domains.elementAt(i)).ident);
			}
			coder.global_add("*/\n");

			// now the data for all the resources
			Set names = local_res_indices.keySet();

			for(int un=0; un<uniqueNames; un++) {
				String resName = lnames[un];
				coder.global_add("/* ");
				coder.global_add(resName);
				coder.global_add(" */ {");
				for (int i=0; i<domains.size(); i++) {
					coder.global_add("\t");

					Attribut attr=null;
					// search the domain for the a resource with this name
					for(int j=0; j<localResources[i].size(); j++) {
						if( ((Attribut) localResources[i].elementAt(j)).ident.compareTo(resName)==0) {
							attr=(Attribut) localResources[i].elementAt(j);
							break;
						}
					}

					/* if nothing was found in the domain, check the global resources
					 * we need this for resources where a global and a local resource
					 * with equal names exist and the visibility needs to be determined
					 * at runtime
					 */
					if(attr==null && globalResources!=null) {
						for(int j=0; j<globalResources.size(); j++) {
							if( ((Attribut) globalResources.elementAt(j)).ident.compareTo(resName)==0) {
								attr=(Attribut) globalResources.elementAt(j);
								break;
							}
						}
					}

					if(attr==null)
						coder.global_add(invalidEntry);
					// the Identifier of a <resource> must be a valid index into the
					// keso_<resource>_index array as must invalidEntry!
					else coder.global_add(attr.getIdentifier());
					if(i<domains.size()-1) coder.global_add(",");
				}
				coder.global_add(" },\n");
			}
			coder.global_add("};\n\n");

			// add extern declaration to global.h
			coder.global_header_add("\n#define KESO_DOM_");
			coder.global_header_add(resCategory.toUpperCase());
			coder.global_header_add("_INDEX_ROWS ");
			coder.global_header_add(uniqueNames);
			coder.global_header_add("\nextern const unsigned char keso_dom_");
			coder.global_header_add(resCategory);
			coder.global_header_add("_index[");
			coder.global_header_add(uniqueNames);
			if (!opts.isSingleDomainSystem()) {
				coder.global_header_add("][");
				coder.global_header_add(domains.size());
			}
			coder.global_header_add("];\n");
		}

		// Replace the call to get<task,...>Index with the appropriate access to the matrix
		// we need a reference to the running task to get the domain_id
		if (!(args[0] instanceof IMAConstant))
			throw new CompileException("get"+resCategory+"ByName(): first argument must be constant!");

		IMAConstant arg_resName = (IMAConstant)args[0];
		String resName = arg_resName.getString();

		if (resName == null)
			throw new CompileException("get"+resCategory+"ByName(): first argument must not be null!");

		coder.add("((object_t*)");
		Integer resRow = (Integer) local_res_indices.get(resName);

		if (resRow == null) {
			String globalID=null;
			// global only resouce?
			if (global_resource_index!=null) globalID = (String) global_resource_index.get(resName);

			if (globalID!=null) { // yes, resolve directly to keso_<resCategory>_index
				coder.add("keso_"+resCategory+"_index[" + globalID + "]");
			} else {
				String warn = "get"+resCategory+"ByName(): " + resName +" is not a known "+resCategory;
				opts.warn(warn);
				coder.add("NULL");
			}
		} else { // (maybe) domain local resource
			coder.add("keso_");
			coder.add(resCategory);
			coder.add("_index[keso_dom_");
			coder.add(resCategory);
			coder.add("_index[" );
			coder.add( resRow );
			if (!opts.isSingleDomainSystem()) coder.add("][KESO_CURRENT_DOMAIN");
			coder.add("]]");
		}
		coder.add(')');
	}

	/**
	 * Add code for a function that returns StatusType and gets
	 * one reference to a Resource object that is mapped to the
	 * corresponding OSEK resource which is then used as the
	 * parameter to the OSEK call <fnname>
	 */
	protected void retIgetResRef(String fnname, String comment, String resClass,
			String osekIDField) throws CompileException {
		IMClass res_class = repository.getClass(resClass);
		coder.add_class(res_class);

		coder.add("/* ");
		coder.add(comment);
		coder.addln("*/");

		if (opts.getTarget()==BuilderOptions.LINUX_TARGET) {
			opts.warn("FIXME: activate task not impl.");
			coder.addln("return (jint) 0;");
		} else { // FIXME not implemented for Linux
			coder.add("return (jint) ");
			coder.add(fnname);
			coder.add("(");
			coder.add_getField(res_class,"obj0",osekIDField);
			coder.addln(");");
		}
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}
}
