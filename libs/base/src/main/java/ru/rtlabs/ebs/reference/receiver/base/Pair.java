package ru.rtlabs.ebs.reference.receiver.base;

import java.util.Objects;

public class Pair<K, V> {

  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public boolean equals(Object o) {
    return (o != null) && (this.getClass() == o.getClass()) && Objects
        .equals(key, ((Pair<?, ?>) o).key) && Objects.equals(value, ((Pair<?, ?>) o).value);
  }

  public int hashCode() {
    return 31 * Objects.hashCode(key) + Objects.hashCode(value);
  }

  public String toString() {
    return key + "=" + value;
  }

  public K getFirst() {
    return this.key;
  }

  public void setFirst(K key) {
    this.key = key;
  }

  public V getSecond() {
    return this.value;
  }

  public void setSecond(V value) {
    this.value = value;
  }

}
