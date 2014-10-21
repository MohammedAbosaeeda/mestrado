// EPOS List Utility Declarations

#ifndef __list_unified_h
#define __list_unified_h

#include "maybe.h"

namespace Unified {

// List Element Rank (for ordered lists)
class List_Element_Rank
{
public:
    List_Element_Rank(int r = 0): _rank(r) {}

    operator int() const { return _rank; }

protected:
    int _rank;
};


// List Elements
namespace List_Elements
{
    typedef List_Element_Rank Rank;

    // Vector Element
    template <typename T>
    class Pointer
    {
    public:
        typedef T Object_Type;
        typedef Pointer Element;

    public:
        Pointer(const T * o): _object(o) {}
    
        T * object() const { return const_cast<T *>(_object); }

    private:
        const T * _object;
    };

    // Hash Table Element
    template <typename T, typename R = Rank>
    class Ranked
    {
    public:
        typedef T Object_Type;
        typedef R Rank_Type;
        typedef Ranked Element;

    public:
        Ranked(const T * o, const R & r = 0): _object(o), _rank(r) {}

        T * object() const { return const_cast<T *>(_object); }

        const R & rank() const { return _rank; }
        const R & key() const { return _rank; }
        void rank(const R & r) { _rank = r; }
        int promote(const R & n = 1) { _rank -= n; return _rank; }
        int demote(const R & n = 1) { _rank += n; return _rank; }

    private:
        const T * _object;
         R _rank;
   };

    // Simple List Element
    template <typename T>
    class Singly_Linked
    {
    public:
        typedef T Object_Type;
        typedef Singly_Linked Element;

    public:
        Singly_Linked(const T * o): _object(o), _next(0) {}
    
        T * object() const { return const_cast<T *>(_object); }

        Element * next() const { return _next; }
        void next(Element * e) { _next = e; }
    
    private:
        const T * _object;
        Element * _next;
    };

    // Simple Ordered List Element
    // Hash Table's Synonym List Element
    template <typename T, typename R = Rank>
    class Singly_Linked_Ordered
    {
    public:
        typedef T Object_Type;
        typedef Rank Rank_Type;
        typedef Singly_Linked_Ordered Element;

    public:
        Singly_Linked_Ordered(const T * o, const R & r = 0):
            _object(o), _rank(r), _next(0) {}
    
        T * object() const { return const_cast<T *>(_object); }

        Element * next() const { return _next; }
        void next(Element * e) { _next = e; }

        const R & rank() const { return _rank; }
        const R & key() const { return _rank; }
        void rank(const R & r) { _rank = r; }
        int promote(const R & n = 1) { _rank -= n; return _rank; }
        int demote(const R & n = 1) { _rank += n; return _rank; }

    private:
        const T * _object;
         R _rank;
        Element * _next;
    };

    // Simple Grouping List Element
    template <typename T>
    class Singly_Linked_Grouping
    {
    public:
        typedef T Object_Type;
        typedef Singly_Linked_Grouping Element;

    public:
        Singly_Linked_Grouping(const T * o, int s):
            _object(o), _size(s), _next(0) {}

        T * object() const { return const_cast<T *>(_object); }

        Element * next() const { return _next; }
        void next(Element * e) { _next = e; }

        unsigned int size() const { return _size; }
        void size(unsigned int l) { _size = l; }
        void shrink(unsigned int n) { _size -= n; }
        void expand(unsigned int n) { _size += n; }
    
    private:
        const T * _object;
        unsigned int _size;
        Element * _next;
    };

    // List Element
    template <typename T>
    class Doubly_Linked
    {
    public:
        typedef T Object_Type;
        typedef Doubly_Linked Element;

    public:
        Doubly_Linked(const T * o): _object(o), _prev(0), _next(0) {}
    
        T * object() const { return const_cast<T *>(_object); }

        Element * prev() const { return _prev; }
        Element * next() const { return _next; }
        void prev(Element * e) { _prev = e; }
        void next(Element * e) { _next = e; }
    
    private:
        const T * _object;
        Element * _prev;
        Element * _next;
    };

    // Ordered List Element
    template <typename T, typename R = Rank>
    class Doubly_Linked_Ordered
    {
    public:
        typedef T Object_Type;
        typedef Rank Rank_Type;
        typedef Doubly_Linked_Ordered Element;

    public:
        Doubly_Linked_Ordered(const T * o,  const R & r = 0):
            _object(o), _rank(r), _prev(0), _next(0) {}
    
        T * object() const { return const_cast<T *>(_object); }

        Element * prev() const { return _prev; }
        Element * next() const { return _next; }
        void prev(Element * e) { _prev = e; }
        void next(Element * e) { _next = e; }

        const R & rank() const { return _rank; }
        void rank(const R & r) { _rank = r; }
        int promote(const R & n = 1) { _rank -= n; return _rank; }
        int demote(const R & n = 1) { _rank += n; return _rank; }

    private:
        const T * _object;
         R _rank;
        Element * _prev;
        Element * _next;
    };
    
    // Scheduling List Element
    template <typename T, typename R = Rank>
    class Doubly_Linked_Scheduling
    {
    public:
        typedef T Object_Type;
        typedef Rank Rank_Type;
        typedef Doubly_Linked_Scheduling Element;

    public:
        Doubly_Linked_Scheduling(const T * o,  const R & r = 0):
            _object(o), _rank(r), _prev(0), _next(0) {}

        Doubly_Linked_Scheduling():
            _object(), _rank(0), _prev(0), _next(0) {}
    
        T * object() const { return const_cast<T *>(_object); }

        Element * prev() const { return _prev; }
        Element * next() const { return _next; }
        void prev(Element * e) { _prev = e; }
        void next(Element * e) { _next = e; }

        const R & rank() const { return _rank; }
        void rank(const R & r) { _rank = r; }
        int promote(const R & n = 1) { _rank -= n; return _rank; }
        int demote(const R & n = 1) { _rank += n; return _rank; }

    private:
        const T * _object;
         R _rank;
        Element * _prev;
        Element * _next;
    };

    //Value-based elements (hold values instead of pointers)
    template <typename T, typename R>
    class Doubly_Linked_Scheduling_Value
    {
    public:
        typedef T Object_Type;
        typedef R Rank_Type;
        typedef Doubly_Linked_Scheduling_Value Element;

    public:
        Doubly_Linked_Scheduling_Value(T o, const R & r = 0)
            : _object(o), _rank(r), _prev(0), _next(0) {}

        Doubly_Linked_Scheduling_Value()
            : _object(T()), _rank(0), _prev(0), _next(0) {}


        T& object() { return _object; }

        Element * prev() const { return _prev; }
        Element * next() const { return _next; }
        void prev(Element * e) { _prev = e; }
        void next(Element * e) { _next = e; }

        const R & rank() const { return _rank; }
        void rank(const R & r) { _rank = r; }
        int promote(const R & n = 1) { _rank -= n; return _rank; }
        int demote(const R & n = 1) { _rank += n; return _rank; }

    private:
        T _object;
        R _rank;
        Element * _prev;
        Element * _next;
    };


    // Grouping List Element
    template <typename T>
    class Doubly_Linked_Grouping
    {
    public:
        typedef T Object_Type;
        typedef Doubly_Linked_Grouping Element;

    public:
        Doubly_Linked_Grouping(const T * o, int s): _object(o), _size(s),
                                                    _prev(0), _next(0) {}

        T * object() const { return const_cast<T *>(_object); }

        Element * prev() const { return _prev; }
        Element * next() const { return _next; }
        void prev(Element * e) { _prev = e; }
        void next(Element * e) { _next = e; }

        unsigned int size() const { return _size; }
        void size(unsigned int l) { _size = l; }
        void shrink(unsigned int n) { _size -= n; }
        void expand(unsigned int n) { _size += n; }
    
    private:
        const T * _object;
        unsigned int _size;
        Element * _prev;
        Element * _next;
    };
};


// List Iterators
namespace List_Iterators
{
    // Forward Iterator (for singly linked lists)
    template<typename El>
    class Forward
    {
    private:
        typedef Forward<El> Iterator;

    public:
        typedef El Element;

    public:
        Forward(): _current(0) {}
        Forward(Element * e): _current(e) {}

        operator Element *() const { return _current; }

        Element & operator*() const { return *_current; }
        Element * operator->() const { return _current; }

        Iterator & operator++() {
            _current = _current->next(); return *this;
        }
        Iterator operator++(int) {
            Iterator tmp = *this; ++*this; return tmp;
        }

        bool operator==(const Iterator & i) const {
            return _current == i.current;
        }
        bool operator!=(const Iterator & i) const {
            return _current != i._current;
        }

    private:
        Element * _current;
    };

    // Bidireacional Iterator (for doubly linked lists)
    template<typename El>
    class Bidirecional
    {
    private:
        typedef Bidirecional<El> Iterator;

    public:
        typedef El Element;

    public:
        Bidirecional(): _current(0) {}
        Bidirecional(Element * e): _current(e) {}

        operator Element *() const { return _current; }

        Element & operator*() const { return *_current; }
        Element * operator->() const { return _current; }

        Iterator & operator++() {
            _current = _current->next(); return *this;
        }
        Iterator operator++(int) {
            Iterator tmp = *this; ++*this; return tmp;
        }

        Iterator & operator--() {
            _current = _current->prev(); return *this;
        }
        Iterator operator--(int) {
            Iterator tmp = *this; --*this; return tmp;
        }
        
        bool operator==(const Iterator & i) const {
            return _current == i.current;
        }
        bool operator!=(const Iterator & i) const {
            return _current != i._current;
        }

    private:
        Element * _current;
    };
}

// Singly-Linked List
template <typename T, 
          typename El = List_Elements::Singly_Linked<T> >
class Simple_List
{
public:
    typedef T Object_Type;
    typedef El Element;
    typedef List_Iterators::Forward<El> Iterator;

public:
    Simple_List(): _size(0), _head(0), _tail(0) {}

    bool empty() const { return (_size == 0); }
    unsigned int size() const { return _size; }

    Maybe<Element*> head() { return Maybe<Element*>(_head, _head != 0); }
    Maybe<Element*> tail() { return Maybe<Element*>(_tail, _tail != 0); }

    Iterator begin() { return Iterator(_head); }
    Iterator end() { return Iterator(_tail->next()); }

    void insert(Element * e) { insert_tail(e); }

    void insert_head(Element * e) {
        if(empty())
            insert_first(e);
        else {
            e->next(_head);
            _head = e;
            _size++;
        }
    }

    void insert_tail(Element * e) {
        if(empty())
            insert_first(e);
        else {
            _tail->next(e);
            e->next(0);
            _tail = e;
            _size++;
        }
    }
    
    Maybe<Element*> remove() { return remove_head(); }

    Element* remove(Element * e) {
        if(last())
            remove_last();
        else if(e == _head)
            remove_head();
            else {
                Element * p = _head;
                for(; p && p->next() && (p->next() != e); p = p->next());
                if(p)
                    p->next(e->next());
        if(e == _tail)
            _tail = p;
                _size--;
            }
        return e;
    }

    Maybe<Element*> remove_head() {
        if(empty())
            return Maybe<Element*>();
        if(last())
            return Maybe<Element*>(remove_last());
        Maybe<Element*> e = Maybe<Element*>(_head);
        _head = _head->next();
        _size--;
        return e;
    }

    Maybe<Element*> remove_tail() {
        Maybe<Element*> e;

        if(_tail != 0)
            e = Maybe<Element*>(remove(_tail));

        return e;
    }

    Maybe<Element*> remove(const Object_Type * obj) {
        Maybe<Element*> e = search(obj);
        if(e.exists())
            return Maybe<Element*>(remove(e.get()));
        return Maybe<Element*>();
    }
    
    Maybe<Element*> search(const Object_Type * obj) {
        Element * e = _head;
        for(; e && (e->object() != obj); e = e->next());
        return Maybe<Element*>(e, e != 0);
    }
    
protected:
    bool last() const { return (_size == 1); }

    void insert(Element * e, Element * p,  Element * n) {
        p->next(e);
        e->next(n);
        _size++;
    }

    void insert_first(Element * e) {
        e->next(0);
        _head = e;
        _tail = e;
        _size++;
    }

    Element * remove_last() {
        Element * e = _head;
        _head = 0;
        _tail = 0;
        _size--;
        return e;
    }

private:
    unsigned int _size;
    Element * _head;
    Element * _tail;
};


// Singly-Linked, Ordered List
template <typename T, 
          typename R = List_Element_Rank, 
          typename El = List_Elements::Singly_Linked_Ordered<T, R>,
          bool relative = false>
class Simple_Ordered_List: public Simple_List<T, El>
{
private:
    typedef Simple_List<T, El> Base;

public:
    typedef T Object_Type;
    typedef R Rank_Type;
    typedef El Element;
    typedef List_Iterators::Forward<El> Iterator;

public:
    using Base::empty;
    using Base::size;
    using Base::head;
    using Base::begin;
    using Base::end;
    using Base::remove_head;
    
    void insert(Element * e) {
        if(empty())
            insert_first(e);
        else {
            Element * next, * prev;
            for(next = head().get(), prev = 0;
                (next->rank() <= e->rank()) && next->next();
                prev = next, next = next->next())
                if(relative)
                    e->rank(e->rank() - next->rank());
            if(next->rank() <= e->rank()) {
                if(relative)
                    e->rank(e->rank() - next->rank());
                insert_tail(e);
            } else if(!prev) {
                if(relative)
                    next->rank(next->rank() - e->rank());
                insert_head(e);
            } else {
                if(relative)
                    next->rank(next->rank() - e->rank());
                Base::insert(e, prev, next);
            }
        }
    }
    
    Maybe<Element*> remove() { return remove_head(); }

    Element * remove(Element * e) {
        Base::remove(e);
        if(relative && e->next())
            e->next()->rank(e->next()->rank() + e->rank());
        return e;
    }

    Maybe<Element*> remove(const Object_Type * obj) {
        Maybe<Element*> e = search(obj);
        if(e.exists())
            return Maybe<Element*>(remove(e.get()));
        return Maybe<Element*>();
    }
    
    Maybe<Element*> search_rank(int rank) {
        Maybe<Element*> el = head();
        if(el.exists()) {
            Element* e(el.get());
            for(; e && (e->rank() != rank); e = e->next());
            return Maybe<Element*>(e, e != 0);
        }

        return Maybe<Element*>();
    }

    Maybe<Element*> remove_rank(int rank) {
        Maybe<Element*> e(search_rank(rank));
        if(e.exists())
            return Maybe<Element*>(remove(e.get()));
        return Maybe<Element*>();
    }
};


// Singly-Linked, Relative Ordered List
template <typename T, 
          typename R = List_Element_Rank,
          typename El = List_Elements::Singly_Linked_Ordered<T, R> >
class Simple_Relative_List: public Simple_Ordered_List<T, R, El, true> {};


// Singly-Linked, Grouping List
template <typename T,
          typename El = List_Elements::Singly_Linked_Grouping<T> >
class Simple_Grouping_List: public Simple_List<T, El>
{
private:
    typedef Simple_List<T, El> Base;

public:
    typedef T Object_Type;
    typedef El Element;
    typedef List_Iterators::Forward<El> Iterator;

public:
    Simple_Grouping_List(): _grouped_size(0) {}

    using Base::empty;
    using Base::size;
    using Base::head;
    using Base::tail;
    using Base::begin;
    using Base::end;

    unsigned int grouped_size() const { return _grouped_size; }

    Maybe<Element*> search_size(unsigned int s) {
        Maybe<Element*> el = head();
        if(el.exists()) {
            Element* e = el.get();
            for(; e && (e->size() < s); e = e->next());
            return Maybe<Element*>(e, e!=0);
        }
        return Maybe<Element*>();
    }
    
    Maybe<Element*> search_left(const Object_Type * obj) {
        Maybe<Element*> el = head();
        if(el.exists()) {
            Element* e = el.get();
            for(; e && (e->object() + e->size() != obj); e = e->next());
            return Maybe<Element*>(e, e!=0);
        }
        return Maybe<Element*>();
    }
    
    void insert_merging(Element * e, Element ** m1, Element ** m2) {
        _grouped_size += e->size();
        *m1 = *m2 = 0;
        Maybe<Element*> r = search(e->object() + e->size());
        Maybe<Element*> l = search_left(e->object());

        if(r.exists()) {
            Element * _r = r.get();
            e->size(e->size() + _r->size());
            remove(_r);
            *m1 = _r;
        }
        if(l.exists()) {
            Element * _l = l.get();
            _l->size(_l->size() + e->size());
            *m2 = e;
        } else
            insert_tail(e);
    }
    
    Maybe<Element*> search_decrementing(unsigned int s) {
        Maybe<Element*> el = search_size(s);
        if(el.exists()) {
            Element* e = el.get();
            e->shrink(s);
            _grouped_size -= s;
            if(!e->size())
                remove(e);
        }
        return el;
    }
    
private:
    unsigned int _grouped_size;
};


// Doubly-Linked List
template <typename T,
          typename El = List_Elements::Doubly_Linked<T> >
class List
{
public:
    typedef T Object_Type;
    typedef El Element;
    typedef List_Iterators::Bidirecional<El> Iterator;

public:
    List(): _size(0), _head(0), _tail(0) {}

    bool empty() const { return (_size == 0); }
    unsigned int size() const { return _size; }
    
    Maybe<Element*> head() { return Maybe<Element*>(_head, _head != 0); }
    Maybe<Element*> tail() { return Maybe<Element*>(_tail, _tail != 0); }
    
    Iterator begin() { return Iterator(_head); }
    Iterator end() { return Iterator(_tail->next()); }

    void insert(Element * e) { insert_tail(e); }

    void insert_head(Element * e) {
        if(empty())
            insert_first(e);
        else {
            e->prev(0);
            e->next(_head);
            _head->prev(e);
            _head = e;
            _size++;
        }
    }

    void insert_tail(Element * e) {
        if(empty())
            insert_first(e);
        else {
            _tail->next(e);
            e->prev(_tail);
            e->next(0);
            _tail = e;
            _size++;
        }
    }
    
    Maybe<Element*> remove() { return remove_head(); }

    Element * remove(Element * e) {
        if(last())
            remove_last();
        else if(!e->prev())
            remove_head();
        else if(!e->next())
            remove_tail();
        else {
            e->prev()->next(e->next());
            e->next()->prev(e->prev());
            _size--;
        }
        
        return e;
    }

    Maybe<Element*> remove_head() {
        if(empty())
            return Maybe<Element*>();
        if(last())
            return remove_last();
        Element * e = _head;
        _head = _head->next();
        _head->prev(0);
        _size--;
        
        return Maybe<Element*>(e);
    }

    Maybe<Element*> remove_tail() {
        if(empty())
            return Maybe<Element*>();
        if(last())
            return remove_last();
        Element * e = _tail;
        _tail = _tail->prev();
        _tail->next(0);
        _size--;
        
        return Maybe<Element*>(e);
    }

    Maybe<Element*> remove(const Object_Type * obj) {
        Maybe<Element*> e(search(obj));
        if(e.exists())
            return Maybe<Element*>(remove(e.get()));
        return Maybe<Element*>();
    }
    
    Maybe<Element*> search(const Object_Type * obj) {
        Element * e = _head;
        for(; e && (e->object() != obj); e = e->next());
        return Maybe<Element*>(e, e != 0);
    }
    
    Maybe<Element*> search(const Object_Type &obj) {
        Element * e = _head;
        for(; e && (e->object() != obj); e = e->next());
        return Maybe<Element*>(e, e != 0);
    }

protected:
    bool last() const { return (_size == 1); }

    void insert(Element * e, Element * p,  Element * n) {
        p->next(e);
        n->prev(e);
        e->prev(p);
        e->next(n);
        _size++;
    }

    void insert_first(Element * e) {
        e->prev(0);
        e->next(0);
        _head = e;
        _tail = e;
        _size++;
    }

    Maybe<Element*> remove_last() {
        Maybe<Element*> e = head();
        _head = 0;
        _tail = 0;
        _size--;

        return e;
    }

private:
    unsigned int _size;
    Element * _head;
    Element * _tail;
};


// Doubly-Linked, Ordered List
template <typename T,
          typename R = List_Element_Rank,
          typename El = List_Elements::Doubly_Linked_Ordered<T, R>,
          bool relative = false>
class Ordered_List: public List<T, El>
{
private:
    typedef List<T, El> Base;

public:
    typedef T Object_Type;
    typedef R Rank_Type;
    typedef El Element;
    typedef List_Iterators::Bidirecional<El> Iterator;

public:
    using Base::empty;
    using Base::size;
    using Base::head;
    using Base::tail;
    using Base::begin;
    using Base::end;

    void insert(Element * e) {
        if(empty())
            insert_first(e);
        else {
            Element * next;
            insert_search:
            for(next = head().get();
                (next->rank() <= e->rank()) && next->next();
                next = next->next())
                if(relative)
                    e->rank(e->rank() - next->rank());
            if(next->rank() <= e->rank()) {
                if(relative)
                    e->rank(e->rank() - next->rank());
                insert_tail(e);
            } else if(!next->prev()) {
                if(relative)
                    next->rank(next->rank() - e->rank());
                insert_head(e);
            } else {
                if(relative)
                    next->rank(next->rank() - e->rank());
                Base::insert(e, next->prev(), next);
            }
        }
    }
    
    Maybe<Element*> remove() { 
        return Base::remove_head();
    }

    Element * remove(Element * e) {
        Base::remove(e);
        if(relative && e->next())
            e->next()->rank(e->next()->rank() + e->rank());

        return e;
    }

    Maybe<Element*> remove(const Object_Type * obj) {
        Maybe<Element*> e = search(obj);
        if(e.exists())
            return Maybe<Element*>(remove(e.get()));
        return Maybe<Element*>();
    }
    
    Element * search_rank(int rank) {
        Maybe<Element*> el = head();
        if(el.exists()) {
            Element* e(el.get());
            for(; e && (e->rank() != rank); e = e->next());
            return Maybe<Element*>(e, e != 0);
        }
        return Maybe<Element*>();
    }

    Element * remove_rank(int rank) {
        Maybe<Element*> e = search_rank(rank);
        if(e.exists())
            return Maybe<Element*>(remove(e.get()));
        return Maybe<Element*>();
    }
};


// Doubly-Linked, Relative Ordered List
template <typename T,
          typename R = List_Element_Rank,
          typename El = List_Elements::Doubly_Linked_Ordered<T, R> >
class Relative_List: public Ordered_List<T, R, El, true> {};


// Doubly-Linked, Scheduling List
template <typename T,
          typename R = List_Element_Rank, 
          typename El = List_Elements::Doubly_Linked_Scheduling<T, R> >
class Scheduling_List: private Ordered_List<T, R, El>
{
private:
    typedef Ordered_List<T, R, El> Base;

public:
    typedef T Object_Type;
    typedef R Rank_Type;
    typedef El Element;
    typedef typename Base::Iterator Iterator;

public:
    Scheduling_List(): _chosen(0) {}

    using Base::empty;
    using Base::size;
    using Base::head;
    using Base::tail;
    using Base::begin;
    using Base::end;

    Maybe<Element*> chosen() { return Maybe<Element*>(_chosen, _chosen != 0); }

    void insert(Element * e) {
        Base::insert(e);
          if(!_chosen)
             _chosen = head().get();
    }

    Element * remove(Element * e) {
        Base::remove(e);
        if(e == _chosen)
            _chosen = head().get();

        return e;
    }

    Maybe<Element*> remove(const Object_Type * obj) {
        Maybe<Element*> e = search(obj);
        if(e.exists())
            return Maybe<Element*>(remove(e.get()));
        else
            return Maybe<Element*>();
    }

    Maybe<Element*> choose() {
        if(!empty()) {
            reorder();
            _chosen = head().get();
        }

        return Maybe<Element*>(_chosen, _chosen != 0);
    }

    Maybe<Element*> choose_another() {
        if(size() > 1) {
            Element * tmp = _chosen;
            Base::remove(tmp);
            _chosen = Base::head().get();
            Base::insert(tmp);
        }

        return Maybe<Element*>(_chosen, _chosen != 0);
    }

    Element * choose(Element * e) {
        if(_chosen)
            reorder();
        _chosen = e;

        return _chosen;
    }

    Maybe<Element*> choose(const Object_Type * obj) {
        Maybe<Element*> e = search(obj);
        if(e.exists())
            return Maybe<Element*>(choose(e.get()));
        else
            return Maybe<Element*>();
    }

    Maybe<Element*> choose(const Object_Type &obj) {
        Maybe<Element*> e = search(obj);
        if(e.exists())
            return Maybe<Element*>(choose(e.get()));
        else
            return Maybe<Element*>();
    }

private:
    void reorder() {
        // Rank might have changed, so remove and insert to reorder
        Base::remove(_chosen);
        Base::insert(_chosen);
    }
    
private:
    Element* _chosen;
};

 
// Doubly-Linked, Grouping List
template <typename T, 
          typename El = List_Elements::Doubly_Linked_Grouping<T> >
class Grouping_List: public List<T, El>
{
private:
    typedef List<T, El> Base;

public:
    typedef T Object_Type;
    typedef El Element;
    typedef List_Iterators::Bidirecional<El> Iterator;

public:
    Grouping_List(): _grouped_size(0) {}

    using Base::empty;
    using Base::size;
    using Base::head;
    using Base::tail;
    using Base::begin;
    using Base::end;

    unsigned int grouped_size() const { return _grouped_size; }
    
    Element * search_size(unsigned int s) {
        Maybe<Element*> _e = head();
        Element * e = _e.get();

        for(; e && (e->size() < sizeof(Element) + s) && (e->size() != s); e = e->next());
        return e;
    }
    
    Maybe<Element*> search_left(const Object_Type * obj) {
        Maybe<Element*> _e = head();
        Element * e = _e.get();

        for(; e && (e->object() + e->size() != obj); e = e->next());
        return Maybe<Element*>(e, e!=0);
    }
    
    void insert_merging(Element * e, Element ** m1, Element ** m2) {
        _grouped_size += e->size();
        *m1 = *m2 = 0;
        Maybe<Element*> r = search(e->object() + e->size());
        Maybe<Element*> l = search_left(e->object());

        if(!l.exists()) {
            insert_tail(e);
        }
        if(r.exists()) {
            Element * _r = r.get();
            e->size(e->size() + _r->size());
            remove(_r);
            *m1 = _r;
        }
        if(l.exists()) {
            Element * _l = l.get();
            _l->size(_l->size() + e->size());
            *m2 = e;
        }
    }
    
    Element * search_decrementing(unsigned int s) {
        Element * e = search_size(s);
        if(e) {
            e->shrink(s);
            _grouped_size -= s;
            if(!e->size())
                remove(e);
        }

        return e;
    }
    
private:
    unsigned int _grouped_size;
};

}

#endif
