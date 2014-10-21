// EPOS Mult Abstraction Test Program

#include <components/rsp_eth.h>
#include <chronometer.h>

__USING_SYS

Implementation::Agent<Implementation::RSP_Controller> rsp_ctrl_agent(Component_Manager::dummy_channel,Component_Manager::dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::RSP_Controller,0>::IID[1]));
//Implementation::Agent<Implementation::RSP_DTMF> rsp_ctrl_dtmf(Component_Manager::dummy_channel,Component_Manager::dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::RSP_DTMF,0>::IID[1]));
//Implementation::Agent<Implementation::RSP_ADPCM> rsp_ctrl_adpcm(Component_Manager::dummy_channel,Component_Manager::dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::IID[1]));
//Implementation::Agent<Implementation::RSP_AES> rsp_ctrl_aes(Component_Manager::dummy_channel,Component_Manager::dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::RSP_AES,0>::IID[1]));

int main()
{

    if(Traits<Implementation::RSP_DTMF>::hardware && !Traits<Implementation::RSP_Controller>::hardware){
        Component_Manager::init_sw_resource(Implementation::RSP_CONTROLLER_ID, Implementation::Resource_Table<Implementation::RSP_Controller,0>::IID[0], &Implementation::Agent<Implementation::RSP_Controller>::static_top_level, (void*)&rsp_ctrl_agent);
    }
    if(Traits<Implementation::RSP_ADPCM>::hardware && !Traits<Implementation::RSP_DTMF>::hardware){
        //Component_Manager::init_sw_resource(Implementation::RSP_DTMF_ID, Implementation::Resource_Table<Implementation::RSP_DTMF,0>::IID[0], &Implementation::Agent<Implementation::RSP_DTMF>::static_top_level, (void*)&rsp_ctrl_dtmf);
    }
    if(Traits<Implementation::RSP_AES>::hardware && !Traits<Implementation::RSP_ADPCM>::hardware){
       //Component_Manager::init_sw_resource(Implementation::RSP_ADPCM_ID, Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::IID[0], &Implementation::Agent<Implementation::RSP_ADPCM>::static_top_level, (void*)&rsp_ctrl_adpcm);
    }
    if(Traits<Implementation::RSP_ETH>::hardware && !Traits<Implementation::RSP_AES>::hardware){
        //Component_Manager::init_sw_resource(Implementation::RSP_AES_ID, Implementation::Resource_Table<Implementation::RSP_AES,0>::IID[0], &Implementation::Agent<Implementation::RSP_AES>::static_top_level, (void*)&rsp_ctrl_aes);
    }

    RSP_ETH eth(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    int n_pkt = eth.start();
    while (Component_Manager::rsp_flag < 2);


    return 0;
}
