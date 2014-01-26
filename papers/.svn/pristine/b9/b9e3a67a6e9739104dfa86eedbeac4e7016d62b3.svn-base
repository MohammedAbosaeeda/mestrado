

//acc_t deve vir de um -D na compilação

static acc_t en[4] = {0.1, 0.2, 0.3, 0.4};
static acc_t acc = 850.0;
void update_time(unsigned long long tb, unsigned long long tf, char mode)
{
    acc -= (tf - tb) * en[mode];
}

void update_time_base(unsigned long long tb, unsigned long long tf, char mode)
{
}

static acc_t ev[10] = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
void update_event(char event)
{
    acc -= ev[event];
}

void update_event_base(char event)
{
}

void update_events(char event, unsigned int n)
{
    acc -= ev[event] * n;
}

void update_events_base(char event, unsigned int n)
{
}

int main(void)
{
    
}

