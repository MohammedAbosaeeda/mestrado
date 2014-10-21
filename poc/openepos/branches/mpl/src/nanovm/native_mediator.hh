/*! NativeMediator base/template class.
 * 
 * Native code adapter (wrapper) for any Hardware Mediator (actually for any object).
 * 
 */
template<class HW_Mediator, typename MPL_Reference>
class NativeMediator
{
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
