package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.WorkItem;
import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import com.ajlearnings.workbuddy.repository.IWorkItemRepository;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.*;
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
    @CacheEvict(key = "'all'")
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
    @Caching(
            put = @CachePut(key = "#workItemId"),
            evict = @CacheEvict(key = "'all'")
    )
    public WorkItem update(ObjectId workItemId, WorkItem workItem) {
        return workItemRepository.save(workItem);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "#workItemId")
    })
    public void delete(ObjectId workItemId) {
        workItemRepository.deleteById(workItemId);
    }
}
