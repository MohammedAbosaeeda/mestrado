#include <map>

/*! Auxiliary class.
 * <<Singleton>>
 * Maps MPL objects (i.e. Java) into native objects (e.g. C++)
 */
/* 
template<typename MPL_Reference, class NativeReference>
class NativeMap
{
    //! NOTE: Linux / STL C++ version

public:
    static NativeMap<MPL_Reference, NativeReference>* getInstance()
    {
        if (! __instance)
        {
            __instance = new NativeMap<MPL_Reference, NativeReference>();
        }

        return __instance;
    }

    /// TODO: put and get methods...
public:

    void put(MPL_Reference key, NativeReference value)
    {
        //__theMap[key] = value;
    }

private:
    //std::map<MPL_Reference, NativeReference>* __theMap;

private:
    NativeMap()
    {
        //__theMap = new std::map<MPL_Reference, NativeReference>();
    }

private:
    static NativeMap<MPL_Reference, NativeReference>* __instance;
    
};
*/ 


/*! NativeMediator base/template class.
 * 
 * Native code adapter (wrapper) for any Hardware Mediator (actually for any object).
 * 
 */
template<class HW_Mediator, typename MPL_Reference>
class NativeMediator
{
/*    
protected:
    typedef NativeMap<MPL_Reference, NativeMediator* > Map;


protected:

    //! The map singleton access
    static Map* nativeMap()
    {
        if (! __nativeMap)
        {
            __nativeMap = new Map();
        }
        
        return __nativeMap;
    } 

    
protected:
    static Map* __nativeMap; //! Contains all wrapped instances of HW_Mediator class. The map is a singleton.
    static bool __nativeMapInitiated;
*/

};


/*! NativeMediator base/template class.
 * <<ObjectBasedAdapter>>
 * 
 * Native code adapter (wrapper) for any Hardware Mediator (actually for any object).
 * 
 */
template<class HW_Mediator, typename MPL_Reference>
class ObjectBasedNativeMediator : public NativeMediator<HW_Mediator, MPL_Reference>
{
    //! MKL NOTE: EBG can generate this class!

public:
    HW_Mediator* inner()
    {
        return __inner;
    }

 

protected:
    HW_Mediator* __inner;

};


/*! NativeMediator base/template class.
 * <<ClassBasedAdapter>>
 * 
 * Native code adapter (wrapper) for any Hardware Mediator (actually for any object).
 * 
 */
template<class HW_Mediator, typename MPL_Reference>
class ClassBasedNativeMediator : public NativeMediator<HW_Mediator, MPL_Reference>, private HW_Mediator
{
    //! MKL NOTE: EBG can generate this class!
};
