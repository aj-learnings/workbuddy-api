package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.WorkItem;
import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import com.ajlearnings.workbuddy.repository.IWorkItemRepository;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "workitem")
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
    @Cacheable(key = "'all'")
    public List<WorkItem> getAll() {
        return workItemRepository.findAll();
    }

    @Override
    @Cacheable(key = "#workItemId")
    public WorkItem get(ObjectId workItemId) {
        return workItemRepository.findById(workItemId)
                                 .orElseThrow(() -> new ResourceNotFoundException("Work item not found with id : " + workItemId));
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
