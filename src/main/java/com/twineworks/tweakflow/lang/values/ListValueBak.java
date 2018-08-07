/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Twineworks GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.twineworks.tweakflow.lang.values;

import com.twineworks.tweakflow.lang.errors.LangError;
import com.twineworks.tweakflow.lang.errors.LangException;
import io.vavr.collection.Vector;

import java.util.*;

final public class ListValueBak implements Iterable<Value> {

  private final Vector<Value> vec;

  private ListValueBak(Vector<Value> vec) {
    this.vec = vec;
  }

  public ListValueBak() {
    this(Vector.empty());
  }

  public ListValueBak(List<Value> values) {
    this.vec = Vector.<Value>empty().appendAll(values);
  }

  public ListValueBak(Value[] values) {
    this.vec = Vector.ofAll(Arrays.asList(values));
  }

  public ListValueBak(Collection<Value> values) {
    this.vec = Vector.<Value>empty().appendAll(values);
  }

  public Value get(long index) {
    if (index >= 0 && index < vec.length()){
      Value value = vec.get((int)index);
      if (value == null) return Values.NIL;
      return value;
    }

    return Values.NIL;
  }

  public ListValueBak set(long index, Value value) {

    if (index > Integer.MAX_VALUE || index < 0L) throw new LangException(LangError.INDEX_OUT_OF_BOUNDS, "cannot set index "+index);

    Vector<Value> ret = vec;

    if (index >= vec.length()){
      ret = ret.padTo((int)index, Values.NIL).append(value);
    }
    else{
      ret = ret.update((int)index, value);
    }

    return new ListValueBak(ret);
  }

  public boolean isEmpty() {
    return vec.isEmpty();
  }

  public ListValueBak append(Value v) {
    return new ListValueBak(vec.append(v));
  }

  public ListValueBak appendAll(List<? extends Value> values) {
    return new ListValueBak(vec.appendAll(values));
  }

  public ListValueBak appendAll(Value[] values) {
    return new ListValueBak(vec.appendAll(Arrays.asList(values)));
  }

  public ListValueBak appendAll(ListValueBak values) {
    return new ListValueBak(vec.appendAll(values));
  }

  public ListValueBak appendAll(Iterable<? extends Value> values) {
    // empty list concat can be short-circuited
    if (values instanceof ListValueBak){
      ListValueBak other = (ListValueBak) values;
      if (vec.isEmpty()){
        return other;
      }
      else if (other.isEmpty()){
        return this;
      }
    }
    return new ListValueBak(vec.appendAll(values));
  }

  public ListValueBak prepend(Value x) {
    return new ListValueBak(vec.prepend(x));
  }

  public ListValueBak padTo(long length, Value withValue) {
    if (length > Integer.MAX_VALUE) throw new LangException(LangError.INDEX_OUT_OF_BOUNDS, "cannot pad to length "+length);
    return new ListValueBak(vec.padTo((int)length, withValue));
  }
  
  public ListValueBak insert(long idx, Value value) {
    if (idx >= Integer.MAX_VALUE) throw new LangException(LangError.INDEX_OUT_OF_BOUNDS, "cannot insert at index "+idx);
    Vector<Value> ret = vec;
    if (idx > vec.size()){
      ret = ret.padTo((int)idx, Values.NIL);
    }

    ret = ret.insert((int)idx, value);

    return new ListValueBak(ret);
  }

  public ListValueBak delete(long idx) {
    if (idx >= vec.length()) return this;
    if (idx < 0) return this;
    return new ListValueBak(vec.removeAt((int)idx));
  }

  public ListValueBak take(int n) {
    return new ListValueBak(vec.take(n));
  }

  public ListValueBak drop(int n) {
    return new ListValueBak(vec.drop(n));
  }

  public ListValueBak init() {
    return new ListValueBak(vec.init());
  }

  public ListValueBak tail() {
    return new ListValueBak(vec.tail());
  }

  public ListValueBak reverse() {
    return new ListValueBak(vec.reverse());
  }

  public Value head() {
    return vec.head();
  }

  public Value last() {
    return vec.last();
  }

  public ListValueBak slice(int startIndex, int endIndex) {
    return new ListValueBak(vec.slice(startIndex, endIndex));
  }

  public Value indexOf(Value x) {
    return Values.make(vec.indexOf(x));
  }

  public Value indexOf(Value x, long from) {
    if (from > Integer.MAX_VALUE) return Values.LONG_NEG_ONE;
    return Values.make(vec.indexOf(x, Math.max((int) from, 0)));
  }
  
  public Value lastIndexOf(Value x) {
    return Values.make(vec.lastIndexOf(x));
  }

  public Value lastIndexOf(Value x, long end) {
    if (end >= Integer.MAX_VALUE) return Values.LONG_NEG_ONE;
    return Values.make(vec.lastIndexOf(x, (int)end));
  }

  public int size() {
    return vec.size();
  }

  public boolean containsValue(Value value) {
    return vec.contains(value);
  }

  public ListValueBak sort(Comparator<Value> comparator){
    Value[] values = vec.toJavaArray(Value.class);
    Arrays.sort(values, comparator);
    return new ListValueBak(values);
  }

  public Iterator<Value> iterator() {

    // basic iterator implementation that allows basic traversal only
    io.vavr.collection.Iterator<Value> internalIterator = vec.iterator();

    return new Iterator<Value>(){
      
      public boolean hasNext() {
        return internalIterator.hasNext();
      }

      
      public Value next() {
        Value next = internalIterator.next();
        if (next == null) return Values.NIL;
        return next;
      }
    };
  }

  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null) return false;

    if (!(o instanceof ListValueBak)) return false;
    ListValueBak values = (ListValueBak) o;
    return vec.equals(values.vec);

  }

  public int hashCode() {
    return vec.hashCode();
  }
}