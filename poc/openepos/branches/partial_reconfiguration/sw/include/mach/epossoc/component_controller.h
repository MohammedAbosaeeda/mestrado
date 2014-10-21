// EPOS EPOSSOC Component Controller Mediator

#ifndef __epossoc_component_controller_h
#define __epossoc_component_controller_h

#include <ic.h>
#include <component_controller.h>

__BEGIN_SYS

class EPOSSOC_Component_Controller: public Component_Controller_Common
{
public:
    // Error return result
    enum {
        CMD_RESULT_ERR  = 0xFFFFFFFF
    };

    typedef struct {
        unsigned int buffer;
        unsigned int dispatcher_address;
        unsigned int object_address;
    } agent_call_info;

public:
    static unsigned int alloc_proxy(unsigned int phy_x, unsigned int phy_y,
            unsigned int phy_local, unsigned int type_id,
            unsigned int instance_id);
    static unsigned int alloc_agent(unsigned int type_id,
            unsigned int instance_id, unsigned int dispatcher_address,
            unsigned int dispatcher_object_address);

    static unsigned int receive_call(unsigned int buff_num);
    static unsigned int receive_call_data(unsigned int buff_num);
    static void receive_return_data(unsigned int buff_num, unsigned int n_ret,
            unsigned int * data);

    static void send_call (unsigned int buff_num, unsigned int op_id);
    static void send_call_data(unsigned int buff_num, unsigned int n_args,
            unsigned int * data);
    static void send_return_data(unsigned int buff_num, unsigned int n_ret,
            unsigned int * data);

    static bool agent_has_call(agent_call_info &info);
    static void enable_agent_receive_int(IC::Interrupt_Handler h);
    static void disable_agent_receive_int();

private:
    // Base addresses for each register group
    enum {
        ADDR_BASE       = Traits<EPOSSOC_Component_Controller>::BASE_ADDRESS,
        ADDR_BASE_CTRL  = ADDR_BASE,
        ADDR_BASE_BUFF  = ADDR_BASE | 0x00000200
    };

    // Control registers address
    enum {
        CTRL_CMD                    = 0x00 << 2,
        CTRL_CMD_RESULT             = 0x01 << 2,
        CTRL_CMD_IDLE               = 0x02 << 2,
        CTRL_STATUS_AGENT_INT       = 0x03 << 2,
        CTRL_STATUS_AGENT_BUFFER    = 0x04 << 2,
        CTRL_INFO_NOC_X             = 0x05 << 2,
        CTRL_INFO_NOC_Y             = 0x06 << 2,
        CTRL_INFO_NOC_LOCAL         = 0x07 << 2,
        CTRL_INFO_BUFFER_SIZE       = 0x08 << 2
    };

    // Buffer registers address
    enum {
        BUFF_PROXY_PHY_X            = 0x0 << 5,
        BUFF_PROXY_PHY_Y            = 0x1 << 5,
        BUFF_PROXY_PHY_LOCAL        = 0x2 << 5,
        BUFF_MSG_TYPE               = 0x3 << 5,
        BUFF_INSTANCE_ID            = 0x4 << 5,
        BUFF_TYPE_ID                = 0x5 << 5,
        BUFF_DATA                   = 0x6 << 5,
        BUFF_TX                     = 0x7 << 5,
        BUFF_RX                     = 0x8 << 5,
        BUFF_AGENT_DISP_ADDR        = 0x9 << 5,
        BUFF_AGENT_DISP_OBJ_ADDR    = 0xA << 5
    };

    // Command opcodes
    enum {
        CMD_ALLOC_PROXY = 0,
        CMD_ALLOC_AGENT = 1
    };

private:
    static void ctrl_cmd(unsigned int val){ ctrl_reg(CTRL_CMD, val); }
    static unsigned int ctrl_cmd() { return ctrl_reg(CTRL_CMD); }

    static unsigned int ctrl_cmd_result() { return ctrl_reg(CTRL_CMD_RESULT); }
    static bool ctrl_cmd_idle() { return ctrl_reg(CTRL_CMD_IDLE) == 1; }

    static bool ctrl_status_agent_int() { return ctrl_reg(CTRL_STATUS_AGENT_INT) == 1; }
    static unsigned int ctrl_status_agent_buffer() { return ctrl_reg(CTRL_STATUS_AGENT_BUFFER); }

    static unsigned int ctrl_info_noc_x() { return ctrl_reg(CTRL_INFO_NOC_X); }
    static unsigned int ctrl_info_noc_y() { return ctrl_reg(CTRL_INFO_NOC_Y); }
    static unsigned int ctrl_info_noc_local() { return ctrl_reg(CTRL_INFO_NOC_LOCAL); }
    static unsigned int ctrl_info_buff_size() { return ctrl_reg(CTRL_INFO_BUFFER_SIZE); }

    static void buff_proxy_phy_x(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_PROXY_PHY_X, val); }
    static unsigned int buff_proxy_phy_x(unsigned int buff_num) { return buff_reg(buff_num, BUFF_PROXY_PHY_X); }

    static void buff_proxy_phy_y(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_PROXY_PHY_Y, val); }
    static unsigned int buff_proxy_phy_y(unsigned int buff_num) { return buff_reg(buff_num, BUFF_PROXY_PHY_Y); }

    static void buff_proxy_phy_local(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_PROXY_PHY_LOCAL, val); }
    static unsigned int buff_proxy_phy_local(unsigned int buff_num) { return buff_reg(buff_num, BUFF_PROXY_PHY_LOCAL); }

    static void buff_msg_type(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_MSG_TYPE, val); }
    static unsigned int buff_msg_type(unsigned int buff_num) { return buff_reg(buff_num, BUFF_MSG_TYPE); }

    static void buff_instance_id(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_INSTANCE_ID, val); }
    static unsigned int buff_instance_id(unsigned int buff_num) { return buff_reg(buff_num, BUFF_INSTANCE_ID); }

    static void buff_type_id(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_TYPE_ID, val); }
    static unsigned int buff_type_id(unsigned int buff_num) { return buff_reg(buff_num, BUFF_TYPE_ID); }

    static void buff_data_tx(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_DATA, val); }
    static unsigned int buff_data_rx(unsigned int buff_num) { return buff_reg(buff_num, BUFF_DATA); }

    static void buff_tx(unsigned int buff_num, bool val){ buff_reg(buff_num, BUFF_TX, val); }
    static bool buff_tx(unsigned int buff_num) { return buff_reg(buff_num, BUFF_TX); }

    static void buff_rx(unsigned int buff_num, bool val){ buff_reg(buff_num, BUFF_RX, val); }
    static bool buff_rx(unsigned int buff_num) { return buff_reg(buff_num, BUFF_RX); }

    static void buff_agent_disp_addr(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_AGENT_DISP_ADDR, val); }
    static unsigned int buff_agent_disp_addr(unsigned int buff_num) { return buff_reg(buff_num, BUFF_AGENT_DISP_ADDR); }

    static void buff_agent_disp_obj_addr(unsigned int buff_num, unsigned int val){ buff_reg(buff_num, BUFF_AGENT_DISP_OBJ_ADDR, val); }
    static unsigned int buff_agent_disp_obj_addr(unsigned int buff_num) { return buff_reg(buff_num, BUFF_AGENT_DISP_OBJ_ADDR); }

    static unsigned int ctrl_reg(unsigned int reg) {
        volatile unsigned int* aux = reinterpret_cast<volatile unsigned int*>(ADDR_BASE_CTRL | reg);
        return *aux;
    }

    static void ctrl_reg(unsigned int reg, unsigned int data) {
        volatile unsigned int* aux = reinterpret_cast<volatile unsigned int*>(ADDR_BASE_CTRL | reg);
        *aux = data;
    }

    static unsigned int buff_reg(unsigned int buff_num, unsigned int reg) {
        volatile unsigned int* aux = reinterpret_cast<volatile unsigned int*>(ADDR_BASE_BUFF | reg | (buff_num<<2));
        return *aux;
    }

    static void buff_reg(unsigned int buff_num, unsigned int reg, unsigned int data) {
        volatile unsigned int* aux = reinterpret_cast<volatile unsigned int*>(ADDR_BASE_BUFF | reg | (buff_num<<2));
        *aux = data;
    }
};

__END_SYS

#endif
