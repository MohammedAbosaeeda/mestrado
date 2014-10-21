/*
 * traits.h
 *
 *  Created on: Jun 21, 2011
 *      Author: tiago
 */

#ifndef TRAITS_H_
#define TRAITS_H_

/*
template <class Imp>
struct Traits
{
    static const bool enabled = true;
    static const bool debugged = true;
};

class Scheduler_HL;

template <> struct Traits<Scheduler_HL>: public Traits<void>
{
	static const bool atomic = true;
};
*/

// IF metaprogram
template<bool condition, typename Then, typename Else>
struct IF
{ typedef Then Result; };

template<typename Then, typename Else>
struct IF<false, Then, Else>
{ typedef Else Result; };

struct Traits
{
	static const bool atomic = true;
};



#endif /* TRAITS_H_ */
