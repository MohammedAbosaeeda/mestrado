// EPOS Periodic Thread Abstraction Test Program

#include <utility/ostream.h>
#include <periodic_thread.h>

__USING_SYS



const unsigned int MS = 1000;

OStream cout;

class RT_Entity{
private:
    unsigned int period; //Period in uSec that the "periodicRun" method will be called, e.g. every 500MS
    int id;              //id of the entity, in case they are in different machines
    float* inputSet;     //An array with the input values
    float* outputSet;    //An array with the output values
    int inputSetSize;    //
    int outputSetSize;
    int (*periodicFunction)(float*,float*);
    Periodic_Thread* periodicThread;
    unsigned int worstCase;
public:
    RT_Entity(unsigned int period,int id,int inputSetSize,int outputSetSize,int (*periodicFunction)(float*,float*),int worstCase){
        this->period=period;
        this->id=id;
        this->inputSetSize=inputSetSize;
        this->outputSetSize=outputSetSize;
        inputSet  = (inputSetSize != 0) ? new float[inputSetSize]:0;
        outputSet = (outputSetSize != 0) ? new float[outputSetSize]:0;
        this->periodicFunction=periodicFunction;
        this->worstCase=worstCase;
    } 
    ~RT_Entity(){
        delete inputSet;
        delete outputSet;
        delete periodicThread;
    }
    void  setInput(int index,float value){ inputSet[index]=value; } 
    float getOutput(int index)           { return outputSet[index]; } 
    int   getId()                        { return id; }
    unsigned int getWorstCase()          { return worstCase; }
    void start(){
        periodicThread=new Periodic_Thread(periodicFunction,inputSet,outputSet,period);
    }
    int join() { return periodicThread->join(); }
};

int temperatureSensorFunction(float* inputSet, float* outputSet){
        Periodic_Thread::wait_next();
        while(1) {
            cout<<"\nSensor\n";
            outputSet[0]++; //Ilegal, just for tests. 
            Periodic_Thread::wait_next();
        }
}

int temperatureControllerFunction(float* inputSet, float* outputSet){
        Periodic_Thread::wait_next();
        while(1) {
            cout<<"\nController\n";
            outputSet[0]=inputSet[0]+10;
            Periodic_Thread::wait_next();
        }
}

int temperatureActuatorFunction(float* inputSet, float* outputSet){
        Periodic_Thread::wait_next();
        while(1) {
            cout<<"\nActuator\n";
            Periodic_Thread::wait_next();
        }
}

class CommunicationSystem{
public:
    static int send(RT_Entity* from,RT_Entity* to,int fromOutput,int toInput){
        //cout<<"entrou send!";
        Periodic_Thread::wait_next();
        while(1) {
            cout<<"\nData sent from entity:"<<from->getId()<<" to entity:"<<to->getId()<<" value sent:"<<from->getOutput(fromOutput)<<"\n";
            to->setInput(toInput,from->getOutput(fromOutput));
            Periodic_Thread::wait_next();
        }
    }
    static int connect(RT_Entity* from,RT_Entity* to,int fromOutput,int toInput,int period){
        //cout<<"entrou!";
        new Periodic_Thread(&send,from,to, fromOutput, toInput,period);
        return 1000*MS;
    }
    //static int sendThroughNetwork(RT_Entity from,RT_Entity to,int fromOutput,int toInput);
    
};

const int PERIOD=5000*MS;
const int DELAY=1000*MS;

const int ID_TEMPERATURE_SENSOR=0;
const int ID_TEMPERATURE_CONTROLLER=1;
const int ID_TEMPERATURE_ACTUATOR=2;

const int WORST_CASE_SENSOR=1000*MS;
const int WORST_CASE_CONTROLLER=1000*MS;
const int WORST_CASE_ACTUATOR=1000*MS;

int main()
{
    RT_Entity temperatureSensor(PERIOD,ID_TEMPERATURE_SENSOR,0,1,&temperatureSensorFunction,WORST_CASE_SENSOR);
    RT_Entity temperatureController(PERIOD,ID_TEMPERATURE_CONTROLLER,1,1,&temperatureControllerFunction,WORST_CASE_CONTROLLER);
    RT_Entity temperatureActuator(PERIOD,ID_TEMPERATURE_ACTUATOR,1,0,&temperatureActuatorFunction,WORST_CASE_ACTUATOR);
    
    
    temperatureSensor.start();
    Alarm::delay(temperatureSensor.getWorstCase());
    
    int worstCaseCommunicator=CommunicationSystem::connect(&temperatureSensor,&temperatureController,0,0,PERIOD);
    Alarm::delay(worstCaseCommunicator);
    
    temperatureController.start();
    Alarm::delay(temperatureController.getWorstCase());
    
    CommunicationSystem::connect(&temperatureController,&temperatureActuator,0,0,PERIOD);
    Alarm::delay(worstCaseCommunicator);
    
    temperatureActuator.start();
    Alarm::delay(temperatureActuator.getWorstCase());
    
    temperatureSensor.join();
//    temperatureController.join();
//    temperatureActuator.join();
    return 0;
}
