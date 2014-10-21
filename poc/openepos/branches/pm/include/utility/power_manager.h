// EPOS-- Power Management Utility Declarations

#ifndef __power_manager_h
#define __power_manager_h 

#include <traits.h>
#include <utility/list.h>

__BEGIN_SYS

class Power {
public:
	virtual ~Power() {}
};


namespace Power_Manager_Xunxo
{
	class Power_Manager_Instances_List_Holder
	{
	public:
		typedef List<Power> ListP;
		static ListP instances;
	};

	unsigned long long GetTimeStamp();
};

/******************** SHARED ASPECT ********************/

template<bool enabled, int modes>
class Power_Manager_Shared {
public:
	void insert(int mode) {}
	void remove(int mode) {}
	bool check_shared(int current_mode, int intended_mode) { return true; }
};

template<int modes>
class Power_Manager_Shared<true, modes>
{
public:
	void insert(int mode)
	{
		_op_mode_counter[mode]++;
	}

	void remove(int mode)
	{
		_op_mode_counter[mode]--;
	}

	bool check_shared(int current_mode, int intended_mode)
	{
		_op_mode_counter[current_mode]--;
		_op_mode_counter[intended_mode]++;
		for(int i = 0; i < intended_mode; i++)
		{
			if(_op_mode_counter[i]) return false;
		}
		return true;
	}

private:
	int _op_mode_counter[modes];
};

/******************** INSTANCES ASPECT ********************/

template<typename T, bool enabled>
class Power_Manager_Instances {
public:
	void insert(Power * _p) {}
	void remove(Power * _p) {}
};

template<typename T>
class Power_Manager_Instances<T, true>
{
public:
	static void insert(Power * _p)
	{
		Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::ListP::Element * el =
				new Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::ListP::Element(_p);
		Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::instances.insert(el);
	}

	static void remove(Power * _p)
	{
		Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::instances.remove(
				Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::instances.search(_p));
	}

	static unsigned int count() {
		return Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::instances.size();
	}

	static Power * get(unsigned int i) {
		Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::ListP::Iterator it =
				Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::instances.begin() + i;
		if(it != Power_Manager_Xunxo::Power_Manager_Instances_List_Holder::instances.end())
			return it->object();
		else
			return 0;
	}
};

/******************** ENERGY ACCOUNTING ASPECT ********************/

template<typename T, int modes, int voltages, bool enabled>
class Power_Manager_Consumption_Table;

//How to get rid of this declaration?
template<typename T, int modes, int voltages>
class Power_Manager_Consumption_Table<T, modes, voltages, false>
{
	static const unsigned int consumption_table [1][1];
};

template<typename T, int modes, int voltages>
class Power_Manager_Consumption_Table<T, modes, voltages, true>
{
public:
	const unsigned int GetPower(unsigned int op_mode, unsigned int v)
	{
		return consumption_table[op_mode][v];
	}
private:
	static const unsigned int consumption_table [modes][voltages];
};

template<typename T, int modes, int voltages, bool enabled>
class Power_Manager_Energy_Accounter {
public:
	Power_Manager_Energy_Accounter() {}
	~Power_Manager_Energy_Accounter() {}
	void update(unsigned int op_mode, unsigned int v = Traits<T>::_3_0v) {}
	unsigned long long last_pow_change() {return 0;}
	void last_pow_change(unsigned long long c) {}
};

template<typename T, int modes, int voltages>
class Power_Manager_Energy_Accounter<T, modes, voltages, true>
{
public:
	Power_Manager_Energy_Accounter()
	: _last_pow_change(Power_Manager_Xunxo::GetTimeStamp())
	{
		consumed_energy = 0;
	}

	~Power_Manager_Energy_Accounter() {}

	void update(unsigned int op_mode, unsigned int v = Traits<T>::_3_0v)
	{
		unsigned long long now, time;
		now = Power_Manager_Xunxo::GetTimeStamp();
		time = now - last_pow_change();
		consumed_energy += time * consumption_table.GetPower(op_mode, v);
		last_pow_change(now);

		//TODO: Retirado por enquanto, deve retornar
		//Battery::remaining_charge_decrement(&energy);

		//TODO: Para o futuro
		//if(Traits<Thread>::energy_scheduler)
		//    Thread::consumption_update(energy);

	}

	unsigned long long last_pow_change()
	{
		return _last_pow_change;
	}

	void last_pow_change(unsigned long long c)
	{
		_last_pow_change = c;
	}

private:
	unsigned long long consumed_energy;
	unsigned long long _last_pow_change;
	Power_Manager_Consumption_Table<T, modes, voltages, Traits<T>::accounting>
	consumption_table;
};

/******************** BINDING SCAFFOLD ********************/

template<
typename T,
bool shared = Traits<T>::shared,
bool instances = Traits<T>::instances,
bool accounting = Traits<T>::accounting
>
class Power_Manager : public Power, public T, public Traits<T>
{
protected:
	typedef Traits<T> _Traits;

public:
	using _Traits::Power_Modes;

	void constructor()
	{
		if(shared) share.insert(power());
		if(instances) instance.insert(this);
	}

	void destructor()
	{
		if(shared) share.remove(power());
		if(instances) instance.remove(this);
	}

	Power_Manager() : T()
	{	constructor();}

	Power_Manager(unsigned int a) : T(a)
	{	constructor();}

	Power_Manager(unsigned char a, unsigned long b) : T(a, b)
	{	constructor();}

	Power_Manager(unsigned char a, unsigned char b,
			unsigned char c, unsigned long d) : T(a, b, c, d)
	{	constructor();}

	Power_Manager(unsigned int a, unsigned int b,
			unsigned int c, unsigned int d) : T(a, b, c, d)
	{	constructor();}

	Power_Manager(unsigned int a, unsigned int b,
			unsigned int c, unsigned long d,
			unsigned int e) : T(a, b, c, d, e)
	{	constructor();}

	~Power_Manager() {destructor();}

	// T::public_methods() which need wrapping

	//CPU


	// UART
	int get()
	{
		power_enter();
		int ret = T::get();
		power_leave();
		return ret;
	}
	void put(char c)
	{
		power_enter();
		T::put(c);
		power_leave();
	}

	//ADC
	int sample()
	{
		power_enter();
		int ret = T::sample();
		power_leave();
		return ret;
	}
	//int get() -> from the template above

	//Power Management Interface
	void power(int mode)
	{
		if(shared && !share.check_shared(power(), mode)) return;

		if(accounting) accounter.update(power(), _Traits::OPERATING_VOLTAGE);

		_prev_op_mode = _op_mode;
		_op_mode = mode;
		T::power(mode);
	}

	int power() {return _op_mode;};

	unsigned int instances_count() { return instance.count(); }
	Power_Manager<T> * get_instance(unsigned int i) { return dynamic_cast<Power_Manager<T>*>(instance.get(i)); }

private:
	void power_enter()
	{
		//TODO: handle context-specific modes, such as SEND_ONLY and RECV_ONLY
		if(power() > _Traits::LIGHT)
		{
			power((_prev_op_mode < _Traits::LIGHT) ? _prev_op_mode : _Traits::LIGHT);
		}

		if(accounting) accounter.update(power(), _Traits::OPERATING_VOLTAGE);
	}

	void power_leave() {}

	int _op_mode;
	int _prev_op_mode;

	Power_Manager_Shared<shared, _Traits::modes> share;
	Power_Manager_Instances<T, instances> instance;
	Power_Manager_Energy_Accounter<T, Traits<T>::modes, Traits<T>::voltages, accounting> accounter;
};

#define POWER_MANAGER_DEFINITIONS(type)\
	template<>\
	const unsigned int\
	Power_Manager_Consumption_Table<\
		type,\
		Traits<type>::modes,\
		Traits<type>::voltages,\
		false\
	>::consumption_table[1][1] = { 0 };\
	\
	template<>\
	const unsigned int\
	Power_Manager_Consumption_Table<\
		type,\
		Traits<type>::modes,\
		Traits<type>::voltages,\
		true\
	>::consumption_table [Traits<type>::modes][Traits<type>::voltages]=\


__END_SYS

#endif
