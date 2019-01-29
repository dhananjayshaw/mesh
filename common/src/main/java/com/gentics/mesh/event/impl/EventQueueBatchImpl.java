package com.gentics.mesh.event.impl;

import static com.gentics.mesh.core.data.search.SearchQueueEntryAction.DELETE_ACTION;
import static com.gentics.mesh.core.data.search.SearchQueueEntryAction.STORE_ACTION;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gentics.mesh.core.data.ContainerType;
import com.gentics.mesh.core.data.IndexableElement;
import com.gentics.mesh.core.data.NodeGraphFieldContainer;
import com.gentics.mesh.core.data.Project;
import com.gentics.mesh.core.data.Tag;
import com.gentics.mesh.core.data.TagFamily;
import com.gentics.mesh.core.data.node.Node;
import com.gentics.mesh.core.data.search.BulkEventQueueEntry;
import com.gentics.mesh.core.data.search.CreateIndexEntry;
import com.gentics.mesh.core.data.search.DropIndexEntry;
import com.gentics.mesh.core.data.search.MoveDocumentEntry;
import com.gentics.mesh.core.data.search.SearchQueueEntry;
import com.gentics.mesh.core.data.search.SearchQueueEntryAction;
import com.gentics.mesh.core.data.search.SeperateSearchQueueEntry;
import com.gentics.mesh.core.data.search.UpdateDocumentEntry;
import com.gentics.mesh.core.data.search.context.EntryContext;
import com.gentics.mesh.core.data.search.context.GenericEntryContext;
import com.gentics.mesh.core.data.search.context.MoveEntryContext;
import com.gentics.mesh.core.data.search.context.impl.GenericEntryContextImpl;
import com.gentics.mesh.core.data.search.context.impl.MoveEntryContextImpl;
import com.gentics.mesh.core.rest.schema.Schema;
import com.gentics.mesh.event.EventQueueBatch;

import io.reactivex.Completable;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @see EventQueueBatch
 */
public class EventQueueBatchImpl implements EventQueueBatch {

	private static final Logger log = LoggerFactory.getLogger(EventQueueBatchImpl.class);

	private String batchId;
	private List<BulkEventQueueEntry<?>> bulkEntries = new ArrayList<>();
	private List<SeperateSearchQueueEntry<?>> seperateEntries = new ArrayList<>();

	public EventQueueBatchImpl() {
	}

//	@Override
//	public EventQueueBatch createIndex(String indexName, Class<?> elementClass) {
//		CreateIndexEntry entry = new CreateIndexEntryImpl(registry.getForClass(elementClass), indexName);
//		addEntry(entry);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch createNodeIndex(String projectUuid, String branchUuid, String versionUuid,
//			ContainerType type, Schema schema) {
//		String indexName = NodeGraphFieldContainer.composeIndexName(projectUuid, branchUuid, versionUuid, type);
//		CreateIndexEntry entry = new CreateIndexEntryImpl(nodeContainerIndexHandler, indexName);
//		entry.setSchema(schema);
//		// entry.getContext().setSchemaContainerVersionUuid(versionUuid);
//		addEntry(entry);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch dropIndex(String indexName) {
//		DropIndexEntry entry = new DropIndexEntryImpl(commonHandler, indexName);
//		addEntry(entry);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch store(Node node, String branchUuid, ContainerType type, boolean addRelatedElements) {
//		GenericEntryContextImpl context = new GenericEntryContextImpl();
//		context.setContainerType(type);
//		context.setBranchUuid(branchUuid);
//		context.setProjectUuid(node.getProject().getUuid());
//		store((IndexableElement) node, context, addRelatedElements);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch move(NodeGraphFieldContainer oldContainer, NodeGraphFieldContainer newContainer,
//			String branchUuid, ContainerType type) {
//		MoveEntryContext context = new MoveEntryContextImpl();
//		context.setContainerType(type);
//		context.setBranchUuid(branchUuid);
//		context.setOldContainer(oldContainer);
//		context.setNewContainer(newContainer);
//		MoveDocumentEntry entry = new MoveDocumentEntryImpl(nodeContainerIndexHandler, context);
//		addEntry(entry);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch store(NodeGraphFieldContainer container, String branchUuid, ContainerType type,
//			boolean addRelatedElements) {
//		Node node = container.getParentNode();
//		GenericEntryContextImpl context = new GenericEntryContextImpl();
//		context.setContainerType(type);
//		context.setBranchUuid(branchUuid);
//		context.setLanguageTag(container.getLanguageTag());
//		context.setSchemaContainerVersionUuid(container.getSchemaContainerVersion().getUuid());
//		context.setProjectUuid(node.getProject().getUuid());
//		store((IndexableElement) node, context, addRelatedElements);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch delete(Tag tag, boolean addRelatedEntries) {
//		// We need to add the project uuid to the context because the index handler for
//		// tags will not be able to
//		// determine the project uuid once the tag has been removed from the graph.
//		GenericEntryContextImpl context = new GenericEntryContextImpl();
//		context.setProjectUuid(tag.getProject().getUuid());
//		delete((IndexableElement) tag, context, addRelatedEntries);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch delete(TagFamily tagFymily, boolean addRelatedEntries) {
//		// We need to add the project uuid to the context because the index handler for
//		// tagfamilies will not be able to
//		// determine the project uuid once the tagfamily has been removed from the
//		// graph.
//		GenericEntryContextImpl context = new GenericEntryContextImpl();
//		context.setProjectUuid(tagFymily.getProject().getUuid());
//		delete((IndexableElement) tagFymily, context, addRelatedEntries);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch delete(NodeGraphFieldContainer container, String branchUuid, ContainerType type,
//			boolean addRelatedEntries) {
//		GenericEntryContextImpl context = new GenericEntryContextImpl();
//		context.setContainerType(type);
//		context.setProjectUuid(container.getParentNode().getProject().getUuid());
//		context.setBranchUuid(branchUuid);
//		context.setSchemaContainerVersionUuid(container.getSchemaContainerVersion().getUuid());
//		context.setLanguageTag(container.getLanguageTag());
//		delete((IndexableElement) container.getParentNode(), context, addRelatedEntries);
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch store(IndexableElement element, GenericEntryContext context, boolean addRelatedEntries) {
//		UpdateDocumentEntryImpl entry = new UpdateDocumentEntryImpl(registry.getForClass(element), element, context,
//				STORE_ACTION);
//		addEntry(entry);
//
//		if (addRelatedEntries) {
//			// We need to store (e.g: Update related entries)
//			element.handleRelatedEntries((relatedElement, relatedContext) -> {
//				store(relatedElement, relatedContext, false);
//			});
//		}
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch delete(IndexableElement element, GenericEntryContext context, boolean addRelatedEntries) {
//		UpdateDocumentEntry entry = new UpdateDocumentEntryImpl(registry.getForClass(element), element, context,
//				DELETE_ACTION);
//		addEntry(entry);
//
//		if (addRelatedEntries) {
//			// We need to store (e.g: Update related entries)
//			element.handleRelatedEntries((relatedElement, relatedContext) -> {
//				this.store(relatedElement, relatedContext, false);
//			});
//		}
//		return this;
//	}
//
//	@Override
//	public EventQueueBatch updatePermissions(IndexableElement element) {
//		GenericEntryContextImpl context = new GenericEntryContextImpl();
//		Project project = element.getProject();
//		if (project != null) {
//			context.setProjectUuid(project.getUuid());
//		}
//		UpdateDocumentEntry entry = new UpdateDocumentEntryImpl(registry.getForClass(element), element, context,
//				SearchQueueEntryAction.UPDATE_ROLE_PERM_ACTION);
//		addEntry(entry);
//		return this;
//	}
//
//	@Override
//	public BulkEventQueueEntry<?> addEntry(BulkEventQueueEntry<?> entry) {
//		bulkEntries.add(entry);
//		return entry;
//	}
//
//	@Override
//	public SeperateSearchQueueEntry<?> addEntry(SeperateSearchQueueEntry<?> entry) {
//		seperateEntries.add(entry);
//		return entry;
//	}
//
//	@Override
//	public List<? extends SearchQueueEntry> getEntries() {
//		List<SearchQueueEntry<? extends EntryContext>> entries = Stream
//				.concat(bulkEntries.stream(), seperateEntries.stream()).collect(Collectors.toList());
//
//		if (log.isDebugEnabled()) {
//			for (SearchQueueEntry entry : entries) {
//				log.debug("Loaded entry {" + entry.toString() + "} for batch {" + getBatchId() + "}");
//			}
//		}
//
//		return entries;
//	}
//
//	@Override
//	public String getBatchId() {
//		return batchId;
//	}
//
//	@Override
//	public void printDebug() {
//		for (SearchQueueEntry entry : getEntries()) {
//			log.debug("Entry {" + entry.toString() + "} in batch {" + getBatchId() + "}");
//		}
//	}
//
//	@Override
//	public void clear() {
//		bulkEntries.clear();
//		seperateEntries.clear();
//	}
//
//	@Override
//	public int size() {
//		return bulkEntries.size() + seperateEntries.size();
//	}
//
//	@Override
//	public void addAll(EventQueueBatch otherBatch) {
//		if (otherBatch instanceof EventQueueBatchImpl) {
//			EventQueueBatchImpl batch = (EventQueueBatchImpl) otherBatch;
//			bulkEntries.addAll(batch.bulkEntries);
//			seperateEntries.addAll(batch.seperateEntries);
//		} else {
//			throw new RuntimeException("Cannot mix SearchQueueBatch instances");
//		}
//	}
//	
//	
	@Override
	public Completable dispatch() {
		return Completable.complete();
	}
	
	
	@Override
	public void updated(IndexableElement updateElement) {
		updateElement.onUpdated();
	}
	
	
	

}
