package com.gentics.mesh.core.field.bool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gentics.mesh.core.data.NodeGraphFieldContainer;
import com.gentics.mesh.core.data.node.Node;
import com.gentics.mesh.core.data.node.field.basic.BooleanGraphField;
import com.gentics.mesh.core.data.service.ServerSchemaStorage;
import com.gentics.mesh.core.rest.node.NodeResponse;
import com.gentics.mesh.core.rest.node.field.impl.BooleanFieldImpl;
import com.gentics.mesh.core.rest.schema.Schema;
import com.gentics.mesh.core.rest.schema.impl.BooleanFieldSchemaImpl;
import com.gentics.mesh.json.JsonUtil;

public class BooleanGraphFieldNodeTest extends AbstractBasicDBTest {

	@Autowired
	private ServerSchemaStorage schemaStorage;

	@Test
	public void testBooleanFieldTransformation() throws Exception {
		Node node = folder("2015");
		Schema schema = node.getSchema();
		BooleanFieldSchemaImpl booleanFieldSchema = new BooleanFieldSchemaImpl();
		booleanFieldSchema.setName("booleanField");
		booleanFieldSchema.setLabel("Some boolean field");
		booleanFieldSchema.setRequired(true);
		schema.addField(booleanFieldSchema);
		node.getSchemaContainer().setSchema(schema);

		NodeGraphFieldContainer container = node.getGraphFieldContainer(english());
		BooleanGraphField field = container.createBoolean("booleanField");
		field.setBoolean(true);

		String json = getJson(node);
		assertTrue("The json should contain the boolean field but it did not.{" + json + "}", json.indexOf("booleanField\" : true") > 1);
		assertNotNull(json);
		NodeResponse response = JsonUtil.readNode(json, NodeResponse.class, schemaStorage);
		assertNotNull(response);

		com.gentics.mesh.core.rest.node.field.BooleanField deserializedNodeField = response.getField("booleanField", BooleanFieldImpl.class);
		assertNotNull(deserializedNodeField);
		assertEquals(true, deserializedNodeField.getValue());
	}

}