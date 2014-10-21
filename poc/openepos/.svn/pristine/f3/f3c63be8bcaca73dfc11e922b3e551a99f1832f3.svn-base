// EPOS EPOSSOC Component Controller Mediator

#ifndef __epossoc_component_controller_h
#define __epossoc_component_controller_h

#include <machine.h>
#include <component_controller.h>

__BEGIN_SYS

class EPOSSOC_Component_Controller: public Component_Controller_Common
{
public:

    //Status
    enum {
        CMD_ALLOC_PROXY = 0,
        CMD_ALLOC_AGENT = 1,
        CMD_RESULT_ERR = 0xFFFFFFFF
    };

    typedef struct {
        unsigned int buffer;
        unsigned int dispatcher_address;
        unsigned int object_address;
    } agent_call_info;

    static unsigned int alloc_proxy(unsigned int phy_x,
                             unsigned int phy_y,
                             unsigned int phy_local,
                             unsigned int type_id,
                             unsigned int instance_id);

    static unsigned int alloc_agent(unsigned int type_id,
                             unsigned int instance_id,
                             unsigned int dispatcher_address,
                             unsigned int dispatcher_object_address);

    static void enable_agent_receive_int(IC::Interrupt_Handler h);
    static void disable_agent_receive_int();

    static unsigned int receive_call(unsigned int buff);

    static unsigned int receive_call_data(unsigned int buff);

    static void send_return_data(unsigned int buff, unsigned int n_ret, unsigned int *data);


    static inline void EPOSSOC_Component_Controller::send_call(unsigned int buff, unsigned int op_id){
        db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::send_call("
                  << (void*) buff << ", "
                  << (void*) op_id << ")\n";

        while(buff_tx(buff));

        buff_msg_type(buff, Implementation::MSG_TYPE_CALL);
        buff_data_tx(buff, op_id);

        buff_tx(buff,true);
    }

    static inline void send_call_data(unsigned int buff, unsigned int n_args, unsigned int *data){
        db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::send_call_data("
                  << (void*) buff << ", "
                  << (void*) n_args << ", "
                  << "data(";
                 for (unsigned int i = 0; i < n_args; ++i)
                         db<EPOSSOC_Component_Controller>(TRC) << (void*)data[i] << ", ";
                db<EPOSSOC_Component_Controller>(TRC) << "))\n";

        while(buff_tx(buff));
        buff_msg_type(buff, Implementation::MSG_TYPE_CALL_DATA);

        for (unsigned int i = 0; i < n_args; ++i) {
            buff_data_tx(buff, data[i]);
            buff_tx(buff,true);
            while(buff_tx(buff));
        }
    }

    static inline void receive_return_data(unsigned int buff, unsigned int n_ret, unsigned int *data){
        db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::receive_return_data("
                << (void*) buff << ", "
                << (void*) n_ret << ")\n";

        for (unsigned int i = 0; i < n_ret; ++i) {
            while(!buff_rx(buff));

            unsigned int msg_type = buff_msg_type(buff);
            if(msg_type == Implementation::MSG_TYPE_RESP_DATA){
                data[i] = buff_data_rx(buff);
            }
            else{
                db<EPOSSOC_Component_Controller>(ERR) << "EPOSSOC_Component_Controller::receive_return_data: ERR- Received msg of type " << (void*)msg_type << " is not return data\n";
            }
            buff_rx(buff, false);

        }

        db<EPOSSOC_Component_Controller>(TRC) << "=data(";
        for (unsigned int i = 0; i < n_ret; ++i)
            db<EPOSSOC_Component_Controller>(TRC) << (void*)data[i] << ", ";
        db<EPOSSOC_Component_Controller>(TRC) << ")\n";
    }

    static inline bool agent_has_call(agent_call_info &info){
        db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::agent_has_call()\n";
        if(ctrl_status_agent_int()){
            unsigned int buff = ctrl_status_agent_buffer();
            info.buffer = buff;
            info.dispatcher_address = buff_agent_disp_addr(buff);
            info.object_address = buff_agent_disp_obj_addr(buff);
            return true;
        }
        else
            return false;
    }


protected:

    enum{
        CTRL_CMD                 = 0x00 << 2,
        CTRL_CMD_RESULT          = 0x01 << 2,
        CTRL_CMD_IDLE            = 0x02 << 2,
        CTRL_STATUS_AGENT_INT    = 0x03 << 2,
        CTRL_STATUS_AGENT_BUFFER = 0x04 << 2,
        CTRL_INFO_NOC_X          = 0x05 << 2,
        CTRL_INFO_NOC_Y          = 0x06 << 2,
        CTRL_INFO_NOC_LOCAL      = 0x07 << 2,
        CTRL_INFO_BUFFER_SIZE    = 0x08 << 2,

        BUFF_PROXY_PHY_X        = 0x0 << 5,
        BUFF_PROXY_PHY_Y        = 0x1 << 5,
        BUFF_PROXY_PHY_LOCAL    = 0x2 << 5,
        BUFF_MSG_TYPE           = 0x3 << 5,
        BUFF_INSTANCE_ID        = 0x4 << 5,
        BUFF_TYPE_ID            = 0x5 << 5,
        BUFF_DATA               = 0x6 << 5,
        BUFF_TX                 = 0x7 << 5,
        BUFF_RX                 = 0x8 << 5,
        BUFF_AGENT_DISP_ADDR    = 0x9 << 5,
        BUFF_AGENT_DISP_OBJ_ADDR = 0xa << 5
    };



    static inline void ctrl_cmd(unsigned int val){ write_ctrl_reg(CTRL_CMD, val); }
    static inline unsigned int ctrl_cmd() { return read_ctrl_reg(CTRL_CMD); }

    static inline unsigned int ctrl_cmd_result() { return read_ctrl_reg(CTRL_CMD_RESULT); }

    static inline bool ctrl_cmd_idle() { return read_ctrl_reg(CTRL_CMD_IDLE) == 1; }

    static inline bool ctrl_status_agent_int() { return read_ctrl_reg(CTRL_STATUS_AGENT_INT) == 1; }

    static inline unsigned int ctrl_status_agent_buffer() { return read_ctrl_reg(CTRL_STATUS_AGENT_BUFFER); }

    static inline unsigned int ctrl_info_noc_x() { return read_ctrl_reg(CTRL_INFO_NOC_X); }
    static inline unsigned int ctrl_info_noc_y() { return read_ctrl_reg(CTRL_INFO_NOC_Y); }
    static inline unsigned int ctrl_info_noc_local() { return read_ctrl_reg(CTRL_INFO_NOC_LOCAL); }
    static inline unsigned int ctrl_info_buff_size() { return read_ctrl_reg(CTRL_INFO_BUFFER_SIZE); }

    static inline void buff_proxy_phy_x(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_PROXY_PHY_X,val); }
    static inline unsigned int buff_proxy_phy_x(unsigned int buff) { return read_buff_reg(buff,BUFF_PROXY_PHY_X); }
    static inline void buff_proxy_phy_y(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_PROXY_PHY_Y,val); }
    static inline unsigned int buff_proxy_phy_y(unsigned int buff) { return read_buff_reg(buff,BUFF_PROXY_PHY_Y); }
    static inline void buff_proxy_phy_local(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_PROXY_PHY_LOCAL,val); }
    static inline unsigned int buff_proxy_phy_local(unsigned int buff) { return read_buff_reg(buff,BUFF_PROXY_PHY_LOCAL); }

    static inline void buff_msg_type(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_MSG_TYPE,val); }
    static inline unsigned int buff_msg_type(unsigned int buff) { return read_buff_reg(buff,BUFF_MSG_TYPE); }

    static inline void buff_instance_id(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_INSTANCE_ID,val); }
    static inline unsigned int buff_instance_id(unsigned int buff) { return read_buff_reg(buff,BUFF_INSTANCE_ID); }

    static inline void buff_type_id(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_TYPE_ID,val); }
    static inline unsigned int buff_type_id(unsigned int buff) { return read_buff_reg(buff,BUFF_TYPE_ID); }

    static inline void buff_data_tx(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_DATA,val); }
    static inline unsigned int buff_data_rx(unsigned int buff) { return read_buff_reg(buff,BUFF_DATA); }

    static inline void buff_tx(unsigned int buff, bool val){ write_buff_reg(buff,BUFF_TX,val); }
    static inline bool buff_tx(unsigned int buff) { return read_buff_reg(buff,BUFF_TX); }

    static inline void buff_rx(unsigned int buff, bool val){ write_buff_reg(buff,BUFF_RX,val); }
    static inline bool buff_rx(unsigned int buff) { return read_buff_reg(buff,BUFF_RX); }

    static inline void buff_agent_disp_addr(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_AGENT_DISP_ADDR,val); }
    static inline unsigned int buff_agent_disp_addr(unsigned int buff) { return read_buff_reg(buff,BUFF_AGENT_DISP_ADDR); }

    static inline void buff_agent_disp_obj_addr(unsigned int buff, unsigned int val){ write_buff_reg(buff,BUFF_AGENT_DISP_OBJ_ADDR,val); }
    static inline unsigned int buff_agent_disp_obj_addr(unsigned int buff) { return read_buff_reg(buff,BUFF_AGENT_DISP_OBJ_ADDR); }

private:
    enum {
        ADDR_BASE = Traits<EPOSSOC_Component_Controller>::BASE_ADDRESS,
        ADDR_BASE_CTRL = ADDR_BASE,
        ADDR_BASE_BUFF = ADDR_BASE | 0x00000200,

    };

    static inline unsigned int read_ctrl_reg(unsigned int reg){
        volatile unsigned int* aux = reinterpret_cast<volatile unsigned int*>(ADDR_BASE_CTRL | reg);
        return *aux;
    }
    static inline void write_ctrl_reg(unsigned int reg, unsigned int data){
        volatile unsigned int* aux = reinterpret_cast<volatile unsigned int*>(ADDR_BASE_CTRL | reg);
        *aux = data;
    }
    static inline unsigned int read_buff_reg(unsigned int buff, unsigned int reg){
        volatile unsigned int* aux = reinterpret_cast<volatile unsigned int*>(ADDR_BASE_BUFF | reg | (buff<<2));
        return *aux;
    }
    static inline void write_buff_reg(unsigned int buff, unsigned int reg, unsigned int data){
        volatile unsigned int* aux = reinterpret_cast<volatile unsigned int*>(ADDR_BASE_BUFF | reg | (buff<<2));
        *aux = data;
    }


};

__END_SYS

#endif
