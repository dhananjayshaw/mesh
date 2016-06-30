package com.gentics.mesh.core.field.microschema;

import com.gentics.mesh.core.data.node.field.list.MicronodeGraphFieldList;
import com.gentics.mesh.core.field.DataProvider;
import com.gentics.mesh.core.field.FieldFetcher;

public interface MicronodeListFieldHelper {

	public static final FieldFetcher FETCH = (container, name) -> container.getMicronodeList(name);

	public final DataProvider FILL = (container, name) -> {
		MicronodeGraphFieldList field = container.createMicronodeFieldList(name);
		//		field.addItem(field.createMicronode(field));

		// TestDataProvider.getInstance().getMicroschemaContainers().get("vcard").getLatestVersion());

		//Micronode micronode = field.getMicronode();
		//		micronode.createString("firstName").setString("Donald");
		//		micronode.createString("lastName").setString("Duck");
	};

	public static final DataProvider CREATE_EMPTY = (container, name) -> container.createMicronodeFieldList(name);
}