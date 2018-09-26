package com.renren.jinkong.kylin.dbtool.model;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/25 19:12
 */
public class Kv<K, V> {
    private K k;
    private V v;

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    public Kv() {
    }

    public Kv(K k, V v) {
        this.k = k;
        this.v = v;
    }

    @Override
    public String toString() {
        return "Kv{" +
                "k=" + k +
                ", v=" + v +
                '}';
    }
}
