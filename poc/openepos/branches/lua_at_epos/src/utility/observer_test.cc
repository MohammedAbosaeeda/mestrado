#include <utility/observer.h>
#include <utility/ostream.h>
#include <utility/debug.h>

__USING_SYS

OStream cout;

class Test_Observed;

class Test_Observer : public Conditional_Observer {
    public:
		Test_Observer(){
			db<Test_Observer>(TRC) << "Test_Observer:: " << this << " is saying hi\n";
		};
		~Test_Observer() {
		    db<Test_Observer>(TRC) << "Test_Observer:: " << this << " is waving goodbye\n";
		}
		void update(Conditionally_Observed * o){
		    cout << "Notify received.\t";
    		db<Test_Observer>(TRC) << "Test_Observer::update(o=" << o << ")\n\n";
		}
};

class Test_Observed : public Conditionally_Observed {
    public:
		Test_Observed(){
		    db<Test_Observed>(TRC) << "Test_Observed:: " << this << " is saying hi\n";		
		};
		~Test_Observed() {
		    db<Test_Observed>(TRC) << "Test_Observed:: " << this << " is waving goodbye\n";
		}
};

int main() {
	cout << "\nConstructing objects.\n";
	Test_Observed * root = new Test_Observed();
	Test_Observer * observer1 = new Test_Observer();
	Test_Observer * observer2 = new Test_Observer();
	Test_Observer * observer3 = new Test_Observer();
	Test_Observer * observer4 = new Test_Observer();

	cout << "\nAttaching observers.\n";
	root->attach(observer1,1);
	root->attach(observer2,1);
	root->attach(observer3,3);
	root->attach(observer4,4);
	
	cout << "\nNotifying just the first two observers.\n";
	root->notify(1);
	cout << "\nNow trying to detach one of them.\n";
	root->detach(observer2,1);
	cout << "\nTrying to notify them again.\n"
		 << "Only one of them will update itself.\n";
	root->notify(1);	
	cout << "\nNotifying the next-to-last observer.\n";
	root->notify(3);	
	cout << "\nNotifying the last observer.\n";	
	root->notify(4);
	
	cout << "Detaching and destructing objects.\n";
	root->detach(observer1,1);
	root->detach(observer3,3);
	root->detach(observer4,4);
	delete observer1;
	delete observer2;
	delete observer3;
	delete observer4;
	delete root;
    return 0;
}

