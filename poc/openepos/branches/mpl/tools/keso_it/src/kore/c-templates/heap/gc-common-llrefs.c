/*KESO--HEADER--KESO*/

extern object_t** unknown_task; 
typedef void (*callback_fkt_t)(object_t*);
#define KESO_STACK_WALK(_stack_,_callback_)  keso_gc_walk_stack((_stack_)->llrefs,_callback_)
void keso_gc_walk_stack(object_t** stack, callback_fkt_t callback);

/*KESO--CFILE--KESO*/

object_t** unknown_task;

void keso_gc_walk_stack(object_t** stack, callback_fkt_t handleObj) {

	if (stack==(object_t**) KESO_EOLL) return; /* empty stack */

	stack = keso_unpackStackRef(stack);

	while (1) {
		/* skip nulls */
		if (*stack==NULL) {
			stack++;
			continue;
		}

		/* end of linked list */
		if (*stack==KESO_EOLL) return;

		/* follow up next frame */
		if (keso_isStackRef(*stack)) {
			stack=keso_unpackStackRef(*stack);
			continue;
		}

		/* process object reference */
		ASSERTCLASSID((*stack)->class_id);
		handleObj(*stack);
		stack++;
	}	
}

