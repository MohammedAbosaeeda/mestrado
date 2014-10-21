unsigned char GetResource(ResourceType res) {
//? assertions
	assert(!(reslocks & (1 << res)), "GetResource: Resource is already locked");
	reslocks |= (1 << res);

//?	
	ResourceType new_prio = ResourcePriority(res) > current_priority ? ResourcePriority(res) : current_priority;
	current_priority = new_prio;
	add_element_first(queues + current_priority - 1, current_task);

	return E_OK;
}

unsigned char ReleaseResource(ResourceType res) {
//? assertions
	assert((reslocks & (1 << res)), "ReleaseResource: Resource was never locked");
	reslocks &= ~(1 << res);

//?	
	remove_element(queues + current_priority - 1);
	Schedule();

	return E_OK;
}

