// EPOS BHeap Utility Declarations

#ifndef __bheap_h
#define __bheap_h

#include <system/config.h>
#include <utility/list.h>

__BEGIN_SYS

namespace BHeap_Elements
{
  typedef List_Element_Rank Rank;
  
  template <typename T, typename R = Rank>
  class Simple_Element
  {
  public:
      typedef T Object_Type;
      typedef Rank Rank_Type;
      typedef Simple_Element Element;
  public:
      Simple_Element(const T * o, const R & r = 0):
          _object(o), _rank(r){ }
          
      T * object() const { return const_cast<T *>(_object); }
      
      const R & rank() const { return _rank; }
      const R & key() const { return _rank; }
      void rank(const R & r) { _rank = r; }
  private:
      const T * _object;
      R _rank;
  };
};

template <typename T,
          typename R = List_Element_Rank,
          typename El = BHeap_Elements::Simple_Element<T, R> >
class BHeap
{
private:
  static const int MAX_SIZE = 10;
  
public:
    typedef T Object_Type;
    typedef R Rank_Type;
    typedef El Element;
  
public:
   BHeap() { elements = 0; }
   
   int size() { return elements; }
   
  // Return TRUE if pos is a leaf position
    bool leaf( int pos ) { 
      return (left(pos) >= elements);
    } 
   
  int left(int root) { 
    return (root * 2) + 1; 
  }
   
   int right(int root) {
        return (root * 2) + 2;
   }
   
   int parent(int child) {
      if(child != 0); //main root has no parent
        return (child - 1) / 2;
      return 0;
   }
   
   bool hasOneLeaf(int root) { return (elements == right(root)); }
   
   void insert(Element *e) {
      if(full()) return;
      
      //kout << "Inserting element = " << *e->object() << " rank = " << e->rank() << "\n";
      
      int curr = elements;
      array[curr] = e; //elements represents the array position after the last, since indexing starts with 0
      elements++;
      
      while(curr != 0) {
          //kout << "curr pos = " << curr << " array[curr]->rank() = " << array[curr]->rank()
          //<< " parent->rank() = " << array[parent(curr)]->rank()  << endl;
          int parent_index = parent(curr);
          if(array[parent_index]->rank() < array[curr]->rank())
            break;
          swap(curr, parent_index);
          curr = parent_index; //update the item's positions
      }
   }
   
   int search_left(int parent_index, Element * e) {
      //if(parent_index >= elements) return -1;
      int left_elem = left(parent_index);
      if(left_elem >= elements) return -1;
      if(array[left_elem] == e) 
        return left_elem;
      return search_root(left_elem, e);
  }
  
  int search_right(int parent_index, Element * e) {
      //if(parent_index >= elements) return -1;
      int right_elem = right(parent_index);
      if(right_elem >= elements) return -1;
      if(array[right_elem] == e) 
        return right_elem;
      return search_root(right_elem, e);
  }
  
  int search_root(int parent_index, Element * e) {
    int i;
    if(array[parent_index] == e) 
      return parent_index;
    i = search_left(parent_index, e);
    if(i != -1) return i;
    return search_right(parent_index, e);
  }

   void remove(Element *e) {
      if(empty()) return;
      int pos = search_root(0, e);
      //kout << "removing element = " << *e->object() << " pos = " << pos << " elements = " << elements << "\n";
      //while(1);
      if(pos != -1) {
          swap(pos, --elements);
          
          int i = pos;
          while(i < elements) {
              int min_index = i;
              int left_index = left(i);
              int right_index = right(i);
              if(left_index < elements && array[left_index]->rank() < array[min_index]->rank())
                  min_index = left_index;
              if(right_index < elements && array[right_index]->rank() < array[min_index]->rank())
                  min_index = right_index;
              if(min_index != i) {
                  //kout << "swapping i = " << i << " min_index = " << min_index << "\n";
                  swap(i, min_index);
                  i = min_index;
              } else
                break;
          }
          
          /*if(elements > 1)
            siftdown(pos);*/
      }
   }
   
   Element *head() { 
     if(elements > 0)
       return array[0];
     return 0;
   }
   
   bool empty() { return (elements == 0); }
   bool full() { return (elements == MAX_SIZE); }
   int count() { return elements; }
   
   Element * value(int pos) { 
     if(pos < elements) 
       return array[pos]; 
  }
  
  Element * search_left(int parent, const Object_Type * obj) {
      if(parent >= elements) return 0;
      int left_elem = left(parent);
      if(left_elem >= elements) return 0;
      if(array[left_elem]->object() == obj) 
        return array[left_elem];
      return search_root(left_elem, obj);
  }
  
  Element * search_right(int parent, const Object_Type * obj) {
      if(parent >= elements) return 0;
      int right_elem = right(parent);
      if(right_elem == -1) return 0;
      if(array[right_elem]->object() == obj) 
        return array[right_elem];
      return search_root(right_elem, obj);
  }
  
  Element *search_root(int parent, const Object_Type * obj) {
    Element *e;
    e = search_left(parent, obj);
    if(e != 0) return e;
    return search_right(parent, obj);
  }
  
  Element * search(const Object_Type * obj) {
      if(empty()) return 0;
      return search_root(0, obj);
  }
  
private:
  Element * array[MAX_SIZE];
  int elements; //how many elements are in the heap
  
  void swap(int pos1, int pos2) {
      Element *tmp = array[pos1];
      array[pos1] = array[pos2];
      array[pos2] = tmp; 
  }
  
  /*void siftdown(int index) {
      int curr = index;
      while(!leaf(curr)) {
          if(hasOneLeaf(curr)) {
              int left_index = left(curr);
              if(array[left_index]->rank() < array[curr]->rank()) {
                  swap(left_index, curr);
                  curr = left_index;
              } else
                break;
          } else {
              int right_index = right(curr);
              int left_index = left(curr);
              if(array[right_index]->rank() > array[left_index]->rank()) {
                  if(array[right_index]->rank() < array[curr]->rank()) {
                      swap(right_index, curr);
                      curr = right_index;
                  } else
                    break;
              } else {
                  if(array[left_index]->rank() < array[curr]->rank()) {
                      swap(left_index, curr);
                      curr = left_index;
                  } else
                    break;
              }
          }
      }
  }  */
};

template <typename T,
          typename R = List_Element_Rank, 
          typename El = BHeap_Elements::Simple_Element<T, R> >
class Scheduling_BHeap: private BHeap<T, R, El>
{
private:
  typedef BHeap<T, R, El> Base;
  
public:    
  typedef T Object_Type;
  typedef R Rank_Type;
  typedef El Element;
  
public:
    Scheduling_BHeap(): _chosen(0) {}  
    
    using Base::empty;
    using Base::size;
    using Base::head;
    using Base::search;
    using Base::value;
    
    Element * volatile & chosen() { return _chosen; }
    
    void insert(Element * e) {
      kout << "inserting e = " << e << "\n";
       Base::insert(e);
       _chosen = head();
    }
    
    Element * remove(Element * e) { 
        Base::remove(e);
        if(e == _chosen)
            _chosen = head();

        return e;
    }
    
    Element * remove(const Object_Type * obj) {
      kout << "removing obj  = " << obj << "\n";
        Element * e = search(obj);
        if(e)
            return remove(e);
        else
            return 0;
    }
    
    Element * choose() {
        if(!empty()) {
            reorder();
            _chosen = head();
        }
        return _chosen;
    }
    
    Element * choose_another() {
        if(size() > 1) {
            Element * tmp = _chosen;
            Base::remove(tmp);
            _chosen = Base::head();
            Base::insert(tmp);
        }

        return _chosen;
    }
    
    Element * choose(Element * e) {
        if(_chosen)
            reorder();
        _chosen = e;

        return _chosen;
    }
    
    Element * choose(const Object_Type * obj) {
        Element * e = search(obj);
        if(e)
            return choose(e);
        else
            return 0;
    }

private:
    void reorder() {
        // Rank might have changed, so remove and insert to reorder
        Base::remove(_chosen);
        Base::insert(_chosen);
    }

private:
    Element * volatile _chosen;
  
};

#endif

__END_SYS