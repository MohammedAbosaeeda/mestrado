int MPI_Recv(void * buf, int const count,
	     MPI_Datatype const datatype, int const source,
	     int const tag, MPI_Comm const comm, 
	     MPI_Status * const status)
{
    message_t message;

    message.header.node = source;
    message.header.tag = tag;
    message.header.context = comm;

    message.buf = buf;
    message.len = get_extent(datatype)*count;
    message.node = rank2node_id(source);

    // receive operation
    *epos_comm >> message;

    status->count = message.len;
    // the division by get_extent(datatype) is done at MPI_Get_count
    status->MPI_ERROR = 0;
    status->MPI_SOURCE = comm.get_local_rank(message.header.node);
    status->MPI_TAG = message.header.tag;

    return 0;
}
