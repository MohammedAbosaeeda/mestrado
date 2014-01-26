int MPI_Send(void *buf, int count, MPI_Datatype datatype,
	     int dest, int tag, MPI_Comm comm)
{
    message_t message;
    message.header.node = MPI_rank;

    // MPI_rank is the global rank of this process
    message.header.context = comm.get_context();
    message.header.tag = tag;
    message.buf = buf;
    message.len = get_extent(datatype)*count;
    message.node = rank2node_id(comm.get_global_rank(dest));

    // send operation	
    // epos_comm is the EPOS Communicator
    return (*epos_comm << message);
}
