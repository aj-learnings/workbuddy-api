package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.WorkItem;
import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import com.ajlearnings.workbuddy.repository.IWorkItemRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkItemStore implements IWorkItemStore {

    private final IWorkItemRepository workItemRepository;

    public WorkItemStore(IWorkItemRepository workItemRepository) {
        this.workItemRepository = workItemRepository;
    }

    @Override
    public WorkItem add(WorkItem workItem) {
        return workItemRepository.save(workItem);
    }

    @Override
    public List<WorkItem> getAll() {
        return workItemRepository.findAll();
    }

    @Override
    public WorkItem get(ObjectId workItemId) {
        return workItemRepository.findById(workItemId)
                                 .orElseThrow(() -> new ResourceNotFoundException("Work item not found with id : " + workItemId.toString()));
    }

    @Override
    public WorkItem update(WorkItem workItem) {
        return workItemRepository.save(workItem);
    }

    @Override
    public void delete(ObjectId workItemId) {
        workItemRepository.deleteById(workItemId);
    }
}
